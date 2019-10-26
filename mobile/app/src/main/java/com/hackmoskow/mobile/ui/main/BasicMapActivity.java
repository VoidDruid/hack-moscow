package com.hackmoskow.mobile.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.domain.models.Places;
import com.hackmoskow.mobile.presenters.main.MainController;
import com.hackmoskow.mobile.ui.settings.SettingsActivity;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapState;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BasicMapActivity extends FragmentActivity {

    @BindView(R.id.place_info_rl)
    RelativeLayout placeInfoRelativeLayout;

    @BindView(R.id.image_info)
    ImageView imageInfo;

    @BindView(R.id.category_info_tv)
    TextView categoryInfo;

    @BindView(R.id.distance_info_tv)
    TextView distanceInfo;

    @BindView(R.id.title_info_tv)
    TextView titleInfo;

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
    private GeoPosition centerPosition;
    private boolean isPlacesObtained = false;
    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {

                public void onPositionUpdated(PositioningManager.LocationMethod method,
                                              GeoPosition position, boolean isMapMatched) {
                    System.out.println(paused);
                    if (!paused) {
                        try {
                            map.setCenter(position.getCoordinate(),
                                    Map.Animation.LINEAR);
                            controller.positionChanged(position.getCoordinate());
                            centerPosition = position;

                            if (!isPlacesObtained) {
                                controller.readyForGetPlaces(centerPosition.getCoordinate());
                                isPlacesObtained = true;
                            }
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

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        placeInfoRelativeLayout.setVisibility(View.GONE);

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
        onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onPause();
    }

    private void initialize() {
        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);

        mapFragment.init(error -> {
            if (error == OnEngineInitListener.Error.NONE) {
                map = mapFragment.getMap();
                map.getPositionIndicator().setVisible(true);
                map.setCenter(new GeoCoordinate(49.196261, -123.004773, 0.0),
                        Map.Animation.NONE);
                map.setZoomLevel(map.getMaxZoomLevel());
                setMapGestureListener();
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

    public void showLostConnection() {
        Toast.makeText(this, "Lost connection with server.", Toast.LENGTH_SHORT).show();
    }

    public void showUserDataAlert() {
        String title = "Настройки профиля";
        String message = "Чтобы получать более точные рекомендации, заполните профиль.";
        String button1String = "Позже";
        String button2String = "Сейчас";

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(title);  // заголовок
        ad.setMessage(message); // сообщение
        ad.setPositiveButton(button1String, (dialog, arg1) -> {
        });
        ad.setNegativeButton(button2String, (dialog, arg1) -> showUserSettings());
        ad.setCancelable(true);
        ad.show();
    }

    @OnClick(R.id.settings_btn)
    public void showUserSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.close_btn)
    public void closedInfoPressed() {
        placeInfoRelativeLayout.setVisibility(View.GONE);
    }

    public void showNearestPlaces(List<Places> places) {
        for (Places place : places) {
            MapMarker mapMarker = new MapMarker(place.getCoordinate());
            mapMarker.setTitle(place.getTitle() + "\n" + place.getCategory());
            mapMarker.showInfoBubble();
            map.addMapObject(mapMarker);
        }
    }

    public void showPlaceInfo(Places places) {
        categoryInfo.setText(places.getCategory());
        titleInfo.setText(places.getTitle());
        distanceInfo.setText(String.valueOf(places.getDistance()));
        placeInfoRelativeLayout.setVisibility(View.VISIBLE);
    }

    private void setMapGestureListener() {
        MapGesture.OnGestureListener listener =
                new MapGesture.OnGestureListener.OnGestureListenerAdapter() {
                    @Override
                    public boolean onMapObjectsSelected(List<ViewObject> objects) {
                        for (ViewObject viewObj : objects) {
                            if (viewObj.getBaseType() == ViewObject.Type.USER_OBJECT) {
                                if (((MapObject)viewObj).getType() == MapObject.Type.MARKER) {
                                    MapMarker marker = (MapMarker)viewObj;
                                    controller.placePressed(marker.getCoordinate().getLatitude(), marker.getCoordinate().getLongitude());
                                }
                            }
                        }
                        // return false to allow the map to handle this callback also
                        return false;
                    }
                };
        mapFragment.getMapGesture().addOnGestureListener(listener);
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
        if (!paused && centerPosition != null) {
            map.setCenter(centerPosition.getCoordinate(), Map.Animation.LINEAR);
        }
    }
}