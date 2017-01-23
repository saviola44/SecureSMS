package com.example.mariusz.securesms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.ContactsValidation;
import utils.CryptoUtil;

/**
 * Created by mariusz on 22.01.17.
 */
public class NewMessageActivity extends AppCompatActivity {
    @Bind(R.id.phoneNumber)
    EditText phoneNumberET;
    @Bind(R.id.keyET)
    TextView keyET;
    @Bind(R.id.secretMessageET)
    EditText secretMessageET;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_msg_layout);
        ButterKnife.bind(this);
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
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
