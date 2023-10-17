package com.example.openweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText etZipCode;
    Button btnGetWeather;
    TextView txtLatitude,txtLongitude, txtCity, txtTime1, txtTemp1, txtDescription1, txtTime2, txtTemp2, txtDescription2, txtTime3, txtTemp3, txtDescription3, txtTime4, txtTemp4, txtDescription4;
    ImageView imgIcon1, imgIcon2, imgIcon3, imgIcon4;
    String lat, lang, city;
    JSONObject data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etZipCode = findViewById(R.id.etZipCode);
        btnGetWeather = findViewById(R.id.btnGetWeather);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        txtCity = findViewById(R.id.txtCity);

        txtTime1 = findViewById(R.id.txtTime1);
        txtTemp1 = findViewById(R.id.txtTemp1);
        txtDescription1 = findViewById(R.id.txtDescription1);
        imgIcon1 = findViewById(R.id.imgIcon1);

        txtTime2 = findViewById(R.id.txtTime2);
        txtTemp2 = findViewById(R.id.txtTemp2);
        txtDescription2 = findViewById(R.id.txtDescription2);
        imgIcon2 = findViewById(R.id.imgIcon2);

        txtTime3 = findViewById(R.id.txtTime3);
        txtTemp3 = findViewById(R.id.txtTemp3);
        txtDescription3 = findViewById(R.id.txtDescription3);
        imgIcon3 = findViewById(R.id.imgIcon3);

        txtTime4 = findViewById(R.id.txtTime4);
        txtTemp4 = findViewById(R.id.txtTemp4);
        txtDescription4 = findViewById(R.id.txtDescription4);
        imgIcon4 = findViewById(R.id.imgIcon4);

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zipcode = etZipCode.getText().toString();
                GeocodingLocation geocodingLocation = new GeocodingLocation();
                geocodingLocation.getAddressFromZipCode(zipcode, MainActivity.this, new GeocoderHandler());


            }
        });
    }

    public class GeocoderHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 1){
                Bundle bundle = msg.getData();
                lat = bundle.getString("latitude");
                lang = bundle.getString("longitude");
                city = bundle.getString("city");
                txtLatitude.setText("Latitude: "+lat);
                txtLongitude.setText("Longitude: "+lang);
                txtCity.setText("City: "+city);

                if(lat != null && lang != null){
                    getWeather();
                }
            }
            else{
                Bundle bundle = msg.getData();
                String error = bundle.getString("error");
                Toast.makeText(MainActivity.this, "Error:-----"+error, Toast.LENGTH_LONG).show();
                Log.e("Tag", "Unable to get data");
            }



        }
    }

    public void getWeather(){
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                try{
                    URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lang + "&exclude=daily,minutely,current,alerts&units=imperial&appid=4abf88c1cd7e9619bd132416ac640656");

                    HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuffer json = new StringBuffer(1024);
                    String temp = null;

                    while ((temp = bufferedReader.readLine()) != null)
                        json.append(temp).append("\n");
                        bufferedReader.close();
                        data = new JSONObject(json.toString());


                }
                catch (Exception e){
                    Log.e("Tag", "Error in getWeather method"+e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if(data != null){
                    Log.d("Tag","My Weather data:---"+data.toString());
                    weatherOne();
                    weatherTwo();
                    weatherThree();
                    weatherFour();

                }
                super.onPostExecute(o);
            }
        }.execute();

    }

    public void weatherOne(){
        try{
            JSONArray jsonArrayOne = data.getJSONArray("hourly");
            JSONObject jsonObjectOne = jsonArrayOne.getJSONObject(0);
            String time = jsonObjectOne.getString("dt");
            String temp = jsonObjectOne.getString("temp");

            JSONArray jsonArrayTwo = jsonObjectOne.getJSONArray("weather");
            JSONObject jsonObjectTwo = jsonArrayTwo.getJSONObject(0);
            String description = jsonObjectTwo.getString("description");
            String icon = jsonObjectTwo.getString("icon");
            String url = "https://openweathermap.org/img/w/" + icon + ".png";

            long date = Long.valueOf(time) *10000;

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            Date dt = new Date(date);

            txtTime1.setText(dateFormat.format(dt));
            txtTemp1.setText(temp+" "+"F");
            txtDescription1.setText(description);
            Glide.with(MainActivity.this).load(url).into(imgIcon1);


        }
        catch(Exception e){
            Log.e("Tag","Error in weatherOne"+e);
        }
    }


    public void weatherTwo(){
        try{
            JSONArray jsonArrayOne = data.getJSONArray("hourly");
            JSONObject jsonObjectOne = jsonArrayOne.getJSONObject(1);
            String time = jsonObjectOne.getString("dt");
            String temp = jsonObjectOne.getString("temp");

            JSONArray jsonArrayTwo = jsonObjectOne.getJSONArray("weather");
            JSONObject jsonObjectTwo = jsonArrayTwo.getJSONObject(0);
            String description = jsonObjectTwo.getString("description");
            String icon = jsonObjectTwo.getString("icon");
            String url = "https://openweathermap.org/img/w/" + icon + ".png";

            long date = Long.valueOf(time) *10000;

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            Date dt = new Date(date);

            txtTime2.setText(dateFormat.format(dt));
            txtTemp2.setText(temp+" "+"F");
            txtDescription2.setText(description);
            Glide.with(MainActivity.this).load(url).into(imgIcon2);


        }
        catch(Exception e){
            Log.e("Tag","Error in weatherTwo"+e);
        }
    }


    public void weatherThree(){
        try{
            JSONArray jsonArrayOne = data.getJSONArray("hourly");
            JSONObject jsonObjectOne = jsonArrayOne.getJSONObject(2);
            String time = jsonObjectOne.getString("dt");
            String temp = jsonObjectOne.getString("temp");

            JSONArray jsonArrayTwo = jsonObjectOne.getJSONArray("weather");
            JSONObject jsonObjectTwo = jsonArrayTwo.getJSONObject(0);
            String description = jsonObjectTwo.getString("description");
            String icon = jsonObjectTwo.getString("icon");
            String url = "https://openweathermap.org/img/w/" + icon + ".png";

            long date = Long.valueOf(time) *10000;

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            Date dt = new Date(date);

            txtTime3.setText(dateFormat.format(dt));
            txtTemp3.setText(temp+" "+"F");
            txtDescription3.setText(description);
            Glide.with(MainActivity.this).load(url).into(imgIcon3);


        }
        catch(Exception e){
            Log.e("Tag","Error in weatherThree"+e);
        }
    }


    public void weatherFour(){
        try{
            JSONArray jsonArrayOne = data.getJSONArray("hourly");
            JSONObject jsonObjectOne = jsonArrayOne.getJSONObject(3);
            String time = jsonObjectOne.getString("dt");
            String temp = jsonObjectOne.getString("temp");

            JSONArray jsonArrayTwo = jsonObjectOne.getJSONArray("weather");
            JSONObject jsonObjectTwo = jsonArrayTwo.getJSONObject(0);
            String description = jsonObjectTwo.getString("description");
            String icon = jsonObjectTwo.getString("icon");
            String url = "https://openweathermap.org/img/w/" + icon + ".png";

            long date = Long.valueOf(time)*10000;

            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
            Date dt = new Date(date);

            txtTime4.setText(dateFormat.format(dt));
            txtTemp4.setText(temp+" "+"F");
            txtDescription4.setText(description);
            Glide.with(MainActivity.this).load(url).into(imgIcon4);


        }
        catch(Exception e){
            Log.e("Tag","Error in weatherFour"+e);
        }
    }

}