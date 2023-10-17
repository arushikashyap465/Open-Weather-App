package com.example.openweatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GeocodingLocation {

    public static void getAddressFromZipCode(final String locationAddress, final Context context, final Handler handler){
        Thread thread = new Thread(){
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                double lang = 0, lat = 0;
                String city = null, strLat = null, strLang = null;

                try{
                    List addressList = geocoder.getFromLocationName(locationAddress, 1);
                    if(addressList != null && addressList.size()> 0){
                        Address address = (Address) addressList.get(0);
                        lang = address.getLongitude();
                        lat = address.getLatitude();
                        city = address.getLocality();
                        strLat = String.valueOf(lat);
                        strLang = String.valueOf(lang);
                    }
                }
                catch(IOException e){
                    Log.e("Tag", "Unable to connect to Geocoder", e);
                }
                finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);

                    if(strLat != null && strLang != null){
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("longitude", String.valueOf(lang));
                        bundle.putString("latitude", String.valueOf(lat));
                        bundle.putString("city", city);
                        message.setData(bundle);

                    }
                    else{
                        message.what = 2;
                        Bundle bundle = new Bundle();
                        bundle.putString("error", "Unable to get latitude and longitude for given location");
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }

            }
        };
        thread.start();
    }

}
