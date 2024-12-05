package com.tigran.api.infrastructure.util.devicedata;

/**
 * Created by Tigran Melkonyan
 * Date: 12/4/24
 * Time: 9:46 PM
 */
public final class DeviceDataUtils {

    private DeviceDataUtils() {
    }

    public static boolean isOutOfRange(double value, double min, double max) {
        return value < min || value > max;
    }
}
