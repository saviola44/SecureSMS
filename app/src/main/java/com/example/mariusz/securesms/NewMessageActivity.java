package com.example.mariusz.securesms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import adapter.ContactAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.Contact;
import model.Message;
import utils.ContactsValidation;
import utils.CryptoUtil;

/**
 * Created by mariusz on 22.01.17.
 */
public class NewMessageActivity extends AppCompatActivity {
    @Bind(R.id.phone)
    EditText phoneNumberET;
    @Bind(R.id.keyET)
    TextView keyET;
    @Bind(R.id.secretMessageET)
    EditText secretMessageET;
    @Bind(R.id.add_from_contact)
    ImageView add_from_contact;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_msg_layout);
        ButterKnife.bind(this);
        Contact contact = getIntent().getParcelableExtra("contact");
        if(contact!=null){
            phoneNumberET.setText(contact.getPhone());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String key = prefs.getString(contact.getPhone(), "secret");
            keyET.setText(key);
            secretMessageET.requestFocus();
        }
    }


    @OnClick(R.id.sendBtn)
    public void sendSmsClicked(){
        SmsManager smsManager = SmsManager.getDefault();
        String phoneNumber = phoneNumberET.getText().toString();
        String key = keyET.getText().toString();
        String message = secretMessageET.getText().toString();
        if(!ContactsValidation.validatePhoneNumber(phoneNumber)){
            Toast.makeText(this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
        }
        else if(!ContactsValidation.validateKey(key)){
            Toast.makeText(this, R.string.invalid_key, Toast.LENGTH_LONG).show();
        }
        else if(message==null || message.equals("")){
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                String secretMessage = CryptoUtil.encryptClearText(key.toCharArray(), message);
                smsManager.sendTextMessage(phoneNumber, null, secretMessage, null, null);
                saveInDatabase(phoneNumber, secretMessage, key);
                clearFields();
                nextActivity(phoneNumber);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void nextActivity(String phoneNumber){
        Intent intent = new Intent(this, ConversationActivity.class);
        Contact contact = Contact.getContactByPhone(phoneNumber);
        if(contact!=null) {
            intent.putExtra("contact", contact);
            startActivity(intent);
            finish();
        }
    }

    private void clearFields(){
        secretMessageET.setText("");
    }
    private void saveInDatabase(String phoneNumber, String secretMessage, String key){
        Contact contact = Contact.getContactByPhone(phoneNumber);
        if(contact==null){
            contact = new Contact(getString(R.string.unknown), phoneNumber);
            contact.save();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().putString(phoneNumber, key).commit();
        }
        Message msg = new Message(secretMessage, contact, true);
        msg.save();
    }

    @OnClick(R.id.add_from_contact)
    public void add_from_contact(){
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("mode", "pick");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            Contact selectedContact = data.getParcelableExtra("contact");
            phoneNumberET.setText(selectedContact.getPhone());
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String key = prefs.getString(selectedContact.getPhone(), "secret");
            keyET.setText(key);
        }
    }
}
