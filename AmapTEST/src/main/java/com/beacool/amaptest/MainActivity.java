package com.beacool.amaptest;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.beacool.amaptest.model.LocationRecord;
import com.beacool.amaptest.util.LogTool;
import com.beacool.amaptest.util.PermissionUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private boolean isCheckPermissions = false;

    private MapView mMapView;
    private AMap mMap;
    private FrameLayout mLayout;

    private AMapLocationClientOption mAMapLocOption = null;
    private AMapLocationClient mAMapLocClient = null;

    private LocationRecord mRecord;

    private Button btn_start_location;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initUI(savedInstanceState);
    }

    private void initData() {
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mRecord = new LocationRecord();
        mRecord.setSpeed_sec_km(5 * 60);

        mAMapLocClient = new AMapLocationClient(this);
        mAMapLocOption = new AMapLocationClientOption();
        mAMapLocClient.setLocationListener(mAMapLocListener);

        mAMapLocOption.setOnceLocation(false);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mAMapLocOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
        //设置定位间隔,单位毫秒,默认为2000ms
        mAMapLocOption.setInterval(5000);
        mAMapLocOption.setKillProcess(true);
        mAMapLocClient.setLocationOption(mAMapLocOption);

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
    }

    private void initUI(Bundle savedInstanceState) {
        mLayout = (FrameLayout) findViewById(R.id.layout_MapView);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMap = mMapView.getMap();
        mMap.setOnMapLoadedListener(mMapLoadedCallback);
        mMap.getUiSettings().setZoomControlsEnabled(false);  // 去除地图默认的加减号

        findViewById(R.id.btn_Draw_From_Local).setOnClickListener(this);
        findViewById(R.id.btn_start_location).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isCheckPermissions) {
            PermissionUtil.requestPermissions(this,
                    PermissionUtil.makePermission(this, permissions), 2048);
            isCheckPermissions = true;
        }
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        LogTool.LogE(TAG,"onPause");
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogTool.LogE(TAG,"onStop");
        if (isCheckPermissions)
            isCheckPermissions = false;

        mAMapLocClient.stopLocation();

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogTool.LogE(TAG,"onDestroy");
        mAMapLocClient.onDestroy();
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    private AMapLocationListener mAMapLocListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            LogTool.LogE(TAG, "onLocationChanged--->");
            StringBuffer sb = new StringBuffer();
            sb.append("[getAddress]:" + location.getAddress());
            sb.append("\t[getLongitude]:" + location.getLongitude());
            sb.append("\t[getLatitude]:" + location.getLatitude());
            sb.append("\t[getCountry]:" + location.getCountry());
            sb.append("\t[getCity]:" + location.getCity());
            sb.append("\t[getCityCode]:" + location.getCityCode());
            sb.append("\t[getAdCode]:" + location.getAdCode());
            sb.append("\t[getAoiName]:" + location.getAoiName());
            sb.append("\t[getBuildingId]:" + location.getBuildingId());
            sb.append("\t[getDistrict]:" + location.getDistrict());
            sb.append("\t[getPoiName]:" + location.getPoiName());
            sb.append("\t[getStreetNum]:" + location.getStreetNum());
            sb.append("\t[getStreet]:" + location.getStreet());
            sb.append("\t[getFloor]:" + location.getFloor());
            sb.append("\t[getProvider]:" + location.getProvider());
            sb.append("\t[getProvince]:" + location.getProvince());
            sb.append("\t[getAccuracy]:" + location.getAccuracy());
            sb.append("\t[getAltitude]:" + location.getAltitude());
            sb.append("\t[getGpsAccuracyStatus]:" + location.getGpsAccuracyStatus());
            sb.append("\t[getSpeed]:" + location.getSpeed());
            sb.append("\t[getSatellites]:" + location.getSatellites());
            sb.append("\t[getErrorInfo]:" + location.getErrorInfo());
            LogTool.LogE(TAG, sb.toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Draw_From_Local: {
                mRecord.setListPoints(LocationRecord.makePointsFromFile(this, getAssets(), "record" + File.separator + "MyTrance.txt"));
                mRecord.setListPaths(LocationRecord.calculatePathFromPoint(mRecord.getListPoints()));

                if (mRecord.getListPoints() == null || mRecord.getListPoints().isEmpty()) {
                    Toast.makeText(this, "没有坐标数据！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mRecord.getListPaths() == null || mRecord.getListPaths().isEmpty()) {
                    Toast.makeText(this, "没有路径！", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<LatLng> listLatlng = new ArrayList<>();
                List<Integer> listColor = new ArrayList<>();

                for (LocationRecord.LocationPoint point : mRecord.getListPoints()) {
                    LogTool.LogE(TAG, "speed=" + point.getSpeed_m_sec() + " m/s");
                    listLatlng.add(new LatLng(point.getLat(), point.getLon()));
                    listColor.add(point.getSpeed_m_sec() < mRecord.getSpeed_m_sec() ? 0xffff0000 : 0xff00ff00);
                }

                PolylineOptions polylineOptions = new PolylineOptions().addAll(listLatlng).colorValues(listColor).useGradient(true).width(17).visible(true);
                mMap.addPolyline(polylineOptions);

//                for (LatLng latlng : listLatlng) {
//                    mMap.add(new DotOptions().center(latlng).color(0xff0000ff).visible(true));
//                }

                /**
                 * 移动到最后坐标点的坐标
                 */
                CameraPosition pos = CameraPosition.builder().target(
                        new LatLng(mRecord.getEndPoint().getLat(), mRecord.getEndPoint().getLon()))
                        .zoom(17).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
                break;
            }
            case R.id.btn_start_location:
                startLoacation();
                break;
        }
    }

    private AMap.OnMapLoadedListener mMapLoadedCallback = new AMap.OnMapLoadedListener() {
        @Override
        public void onMapLoaded() {
            LogTool.LogE(TAG, "onMapLoaded--->");
            // 启动定位
            mAMapLocClient.startLocation();
        }
    };

    public void startLoacation() {
        if (mAMapLocClient != null) {
            mAMapLocClient.startLocation();

            CameraPosition pos = CameraPosition
                    .builder()
                    .target(new LatLng(31.211218, 121.605676))
                    .zoom(17)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(pos));
        }
    }
}
