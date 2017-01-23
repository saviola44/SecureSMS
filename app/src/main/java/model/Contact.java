package model;

import com.orm.SugarRecord;

/**
 * Created by mariusz on 22.01.17.
 */
public class Contact extends SugarRecord {
    String name;
    String phoneNumber;
    String key;

    public Contact() {
    }

    public Contact(String name, String phoneNumber, String key) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
