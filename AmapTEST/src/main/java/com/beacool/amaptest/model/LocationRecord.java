package com.beacool.amaptest.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Package com.beacool.baidumaptest.model.
 * Created by JoshuaYin on 2016/11/23.
 * <p>
 * Description:
 */

public class LocationRecord {
    private final String TAG = this.getClass().getSimpleName();

    private List<LocationPoint> listPoints;
    private List<LocationPath> listPaths;
    private LocationPoint startPoint, endPoint;
    private float speed_sec_km = 0.f;
    private float speed_m_sec = 0.f;

    public LocationPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(LocationPoint endPoint) {
        this.endPoint = endPoint;
    }

    public List<LocationPath> getListPaths() {
        return listPaths;
    }

    public void setListPaths(List<LocationPath> listPaths) {
        this.listPaths = listPaths;
    }

    public List<LocationPoint> getListPoints() {
        return listPoints;
    }

    public void setListPoints(List<LocationPoint> listPoints) {
        this.listPoints = listPoints;
        if (listPoints != null && !listPoints.isEmpty()) {
            startPoint = listPoints.get(0);
            if (listPoints.size() > 1)
                endPoint = listPoints.get(listPoints.size() - 1);
        }
    }

    public float getSpeed_sec_km() {
        return speed_sec_km;
    }

    public void setSpeed_sec_km(float speed_sec_km) {
        this.speed_sec_km = speed_sec_km;
        speed_m_sec = 1000.f / this.speed_sec_km;
    }

    public float getSpeed_m_sec() {
        return speed_m_sec;
    }

    public LocationPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(LocationPoint startPoint) {
        this.startPoint = startPoint;
    }

    public static class LocationPoint {
        private double lat = 0.d;
        private double lon = 0.d;
        private long time = 0;
        private float speed_m_sec = 0.f;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public float getSpeed_m_sec() {
            return speed_m_sec;
        }

        public void setSpeed_m_sec(float speed_m_sec) {
            this.speed_m_sec = speed_m_sec;
        }
    }

    public static class LocationPath {
        private float speed = 0.f;
        private double distance = 0.f;
        private int color = 0xff000000;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }
    }

    public static List<LocationPoint> makePointsFromFile(Context context, AssetManager assetManager, String path) {
        if (assetManager == null || TextUtils.isEmpty(path))
            return null;

        List<LocationPoint> res = null;
        InputStream input = null;
        InputStreamReader reader = null;
        BufferedReader bufReader = null;

        try {
            input = assetManager.open(path);
            reader = new InputStreamReader(input);
            bufReader = new BufferedReader(reader);
            String line = "";
            long now = System.currentTimeMillis();
            res = new ArrayList<>();
            while ((line = bufReader.readLine()) != null) {
                JSONObject json = new JSONObject(line);
                LocationPoint point = new LocationPoint();
                LatLng latlng = new CoordinateConverter(context).from(CoordinateConverter.CoordType.BAIDU)
                        .coord(new LatLng(json.optDouble("lat"), json.optDouble("lon")))
                        .convert();
                point.setLat(latlng.latitude);
                point.setLon(latlng.longitude);
                point.setTime(now);
                now += 5000;
                res.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufReader != null) {
                    bufReader.close();
                    bufReader = null;
                }
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
                if (input != null) {
                    input.close();
                    input = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public static List<LocationPath> calculatePathFromPoint(List<LocationPoint> points) {
        if (points == null || points.isEmpty())
            return null;

        List<LocationPath> res = new ArrayList<>();
        int len = points.size();
        if (len < 2)
            return res;

        LocationPoint prePoint = points.get(0);
        for (int i = 1; i < len; i++) {
            LocationPoint point = points.get(i);
            LocationPath path = new LocationPath();

            double distance = AMapUtils.calculateLineDistance(new LatLng(point.getLat(), point.getLon()),
                    new LatLng(prePoint.getLat(), prePoint.getLon()));

            path.setDistance(distance);
            path.setSpeed((float) (distance / ((point.getTime() - prePoint.getTime()) / 1000)));
            point.setSpeed_m_sec(path.getSpeed());
            prePoint = point;

            res.add(path);
        }

        return res;
    }
}
