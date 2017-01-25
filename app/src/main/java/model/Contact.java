package model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

/**
 * Created by mariusz on 22.01.17.
 */

public class Contact extends SugarRecord implements Parcelable{
    Long id;
    String name;
    String phone;

    public Contact() {
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }


    protected Contact(Parcel in) {
        id = in.readLong();
        name = in.readString();
        phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Message> getMessagesByPhone(){
        List<Message> messages = Message.find(Message.class, "contact = ?", "" + id);
        return messages;
    }

    public static Contact getContactByPhone(String phone){
        List<Contact> contact = Contact.find(Contact.class, "phone = ?",  phone);
        if(contact.size()==1){
            return contact.get(0);
        }
        else{
            return null;
        }
    }

}
