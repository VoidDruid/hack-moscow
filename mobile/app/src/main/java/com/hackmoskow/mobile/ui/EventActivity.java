package com.hackmoskow.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.ui.main.BasicMapActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventActivity extends Activity {

    @BindView(R.id.title_event)
    TextView titleTextView;

    @BindView(R.id.description_event)
    TextView descriptionTextView;

    private double latitude;
    private double longitude;
    private String title;
    private String description;
    private boolean isModuleOpened;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            isModuleOpened = true;
            Uri uri = intent.getData();
            titleTextView.setText(uri.getQueryParameter("title"));
            String description = uri.getQueryParameter("description");
            descriptionTextView.setText(description);
            if (description == null) {
                descriptionTextView.setVisibility(View.GONE);
            }
            latitude = Double.parseDouble(Objects.requireNonNull(uri.getQueryParameter("latitude")));
            longitude = Double.parseDouble(Objects.requireNonNull(uri.getQueryParameter("longitude")));
            description = uri.getQueryParameter("description");
            title = uri.getQueryParameter("title");

        } else {
            isModuleOpened = false;

            titleTextView.setText(intent.getStringExtra("title"));
            descriptionTextView.setText(intent.getStringExtra("description"));
            if (intent.getStringExtra("description").isEmpty()) {
                descriptionTextView.setVisibility(View.GONE);
            }
            latitude = intent.getDoubleExtra("latitude", 0);
            longitude = intent.getDoubleExtra("longitude", 0);
            description = intent.getStringExtra("description");
            title = intent.getStringExtra("title");
        }
    }

    @OnClick(R.id.rout_btn)
    public void route() {
        if (!isModuleOpened) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("route", true);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            Intent intent = new Intent(this, BasicMapActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        }
    }

    @OnClick(R.id.event_close_btn)
    public void close() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("route", false);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @OnClick(R.id.share_event)
    public void shareEvent() {

        String message = createMessage();

        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        myIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }

    private String createMessage() {
        try {
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                StringBuilder str = new StringBuilder();
                str.append(title);
                str.append("\n\n");

                if (!description.isEmpty()) {
                    str.append(description);
                    str.append("\n\n");
                }

                str.append(addresses.get(0).getAddressLine(0));

                str.append("\n\n________\n");
                str.append("http://app.hackmoscow/gizmos?title=").append(title.replaceAll(" ", "+"))
                        .append("&description=").append(description.replaceAll(" ", "+"))
                        .append("&latitude=").append(latitude)
                        .append("&longitude=").append(longitude);
                str.append("\n________\n\n");

                str.append("Send from application");
                return  str.toString();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
