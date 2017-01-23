package utils;

import android.util.Patterns;

/**
 * Created by mariusz on 23.01.17.
 */
public class ContactsValidation {
    public static boolean validatePhoneNumber(String phoneNumber){
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }
    public static boolean validateKey(String key){
        if(key==null || key.length()<5){
            return false;
        }
        return true;
    }
}
