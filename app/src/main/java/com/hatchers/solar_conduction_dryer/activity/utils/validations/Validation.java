package com.hatchers.solar_conduction_dryer.activity.utils.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nikam on 04/02/2018.
 */

public class Validation {

   public static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    public static boolean isValidName(String name) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(name);
        return ms.matches();
    }

    public static boolean isValidAdharNumber(String adhar_number) {
        String regEx = "^[0-9]{12}$";
        return adhar_number.matches(regEx);
    }


    public static boolean isValidPincode(String pincode) {
        String regEx = "^[0-9]{6}$";
        return pincode.matches(regEx);
    }
}
