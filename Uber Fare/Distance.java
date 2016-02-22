/**
 * Contains static members for computing distances on Earth from
 * geographic coordinates.
 *
 * @author Jim Glenn
 * @version 0.1 2015-07-10
 */

public class Distance {
    
    public static final double EARTH_RADIUS_MILES = 3958.761;

    /* Computes the colatitude of the given latitude.
     *
     * Takes a parameter lat that is a double between -90 and 90 (inclusive) for the latitude,
     * in degrees
     * Returns the colatitude, in radians
     */
    public static double colatitudeRadians(double lat) {
	return Math.toRadians(90 - lat);
    } // colatitudeRadians

    /* Computes the distance between two points on the Earth's surface.
     *
     * Parameter lat1 is a double between -90 and 90 (inclusive) for the latitude of
     * the first point
     * Parameter long1 is a double for the longitude of the first point
     * Parameter lat2 is a double between -90 and 90 (inclusive) for the latitude of
     * the second point
     * Parameter long2 is a double for the longitude of the second point
     * Returns the distance between the points, in miles
     */
    public static double calcDistance(double lat1, double long1, double lat2, double long2) {
	double colat1 = colatitudeRadians(lat1);
	double colat2 = colatitudeRadians(lat2);
	double deltaLong = Math.toRadians(long2 - long1);

	double c = Math.cos(colat1) * Math.cos(colat2) + Math.sin(colat1) * Math.sin(colat2) * Math.cos(deltaLong);

	if (Math.abs(c) > 1.0) {
		// to handle errors arising from the imprecision of doubles
		return 0.0;
	} else {
	    return EARTH_RADIUS_MILES * Math.acos(c);
	}
    } // calcDistance
    
} // class
