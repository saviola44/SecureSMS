package com.example.mariusz.securesms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import adapter.ContactAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import model.Contact;

/**
 * Created by mariusz on 23.01.17.
 */
public class ContactsActivity extends AppCompatActivity {
    public static final int MODE_PICK = 1;
    public static final int MODE_SHOW_CONVERSATION = 2;
    @Bind(R.id.contactListView)
    ListView contactListView;
    ContactAdapter contactAdapter;
    List<Contact> contacts;

    int mode = MODE_SHOW_CONVERSATION;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        ButterKnife.bind(this);
        contacts = Contact.listAll(Contact.class);
        contactAdapter = new ContactAdapter(this, R.layout.row_contact_layout, contacts);
        contactListView.setAdapter(contactAdapter);

        String modeStr = getIntent().getStringExtra("mode");
        if(modeStr!=null && modeStr.equals("pick")){
            mode = MODE_PICK;
        }


        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts.get(position);
                if(mode==MODE_SHOW_CONVERSATION) {
                    Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                    intent.putExtra("contact", contact);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("contact", contact);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

}
