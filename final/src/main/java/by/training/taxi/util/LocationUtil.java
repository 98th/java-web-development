package by.training.taxi.util;

import java.util.Random;

public class LocationUtil {


    public static double generateLatitude() {
        Random generator = new Random();
        double output = 0.47 + (0.48 - 0.47 ) * generator.nextDouble();
        output = Double.parseDouble(String.format("%.9f", output).replace(',', '.'));
        return output;
    }

    public static double generateLongitude() {
        Random generator = new Random();
        double output = 0.94 + (1.01 - 0.94 ) * generator.nextDouble();
        output = Double.parseDouble(String.format("%.9f", output).replace(',', '.'));
        return output;
    }

    public static String getRandomLocation() {
        double latitude = LocationUtil.generateLatitude();
        double longitude = LocationUtil.generateLongitude();
        return LocationUtil.split(latitude, longitude);
    }

    public static String split(double latitude, double longitude) {
        return latitude + ";" + longitude;
    }

}
