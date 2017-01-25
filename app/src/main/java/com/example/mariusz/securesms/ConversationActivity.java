package com.example.mariusz.securesms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import adapter.ConversationAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import model.Contact;
import model.Message;

/**
 * Created by mariusz on 24.01.17.
 */
public class ConversationActivity extends AppCompatActivity {
    @Bind(R.id.convrsationHeader)
    TextView convrsationHeader;
    @Bind(R.id.conversationListView)
    ListView conversationListView;

    ConversationAdapter convAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_layout);
        ButterKnife.bind(this);
        Contact contact = getIntent().getParcelableExtra("contact");

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String password = prefs.getString(contact.getPhone(), "secret");

        List<Message> messages = contact.getMessagesByPhone();
        convAdapter = new ConversationAdapter(this,R.layout.row_conversation_layout, messages, password);
        conversationListView.setAdapter(convAdapter);
    }
}
