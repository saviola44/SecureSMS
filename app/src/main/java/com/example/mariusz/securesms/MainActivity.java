package com.example.mariusz.securesms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.addContact)
    Button addContactBtn;
    @Bind(R.id.newMessage)
    Button newMessageBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.newMessage)
    public void onNewMsgBtnClicked(){
        Intent intent = new Intent(this, NewMessageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.addContact)
    public void addContactClicked(){
        Intent intent = new Intent(this, NewContact.class);
        startActivity(intent);
    }

    @OnClick(R.id.contacts)
    public void onContactsClicked(){
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }
}
