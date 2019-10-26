package com.hackmoskow.mobile.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.domain.models.Event;
import com.hackmoskow.mobile.domain.models.Places;
import com.hackmoskow.mobile.domain.repository.CategoriesRepository;
import com.hackmoskow.mobile.presenters.main.MainController;
import com.hackmoskow.mobile.ui.EventActivity;
import com.hackmoskow.mobile.ui.settings.SettingsActivity;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BasicMapActivity extends FragmentActivity {

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION};
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
    @BindView(R.id.categories_btn)
    Spinner spinner;
    private boolean paused = true;
    private Map map = null;
    private AndroidXMapFragment mapFragment = null;
    private PositioningManager posManager;
    private MainController controller;
    private GeoPosition centerPosition;
    private boolean isPlacesObtained = false;
    private List<MapObject> markers;
    private GeoCoordinate lastEventCoordinate;
    private List<CategoriesRepository.Category> categories;
    private MapRoute mapRoute;
    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {

                public void onPositionUpdated(PositioningManager.LocationMethod method,
                                              GeoPosition position, boolean isMapMatched) {
                    if (!paused) {
                        try {
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
        categories = new ArrayList<>();
        markers = new ArrayList<>();
        lastEventCoordinate = new GeoCoordinate(0, 0);
        mapRoute = new MapRoute();
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
                map.setCenter(new GeoCoordinate(55.814297, 37.57504, 0.0),
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
        map.removeMapObjects(markers);
        markers.clear();
        for (Places place : places) {
            MapMarker mapMarker = new MapMarker(place.getCoordinate());
            mapMarker.setTitle("Place");
            mapMarker.showInfoBubble();
            map.addMapObject(mapMarker);
            markers.add(mapMarker);
        }
    }

    @OnClick(R.id.nav_btn)
    public void searchMe() {
        map.setCenter(centerPosition.getCoordinate(), Map.Animation.LINEAR);
        map.setZoomLevel(10);
    }

    public void showEvents(List<Event> events) {
        for (Event event : events) {
            MapMarker mapMarker = new MapMarker(new GeoCoordinate(event.getLat(), event.getLongitude()));
            mapMarker.setTitle("Event");
            try {
                Image icon = new Image();
                icon.setImageResource(R.drawable.attention);
                mapMarker.setIcon(icon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapMarker.showInfoBubble();
            map.addMapObject(mapMarker);
        }
    }

    public void setCategories(List<CategoriesRepository.Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);

        List<String> catForAdapter = new ArrayList<>();
        for (CategoriesRepository.Category category : categories) {
            catForAdapter.add(category.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, catForAdapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                controller.categoriesSelected(categories.get(selectedItemPosition));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void showPlaceInfo(Places places) {
        categoryInfo.setText(places.getCategory());
        titleInfo.setText(places.getTitle());
        distanceInfo.setText(String.valueOf(places.getDistance()));
        placeInfoRelativeLayout.setVisibility(View.VISIBLE);
    }

    public void showEventInfo(Event event) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("title", event.getTitle());
        intent.putExtra("description", event.getDescription());
        intent.putExtra("latitude", event.getLat());
        intent.putExtra("longitude", event.getLongitude());

        lastEventCoordinate = new GeoCoordinate(event.getLat(), event.getLongitude());

        startActivityForResult(intent, 100);
    }

    private void setMapGestureListener() {
        MapGesture.OnGestureListener listener =
                new MapGesture.OnGestureListener.OnGestureListenerAdapter() {
                    @Override
                    public boolean onMapObjectsSelected(List<ViewObject> objects) {
                        for (ViewObject viewObj : objects) {
                            if (viewObj.getBaseType() == ViewObject.Type.USER_OBJECT) {
                                if (((MapObject) viewObj).getType() == MapObject.Type.MARKER) {
                                    MapMarker marker = (MapMarker) viewObj;
                                    controller.placePressed(marker.getCoordinate().getLatitude(), marker.getCoordinate().getLongitude());
                                    return true;
                                }
                            }
                        }
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

    public void showRout(GeoCoordinate from, GeoCoordinate to) {
        map.removeMapObject((MapObject)mapRoute);
        RouteManager rm = new RouteManager();

        RoutePlan routePlan = new RoutePlan();
        routePlan.addWaypoint(from);
        routePlan.addWaypoint(to);

        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);

        routePlan.setRouteOptions(routeOptions);

        rm.calculateRoute(routePlan, new RouteListener());
    }

    private class RouteListener implements RouteManager.Listener {

        // Method defined in Listener
        public void onProgress(int percentage) {
            // Display a message indicating calculation progress
        }

        // Method defined in Listener
        public void onCalculateRouteFinished(RouteManager.Error error, List<RouteResult> routeResult) {
            // If the route was calculated successfully
            if (error == RouteManager.Error.NONE) {
                // Render the route on the map
                mapRoute = new MapRoute(routeResult.get(0).getRoute());
                map.addMapObject(mapRoute);
            }
            else {
                // Display a message indicating route calculation failure
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (100) : {
                if (resultCode == Activity.RESULT_OK) {
                    boolean isRoute = data.getBooleanExtra("route", false);
                    if (isRoute) {
                        showRout(centerPosition.getCoordinate(), lastEventCoordinate);
                    }
                }
                break;
            }
        }
    }
}