package com.core.battoryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.TextView;

public class BatteryInfoActivity extends AppCompatActivity implements BatteryInfoView {
    private TextView textViewBatteryPrct;
    private TextView textViewBatteryHealth;
    private TextView textViewBatteryPlugged;
    private TextView textViewBatteryStatus;
    private TextView textViewBatteryTechnology;
    private TextView textViewBatteryTemperature;
    private TextView textViewBatteryVoltage;
    private TextView textViewBatteryCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        BatteryInfoPresenter batteryInfoPresenter = new BatteryInfoPresenter(this);
        batteryInfoPresenter.getBatteryData(this);
    }

    private void initView() {
        textViewBatteryPrct = findViewById(R.id.textViewBatteryPrct);
        textViewBatteryHealth = findViewById(R.id.textViewBatteryHealth);
        textViewBatteryPlugged = findViewById(R.id.textViewBatteryPlugged);
        textViewBatteryStatus = findViewById(R.id.textViewBatteryStatus);
        textViewBatteryTechnology = findViewById(R.id.textViewBatteryTechnology);
        textViewBatteryTemperature = findViewById(R.id.textViewBatteryTemperature);
        textViewBatteryVoltage = findViewById(R.id.textViewBatteryVoltage);
        textViewBatteryCapacity = findViewById(R.id.textViewBatteryCapacity);
    }

    @Override
    public void getBatteryInformation(ContentValues contentValues) {
        textViewBatteryPrct.setText(String.format(":  %s", contentValues.getAsString("Percentage")));
        textViewBatteryHealth.setText(String.format(":  %s", contentValues.getAsString("health")));
        textViewBatteryPlugged.setText(String.format(":  %s", contentValues.getAsString("Plugged")));
        textViewBatteryStatus.setText(String.format(":  %s", contentValues.getAsString("Status")));
        textViewBatteryTechnology.setText(String.format(":  %s", contentValues.getAsString("Technology")));
        textViewBatteryTemperature.setText(String.format(":  %s", contentValues.getAsString("Temperature")));
        textViewBatteryVoltage.setText(String.format(":  %s", contentValues.getAsString("Voltage")));
        textViewBatteryCapacity.setText(String.format(":  %s", contentValues.getAsString("Capacity")));
    }
}
