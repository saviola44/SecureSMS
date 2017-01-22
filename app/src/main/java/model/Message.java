package model;

/**
 * Created by mariusz on 22.01.17.
 */
public class Message {
    String cipher;
    Contact contact;

    public Message() {
    }

    public Message(String cipher, Contact contact) {
        this.cipher = cipher;
        this.contact = contact;
    }

    public String getCipher() {
        return cipher;
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
