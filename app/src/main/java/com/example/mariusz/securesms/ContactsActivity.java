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
    @Bind(R.id.contactListView)
    ListView contactListView;
    ContactAdapter contactAdapter;
    List<Contact> contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_layout);
        ButterKnife.bind(this);
        contacts = Contact.listAll(Contact.class);
        contactAdapter = new ContactAdapter(this, R.layout.row_contact_layout, contacts);
        contactListView.setAdapter(contactAdapter);

        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts.get(position);
                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                intent.putExtra("contact", contact);
                startActivity(intent);
            }
        });
    }

}
