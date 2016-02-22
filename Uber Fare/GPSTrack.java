import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/*
 * Contains static members for reading a GPS track from a file and interpolating 
 * between points.
 *
 * Jim Glenn
 * 2015-07-10
 */

public class GPSTrack {
    
    /* Interpolates between two values.
     *
     * x1 a double for the first value to interpolate between
     * x2 a double for the second value to interpolate between
     * y1 a double
     * y2 a double not equal to y1
     * z a value between y1 and y2 given how far to interpolate between x1 and x2
     * Returns the interpolated value
     */
    private static double interpolate(double x1, double x2, double y1, double y2, double z) {
	return x1 + (x2 - x1) * (z - y1) / (y2 - y1);
    } // interpolate

    /* Creates a new track from the given track.  The new track will have points evenly
     * spaced in time according to the given interval, with positions interpolated between
     * points in the original track.  The first and last points in the returned track
     * will be the first and last points in the original; as a result the last point
     * in the returned track may be less than the interval away (in time) from the
     * penultimate point.
     *
     * Parameters:
     * track a GPS track with points in order of increasing time
     * interval a positive double for the time between points in the returned track
     * Returns the interpolated track
     */
    private static Trackpoint[] interpolateTrack(List<Trackpoint> track, double interval) {
	List<Trackpoint> result = new ArrayList<Trackpoint>();

	if (track.size() == 0) {
	    return null;
	}

	result.add(track.get(0));
	
	int point = 1;
	Trackpoint last = track.get(0);
	double time = last.getTime() + interval; // time of the next interpolated point

	while (point < track.size()) {
		Trackpoint curr = track.get(point);

		// INVARIANT: all interpolated points with
		// time <= track.get(point - 1).getTime() have been added
		while (time < curr.getTime()) {
			// add interpolated point (should interpolate along great circle,
			// but this should be close enough if the track points are
			// close enough)
		    result.add(new Trackpoint(interpolate(last.getLatitude(), curr.getLatitude(), last.getTime(), curr.getTime(), time),
					      interpolate(last.getLongitude(), curr.getLongitude(), last.getTime(), curr.getTime(), time),
					      time));
		    
		    time += interval;
		}
		if (time == curr.getTime()) {
		    result.add(curr);
		    time += interval;
		}

		last = curr;
		point++;
	}

	// add last point if not already added
	if (track.get(point - 1).getTime() > time) {
	    result.add(track.get(point - 1));
	}

	return result.toArray(new Trackpoint[result.size()]);
    } // interpolateTrack

    /* Loads a GPS track from the file with the given name and returns it
     * as a list of Trackpoint objects, with one Trackpoint per second.
     * The file should contain one point per line, with latitude, longitude,
     * and time separated by spaces; the geographic coordinates should be
     * given in decimal degrees and the time should be given in seconds
     * since an epoch.
     *
     * Parameter:
     * fname the name of a file containing GPS track data
     * Returns the GPS track as a list of Trackpoint objects in chronological
     * order, or null if the file could not be opened
     */
    public static Trackpoint[] trackFromFile(String fname) {
	try {
	    // open file for reading
	    Scanner in = new Scanner(new File(fname));
	    
	    // create the list
	    List<Trackpoint> track = new ArrayList<Trackpoint>();
	    
	    // loop until there is no more data
	    while (in.hasNextDouble()) {
		// read data from line
		double lat = in.nextDouble();
		double lon = in.nextDouble();
		double time = in.nextDouble();
		
		// bindle data into an object and add to the list
		track.add(new Trackpoint(lat, lon, time));
	    }

	    // clean up
	    in.close();

	    // interpolate any missing data and return the result
	    return interpolateTrack(track, 1.0);
	} catch (FileNotFoundException ex) {
	    // bad file name!  abort!
	    return null;
	}
    } // trackFromFile
    
} // class
