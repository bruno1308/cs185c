package edu.sjsu.cs185c.util;

public class MathUtil {
    public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2)
    {
    	double dx = x1 - x2;
    	double dy = y1 - y2;
    	double dz = z1 - z2;

      // We should avoid Math.pow or Math.hypot due to perfomance reasons
      return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

}
