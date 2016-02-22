/**
 * This class defines a Trackpoint object.  A Trackpoint
 * represents a geographical position with recorded time from a GPS track.
 *
 *  Jim Glenn, Crystal Valentine
 *  10/2015
 */

public class Trackpoint {

    // The latitude of this trackpoint, in decimal degrees.
    private double latitude;

    // The longitude of this trackpoint, in decimal degrees.
    private double longitude;

    // The time of this trackpoint, in seconds since the epoch.
    private double time;

    /* Constructor
     * Creates a new trackpoint with the given values.
     *
     * Parameters:
     * lat a double between +/-90 giving the latitude in decimal degrees
     * lon a double giving the longitude in decimal degrees
     * t a double giving the time in seconds since the epoch
     */
    public Trackpoint(double lat, double lon, double t) {
	latitude = lat;
	longitude = lon;
	time = t;
    } // constructor

    //Returns the latitude of this trackpoint.
    public double getLatitude() {
	return latitude;
    } // getLatitude

    // Returns the longitude of this trackpoint, in decimal degrees.
    public double getLongitude() {
	return longitude;
    } // getLongitude

    // Returns the time of this trackpoint, in seconds since the epoch.
    public double getTime() {
	return time;
    } // getTime

    /*
     * Returns the distance between the coordinates of this trackpoint
     * and the given trackpoint, in miles.
     *
     * Parameter o is another trackpoint
     * The method returns the distance between the coordinates of the trackpoints
     */
    public double distance(Trackpoint o) {
	return Distance.calcDistance(latitude, longitude, o.latitude, o.longitude);
    } // distance

    /*
     * Returns the time between this trackpoint and the given one, in seconds;
     * positive if the given trackpoint is later and negative if it is earlier.
     *
     * Parameter o is another  trackpoint
     * The method returns the time (in seconds) between the trackpoints
     */
    public double timeDifference(Trackpoint o) {
	return o.time - time;
    } // timeDifference

    // Returns a printable representation of this trackpoint.
    public String toString() {
	return "(" + latitude + ", " + longitude + ") @ " + time;
    } // toString
    
} // class
