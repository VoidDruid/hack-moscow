package com.hackmoskow.mobile.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.presenters.MainController;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicMapActivity extends FragmentActivity {

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION};


    private boolean paused = true;
    private Map map = null;
    private AndroidXMapFragment mapFragment = null;
    private PositioningManager posManager;
    private MainController controller;
    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {

                public void onPositionUpdated(PositioningManager.LocationMethod method,
                                              GeoPosition position, boolean isMapMatched) {
                    if (!paused) {
                        try {
                            map.setCenter(position.getCoordinate(),
                                    Map.Animation.LINEAR);
                            controller.positionChanged(position.getCoordinate());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                public void onPositionFixChanged(PositioningManager.LocationMethod method,
                                                 PositioningManager.LocationStatus status) {
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();

        posManager = PositioningManager.getInstance();
        posManager.addListener(new WeakReference<>(positionListener));
        if (posManager != null) {
            posManager.start(
                    PositioningManager.LocationMethod.GPS_NETWORK);
        }
        controller = new MainController(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        System.out.println("Resume");
        onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("Start");
        onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("Stop");
        onPause();
    }

    private void initialize() {
        setContentView(R.layout.activity_main);

        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);

        mapFragment.init(error -> {
            if (error == OnEngineInitListener.Error.NONE) {
                map = mapFragment.getMap();
                map.getPositionIndicator().setVisible(true);
                map.setCenter(new GeoCoordinate(49.196261, -123.004773, 0.0),
                        Map.Animation.NONE);
                map.setZoomLevel(map.getMaxZoomLevel());
                System.out.println(map.getMaxZoomLevel() + " " + map.getMinZoomLevel());
                System.out.println("Created!");
            } else {
                System.out.println("ERROR: Cannot initialize Map Fragment");
            }
        });
    }

    protected void checkPermissions() {

        final List<String> missingPermissions = new ArrayList<String>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                initialize();
                break;
        }
    }

    public void onResume() {
        super.onResume();
        paused = false;
        if (posManager != null) {
            posManager.start(
                    PositioningManager.LocationMethod.GPS_NETWORK);
        }
    }

    // To pause positioning listener
    public void onPause() {
        if (posManager != null) {
            posManager.stop();
        }
        super.onPause();
        paused = true;
    }

    @SuppressLint("ShowToast")
    public void showLostConnection() {
        Toast.makeText(this, "Lost connection with server.", Toast.LENGTH_SHORT);
    }
}