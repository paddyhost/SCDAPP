package com.hatchers.solar_conduction_dryer.activity.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.awareness.state.Weather;
import com.hatchers.solar_conduction_dryer.R;
import com.hatchers.solar_conduction_dryer.activity.activity.home.Menus_Fragment;
import com.hatchers.solar_conduction_dryer.activity.scd_menus.whether.Whether_Fragment;
import com.hatchers.solar_conduction_dryer.activity.utils.RuntimePermissions;

public class Menu_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callMenuFragment();

        RuntimePermissions.checkLocationPermission(Menu_Activity.this);

    }

    private void callMenuFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Menus_Fragment menusFragment = new Menus_Fragment();
        fragmentTransaction.replace(R.id.frame, menusFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RuntimePermissions.MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RuntimePermissions.checkReadExternalStoragePermission(Menu_Activity.this);
                               /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                Whether_Fragment menusFragment = new Whether_Fragment();
                                fragmentTransaction.replace(R.id.frame, menusFragment).commit();*/
                            }
                        }, 500);

                       /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Whether_Fragment menusFragment = new Whether_Fragment();
                        fragmentTransaction.replace(R.id.frame, menusFragment).commit();
*/
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Please enable location...",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            case RuntimePermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                RuntimePermissions.checkWriteExternalStoragePermission(Menu_Activity.this);
                               /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                Whether_Fragment menusFragment = new Whether_Fragment();
                                fragmentTransaction.replace(R.id.frame, menusFragment).commit();*/
                            }
                        }, 500);

                       /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Whether_Fragment menusFragment = new Whether_Fragment();
                        fragmentTransaction.replace(R.id.frame, menusFragment).commit();
*/
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Please enable location...",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            case RuntimePermissions.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 500);

                       /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        Whether_Fragment menusFragment = new Whether_Fragment();
                        fragmentTransaction.replace(R.id.frame, menusFragment).commit();
*/
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Please enable location...",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }


    }


}