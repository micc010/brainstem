/**
 * Copyright 2018 https://github.com/micc010
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gxhl.jts.common.utils;

import lombok.Data;

/**
 * @author roger.li
 * @since 2018/7/24
 */
public class GPSUtils {

    //
    // Krasovsky 1940
    //
    // a = 6378245.0, 1/f = 298.3
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    public static final double EARTH_AXIS_KRASOVSKY = 6378245.0;
    public static final double EARTH_AXIS_KRASOVSKY_ECCENTRICITY = 0.00669342162296594323;

    //
    // WGS84
    //
    // a = 6378137.0, 1/f = 298.257
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    public static final double EARTH_AXIS_WGS84 = 6378137.0;
    public static final double EARTH_AXIS_WGS84_ECCENTRICITY = 0.006739501819472924847;

    public static Point lonlatToMercator(double lat, double lon) {
        double x = lon * 20037508.34 / 180;
        double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
        y = y * 20037508.34 / 180;
        return new Point(x, y);
    }

    public static Point mercatorTolonlat(double lat, double lon) {
        double x = lon / 20037508.34 * 180;
        double y = lat / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
        return new Point(x, y);
    }

    public static Point transform(double wgLat, double wgLon) {
        return transform(wgLat, wgLon, EARTH_AXIS_KRASOVSKY, EARTH_AXIS_KRASOVSKY_ECCENTRICITY);
    }

    // World Geodetic System ==> Mars Geodetic System
    public static Point transform(double wgLat, double wgLon, double axis, double eccentricity) {
        if (outOfChina(wgLat, wgLon)) {
            return new Point(wgLat, wgLon);
        }
        double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
        double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
        double radLat = wgLat / 180.0 * Math.PI;
        double magic = Math.sin(radLat);
        magic = 1 - eccentricity * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((axis * (1 - eccentricity)) / (magic * sqrtMagic) * Math.PI);
        dLon = (dLon * 180.0) / (axis / sqrtMagic * Math.cos(radLat) * Math.PI);
        return new Point(wgLon + dLon, wgLat + dLat);
    }

    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004 || lon > 137.8347)
            return true;
        if (lat < 0.8293 || lat > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * Math.PI) + 40.0 * Math.sin(y / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * Math.PI) + 320 * Math.sin(y * Math.PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * Math.PI) + 20.0 * Math.sin(2.0 * x * Math.PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * Math.PI) + 40.0 * Math.sin(x / 3.0 * Math.PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * Math.PI) + 300.0 * Math.sin(x / 30.0 * Math.PI)) * 2.0 / 3.0;
        return ret;
    }

    @Data
    public static class Point {
        private double x;
        private double y;

        public Point() {
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

}
