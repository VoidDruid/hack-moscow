package com.hackmoskow.mobile.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hackmoskow.mobile.R;
import com.hackmoskow.mobile.domain.models.UserData;
import com.hackmoskow.mobile.presenters.settings.SettingsController;
import com.hackmoskow.mobile.ui.main.BasicMapActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends Activity {

    @BindView(R.id.male_cb)
    CheckBox maleCheckBox;

    @BindView(R.id.female_cb)
    CheckBox femaleCheckBox;

    @BindView(R.id.other_cb)
    CheckBox otherCheckBox;

    @BindView(R.id.age_et)
    EditText ageEditText;

    @BindView(R.id.error_tv)
    TextView errorTextView;

    SettingsController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        controller = new SettingsController(this);

        maleCheckBox.setOnClickListener(v -> checkBoxPressed(maleCheckBox));
        femaleCheckBox.setOnClickListener(v -> checkBoxPressed(femaleCheckBox));
        otherCheckBox.setOnClickListener(v -> checkBoxPressed(otherCheckBox));
    }

    public void showSettings(UserData userData) {
        if (userData.getSex().equals("Male")) {
            maleCheckBox.setChecked(true);
        } else if (userData.getSex().equals("Female")) {
            femaleCheckBox.setChecked(true);
        } else {
            otherCheckBox.setChecked(true);
        }
        ageEditText.setText(String.valueOf(userData.getAge()));
    }

    private void checkBoxPressed(CheckBox checkBox) {
        maleCheckBox.setChecked(false);
        femaleCheckBox.setChecked(false);
        otherCheckBox.setChecked(false);

        checkBox.setChecked(true);
    }

    @OnClick(R.id.save_btn)
    void saveButtonPressed() {
        if (maleCheckBox.isChecked() || femaleCheckBox.isChecked() || otherCheckBox.isChecked()) {
            controller.saveSettingsPressed(maleCheckBox.isChecked() ? "Male" : femaleCheckBox.isChecked() ? "Female" : "Other", ageEditText.getText().toString());
        }
    }

    public void showAgeError() {
        errorTextView.setVisibility(View.VISIBLE);
    }

    public void close() {
        finish();
    }

    public void showSavingError() {
        Toast.makeText(getBaseContext(), "Connection error", Toast.LENGTH_SHORT).show();
    }
}
