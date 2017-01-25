package model;

import com.orm.SugarRecord;

/**
 * Created by mariusz on 22.01.17.
 */
public class Message extends SugarRecord{
    String cipher;
    Contact contact;
    boolean isYou;

    public Message() {
    }

    public Message(String cipher, Contact contact, boolean isYou) {
        this.cipher = cipher;
        this.contact = contact;
        this.isYou = isYou;
    }

    public String getCipher() {
        return cipher;
    }

    public boolean isYou() {
        return isYou;
    }

    public void setYou(boolean you) {
        isYou = you;
    }

    public void setCipher(String cipher) {
        this.cipher = cipher;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
