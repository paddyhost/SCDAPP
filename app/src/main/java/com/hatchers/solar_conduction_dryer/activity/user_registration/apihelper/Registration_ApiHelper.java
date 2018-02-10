package com.hatchers.solar_conduction_dryer.activity.user_registration.apihelper;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Map;

import com.hatchers.solar_conduction_dryer.activity.Preference_Manager.PrefManager;
import com.hatchers.solar_conduction_dryer.activity.app.MyApplication;
import com.hatchers.solar_conduction_dryer.activity.activity.Menu_Activity;
import com.hatchers.solar_conduction_dryer.activity.constants.WebServiceUrls;


public class Registration_ApiHelper
{
    public static boolean newUserRegistration(final Activity activity)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST, WebServiceUrls.urlUserRegistration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responce = new JSONObject(response);
                    if (responce.getString("status").equalsIgnoreCase("sucess")) {
                            new PrefManager(activity).createLogin(new PrefManager(activity).getMobileNumber());
                            new PrefManager(activity).setSkipped_Registration(false);
                            Intent intent = new Intent(activity, Menu_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                    }
                    else
                    {
                          Toast.makeText(activity,"Failed Try again..",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(activity,error.toString(),Toast.LENGTH_SHORT).show();

               }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new Hashtable<String, String>();
                //http://hatchers.in/calender/registration.php?
                // name=vishal&
                // mobileno=99999999&
                // birthdate=20/12/2016&
                // gender=M&
                // city=gangapur2&
                // appname=shivaji

                params.put("mobileno", new PrefManager(activity).getMobileNumber());
                params.put("name", new PrefManager(activity).getUserName());
                params.put("birthdate", new PrefManager(activity).getBirthDate());
                params.put("gender", new PrefManager(activity).getGender());
                params.put("city", new PrefManager(activity).getCity());
                params.put("state",new PrefManager(activity).getState());
                params.put("pincode",new PrefManager(activity).getPincode());
                params.put("country",new PrefManager(activity).getCountry());
                params.put("appname", "SCD");
                //returning parameters
                return params;
            }

        };

        // Adding request to request queue

        MyApplication.getInstance().addToRequestQueue(strReq);
        return true;


    }

}
