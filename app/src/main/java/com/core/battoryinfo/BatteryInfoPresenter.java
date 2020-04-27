package com.core.battoryinfo;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

class BatteryInfoPresenter {
    BatteryInfoView view;

    BatteryInfoPresenter(BatteryInfoView v) {
        view = v;
    }

    public void getBatteryData(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        context.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
            if (present) {
                ContentValues contentProvider = new ContentValues();
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
                contentProvider.put("health", health + " (" + batteryHealth(health) + ")");

                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPerc = 0;
                if (level != -1 && scale != -1) {
                    batteryPerc = (int) ((level / (float) scale) * 100f);
                }
                contentProvider.put("Percentage", batteryPerc + "%");

                int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                contentProvider.put("Plugged", plugged > 0 ? "Yes" : "No");

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                contentProvider.put("Status", batteryStatus(status));

                String technology = "";
                if (intent.getExtras() != null) {
                    technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
                }
                contentProvider.put("Technology", technology);
                int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                if (temperature > 0) {
                    contentProvider.put("Temperature", ((float) temperature / 10f) + "C");
                } else {
                    contentProvider.put("Temperature", "");
                }

                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
                contentProvider.put("Voltage", voltage + "mV");

                long capacity = getBatteryCapacity(context);
                contentProvider.put("Capacity", capacity);

                view.getBatteryInformation(contentProvider);
            }
        }
    };

    private long getBatteryCapacity(Context context) {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        Long chargeCounter = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
        Long capacity = batteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if (chargeCounter != null && capacity != null) {
            long value = (long) (((float) chargeCounter / (float) capacity) * 100f);
            return value;
        }
        return 0;
    }

    private String batteryStatus(int status) {
        switch (status) {
            case 1:
                return "Unknown";
            case 2:
                return "Charging";
            case 3:
                return "Discharging";
            case 4:
                return "Not Charging";
            case 5:
                return "Full";
            default:
                return "No status";
        }
    }

    private String batteryHealth(int health) {
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                return "Cold";
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "Dead";
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "Good";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "Over head";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "Over Voltage";
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                return "Unknown";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "Unspecified failure";
            default:
                return "No health";
        }
    }
}