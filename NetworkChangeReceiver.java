package com.example.sakthivel.retrofitapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import static com.example.sakthivel.retrofitapp.MainActivity.dialog;


public class NetworkChangeReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
         /*   if (isOnline(context)) {
                dialog(true);
                Log.e("keshav", "Online Connect Intenet ");
            } else {
                dialog(false);
                Log.e("keshav", "Conectivity Failure !!! ");
            }*/

            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                    //Toast.makeText(this, "It's Mobile Network", Toast.LENGTH_SHORT).show();
                    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                    int strength = telephonyManager.getSignalStrength().getGsmSignalStrength();

                    if (strength > 30) {
                        dialog(true, "Good Signal");
                    } else if (strength > 20 && strength < 30) {
                        dialog(true, "Average Signal");
                    } else if (strength < 20 && strength > 3) {
                        dialog(true, "Weak Signal");
                    } else if (strength < 3) {
                        dialog(true, "Very Weak Signal");
                    }
                } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                    //Toast.makeText(this, "It's Wifi Network", Toast.LENGTH_SHORT).show();
                    WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                    int rssi = wifiManager.getConnectionInfo().getRssi();
                    int level = WifiManager.calculateSignalLevel(rssi, 5);
                    if (rssi >= -50)
                        dialog(true, "Excellent Network");
                    else if (rssi < -50 && rssi >= -60)
                        dialog(true, "Good Network");
                    else if (rssi < -60 && rssi >= -70)
                        dialog(true, "Fair Network");
                    else if (rssi < -70)
                        dialog(true, "Weak Network");
                }
            }
            else{
                dialog(false, "No Internet Connection");
            }



        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
