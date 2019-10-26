package com.hackmoskow.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hackmoskow.mobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventActivity extends Activity {

    @BindView(R.id.title_event)
    TextView titleTextView;

    @BindView(R.id.description_event)
    TextView descriptionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        titleTextView.setText(intent.getStringExtra("title"));
        descriptionTextView.setText(intent.getStringExtra("description"));
        if (intent.getStringExtra("description").isEmpty()) {
            descriptionTextView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.event_close_btn)
    public void close() {
        finish();
    }
}
