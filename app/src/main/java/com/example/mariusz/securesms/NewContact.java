package com.example.mariusz.securesms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.Contact;
import utils.ContactsValidation;

/**
 * Created by mariusz on 23.01.17.
 */
public class NewContact extends AppCompatActivity {
    @Bind(R.id.contactNameET)
    EditText contactNameET;
    @Bind(R.id.phoneNumberET)
    EditText phoneNumberET;
    @Bind(R.id.yoursKeyET)
    EditText yoursKeyET;
    @Bind(R.id.phoneNumberPrefixET)
    EditText phoneNumberPrefixET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_contact_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cancelBtn)
    public void onClickedCancelBtn(){
        finish();
    }

    @OnClick(R.id.saveBtn)
    public void onClickedSaveBtn(){
        if(validate()){
            String phoneNumberStr = phoneNumberET.getText().toString();
            String key = yoursKeyET.getText().toString();
            String contactName = contactNameET.getText().toString();
            String prefix = phoneNumberPrefixET.getText().toString();
            if(prefix==null || prefix.equals("")){
                prefix = getString(R.string._48);
            }
            phoneNumberStr = prefix + phoneNumberStr;
            Contact contact = new Contact(contactName, phoneNumberStr);
            List<Contact> contacts = Contact.find(Contact.class, "phone = ?", phoneNumberStr);
            if(contacts.size()>0){
                Toast.makeText(this, R.string.contact_exist, Toast.LENGTH_LONG).show();
            }
            else{
                contact.save();
                final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                prefs.edit().putString(contact.getPhone(), key).commit();
            }
        }
    }

    private boolean validate(){
        String phoneNumberStr = phoneNumberET.getText().toString();
        if(!ContactsValidation.validatePhoneNumber(phoneNumberStr)){
            Toast.makeText(this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
            return false;
        }
        String key = yoursKeyET.getText().toString();
        if(!ContactsValidation.validateKey(key)){
            Toast.makeText(this, R.string.invalid_key, Toast.LENGTH_LONG).show();
            return false;
        }
        String contactName = contactNameET.getText().toString();
        if(contactName==null || contactName.equals("")){
            Toast.makeText(this, R.string.invalid_contact_name, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
