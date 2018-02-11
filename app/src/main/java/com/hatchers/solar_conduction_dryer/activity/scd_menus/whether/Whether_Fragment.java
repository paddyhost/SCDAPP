package com.hatchers.solar_conduction_dryer.activity.scd_menus.whether;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.hatchers.solar_conduction_dryer.R;
import com.hatchers.solar_conduction_dryer.activity.constants.WeatherConstants;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Whether_Fragment extends Fragment {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION=111;
    private TextView datTxtLabel,dayTxt,weatherReportTxt,temperatureTxt,humidityTxt,
                     weatherMsgTxt, msgToDryOrNotTxt;
    private ImageView wheatherImg,weatherLikeImg;
    TextView textpalce;
    TextView textweather;

    public Whether_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                weatherReportTxt.setText("Sorry You to Grant GPS permissions");
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)  == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        // locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }
                    weatherReportTxt.setText("Searching...");
                    serachWeather();
                }
                else
                {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }


        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_whether, container, false);
         textweather= (TextView) view.findViewById(R.id.weatherhuminity);
        initiaizations(view);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.weather_greed));
        }


        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        else
        {
            weatherReportTxt.setText("Searching...");

            serachWeather();
        }
        return view;

    }

    private void initiaizations(View view)
    {
        datTxtLabel = (TextView)view.findViewById(R.id.dayw);
        textpalce  =(TextView)view.findViewById(R.id.dayw);
        dayTxt = (TextView)view.findViewById(R.id.day);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        dayTxt.setText(dayOfTheWeek);
        wheatherImg = (ImageView)view.findViewById(R.id.weathericon);
        weatherReportTxt = (TextView)view.findViewById(R.id.wheather);
        temperatureTxt = (TextView)view.findViewById(R.id.temperature);
        weatherLikeImg = (ImageView)view.findViewById(R.id.likeicon);
        weatherMsgTxt = (TextView)view.findViewById(R.id.message);
        msgToDryOrNotTxt = (TextView)view.findViewById(R.id.message2);
        TextView cc= (TextView) view.findViewById(R.id.cc);
        cc.setText(Html.fromHtml("<sup>o</sup>"));
    }

    void serachWeather() {
        final GoogleApiClient client = new GoogleApiClient.Builder(getActivity())
                .addApi(Awareness.API)
                .build();
        client.connect();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Please Enable Location...", Toast.LENGTH_LONG).show();
            return;
        }
        final GoogleApiClient client1 = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(Awareness.API)
                .build();
        client1.connect();
     /*   Awareness.SnapshotApi.getPlaces(client1).setResultCallback(new ResultCallback<PlacesResult>() {
            @Override
            public void onResult(@NonNull PlacesResult placesResult) {
                if (placesResult.getStatus().isSuccess())
                {
                     List<PlaceLikelihood> likehoods=   placesResult.getPlaceLikelihoods();
                     if(likehoods!=null)
                     {
                        for(PlaceLikelihood likelihood:likehoods) {
                            textpalce.setText(likelihood.getPlace().getName()+"");
                        }
                     }
                    else
                     {
                         textpalce.setText("Place can't Identify");

                     }
                }
                else
                {
                    textpalce.setText("Place can't Identify");

                }

               }
        });

*/
        Awareness.SnapshotApi.getLocation(client)
                .setResultCallback(new ResultCallback<LocationResult>() {
                    @Override
                    public void onResult(@NonNull LocationResult locationResult) {
                        Location location = locationResult.getLocation();
                        //  textpalce.setText(location.getTime()+"");

                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            String stateName = addresses.get(0).getLocality();
                            textpalce.setText(stateName);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });


        Awareness.SnapshotApi.getWeather(client)
                .setResultCallback(new ResultCallback<WeatherResult>() {
                    @Override
                    public void onResult(@NonNull WeatherResult weatherResult) {
                        if (!weatherResult.getStatus().isSuccess()) {
                            Log.e("weather", "Could not get weather." + weatherResult.getStatus());
                            Toast.makeText(getContext(), "Could not get weather...", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Weather weather = weatherResult.getWeather();
                        textweather.setText("" + weather.getHumidity());
                        temperatureTxt.setText("" + Html.fromHtml(weather.getTemperature(Weather.CELSIUS) + "<sup>o</sup>C"));

                        float temp=weather.getTemperature(Weather.CELSIUS);
                        if(weather.getHumidity() > WeatherConstants.HUMIDITY_EXCELLENT_MIN &&
                                weather.getHumidity() < WeatherConstants.HUMIDITY_EXCELLENT_MAX &&
                                temp>= WeatherConstants.TEMPERATURE_EXCELLENT_MAX)
                        {
                            wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                            weatherReportTxt.setText("Sunny Environment");
                            weatherMsgTxt.setText("Go Ahead!!!");
                            msgToDryOrNotTxt.setText("Weather Excellent for Drying");
                        }

                        else if(weather.getHumidity() > WeatherConstants.HUMIDITY_GOOD_MIN &&
                                weather.getHumidity() < WeatherConstants.HUMIDITY_GOOD_MAX &&
                                temp > WeatherConstants.TEMPERATURE_GOOD_MIN &&
                                temp < WeatherConstants.TEMPERATURE_GOOD_MAX)
                        {
                            wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                            weatherReportTxt.setText("Sunny Environment");
                            weatherMsgTxt.setText("Go Ahead!!!");
                            msgToDryOrNotTxt.setText("Weather Good for Drying");
                        }


                        else if(weather.getHumidity() > WeatherConstants.HUMIDITY_MODERATE_MIN &&
                                weather.getHumidity() < WeatherConstants.HUMIDITY_MODERATE_MAX &&
                                temp > WeatherConstants.TEMPERATURE_MODERATE_MIN &&
                                temp < WeatherConstants.TEMPERATURE_MODERATE_MAX)
                        {
                            wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                            weatherReportTxt.setText("Cloudy Environment");
                            weatherMsgTxt.setText("Opps!!!");
                            msgToDryOrNotTxt.setText("Weather Moderate for Drying");
                            weatherLikeImg.setRotation(180);
                        }


                        else if(weather.getHumidity() < WeatherConstants.HUMIDITY_NOT_SUITABLE_MIN &&
                                weather.getHumidity() > WeatherConstants.HUMIDITY_NOT_SUITABLE_MAX &&
                                temp <= WeatherConstants.TEMPERATURE_NOTSUITABLE_MAX)
                        {
                            wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                            weatherReportTxt.setText("Rainy Environment");
                            weatherMsgTxt.setText("Oops!!!");
                            msgToDryOrNotTxt.setText("Weather Not Suitable for Drying");
                            weatherLikeImg.setRotation(180);
                        }

                        else
                        {
                            wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                            weatherReportTxt.setText("Unknown Environment");
                            weatherLikeImg.setRotation(180);
                            weatherMsgTxt.setText("Opps!!!");
                            msgToDryOrNotTxt.setText("Not Sutaible for Drying");

                        }
                        /*Weather weather = weatherResult.getWeather();
                        textweather.setText("" + weather.getHumidity());
                        temperatureTxt.setText("" + Html.fromHtml(weather.getTemperature(Weather.CELSIUS) + "<sup>o</sup>C"));
                        int[] conditions = weather.getConditions();
                        for (int i : conditions) {
                            switch (i) {

                                case Weather.CONDITION_CLEAR:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.sun));
                                    weatherReportTxt.setText("Sunny Environment");
                                    weatherMsgTxt.setText("Go Ahead!!!");
                                    msgToDryOrNotTxt.setText("Sutaible for Drying");

                                    break;
                                case Weather.CONDITION_FOGGY:
                                    //wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    weatherReportTxt.setText("Foggy Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");

                                    break;

                                case Weather.CONDITION_CLOUDY:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    weatherReportTxt.setText("Cloudy Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");

                                    break;
                                case Weather.CONDITION_RAINY:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                                    weatherReportTxt.setText("Rainy Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");
                                    break;
                                case Weather.CONDITION_WINDY:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    weatherReportTxt.setText("Windy Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");

                                    break;
                                case Weather.CONDITION_UNKNOWN:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    weatherReportTxt.setText("Unknown Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");
                                    break;
                                case Weather.CONDITION_STORMY:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    weatherReportTxt.setText("Stromy Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");
                                    break;
                                case Weather.CONDITION_HAZY:
                                    wheatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloudyday));
                                    weatherReportTxt.setText("Hazy Environment");
                                    weatherLikeImg.setRotation(180);
                                    weatherMsgTxt.setText("Opps!!!");
                                    msgToDryOrNotTxt.setText("Not Sutaible for Drying");
                                    break;


                            }

                        }*/

                        //     Toast.makeText(getContext(),""+weather.getHumidity(),Toast.LENGTH_LONG).show();
                        //Log.i(TAG, "Weather: " + weather);
                    }
                });

    }

}
