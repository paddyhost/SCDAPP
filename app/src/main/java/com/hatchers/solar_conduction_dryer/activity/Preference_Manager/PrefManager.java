package com.hatchers.solar_conduction_dryer.activity.Preference_Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    public static int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREF_NAME = "Solar_Conduction_Dryer";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String MOBILE_NUMBER = "mobile_number";
    private static final String USER_NAME = "username";
    private static final String GENDER = "gender";
    private static final String BIRTH_DATE = "birth_date";
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String STATE = "state";
    private static final String PINCODE = "pincode";
    private static final String Skipped_Registration = "skipped";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_IS_LOGGED_OUT = "isLoggedOut";



    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void setMobileNumber(String mobileNumber) {
        editor.putString(MOBILE_NUMBER, mobileNumber);
        editor.commit();
    }

    public String getMobileNumber() {
        return pref.getString(MOBILE_NUMBER, null);
    }

    public void setGender(String gender) {
        editor.putString(GENDER, gender);
        editor.commit();
    }

    public String getGender() {
        return pref.getString(GENDER, null);
    }

    public void setUserName(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(USER_NAME, null);
    }

    public void setBirthDate(String birthDate) {
        editor.putString(BIRTH_DATE, birthDate);
        editor.commit();
    }

    public String getBirthDate() {
        return pref.getString(BIRTH_DATE, null);
    }



    public String getCountry() {
        return pref.getString(COUNTRY, null);
    }


    public void setCountry(String country) {
        editor.putString(COUNTRY, country);
        editor.commit();
    }

    public String getCity() {
        return pref.getString(CITY, null);
    }


    public void setCity(String city) {
        editor.putString(CITY, city);
        editor.commit();
    }


    public String getState() {
        return pref.getString(STATE, null);
    }


    public void setState(String state) {
        editor.putString(STATE, state);
        editor.commit();
    }


    public String getPincode() {
        return pref.getString(PINCODE, null);
    }


    public void setPincode(String pincode) {
        editor.putString(PINCODE, pincode);
        editor.commit();
    }


    public void createLogin(String mobile) {

        editor.putString(MOBILE_NUMBER, mobile);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }


    public boolean isLoggedIn() {

        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public boolean isLoggedOut() {
        return pref.getBoolean(KEY_IS_LOGGED_OUT, false);
    }

    public void setLoggedOut()
    {
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.commit();
    }

    public void setSkipped_Registration(boolean skipped_registration)
    {
        editor.putBoolean(Skipped_Registration, skipped_registration);
        editor.commit();
    }

    public boolean isSkippedRegistration() {

        return pref.getBoolean(Skipped_Registration, false);
    }

}
