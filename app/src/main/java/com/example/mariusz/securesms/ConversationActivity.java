package com.example.mariusz.securesms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import butterknife.OnClick;
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
    Contact contact;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_layout);
        ButterKnife.bind(this);
        contact = getIntent().getParcelableExtra("contact");

        convrsationHeader.setText(getString(R.string.your_conversation_with) + " " + contact.getName());
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String password = prefs.getString(contact.getPhone(), "secret");

        List<Message> messages = contact.getMessagesByPhone();
        convAdapter = new ConversationAdapter(this,R.layout.row_conversation_layout, messages, password);
        conversationListView.setAdapter(convAdapter);
    }

    @OnClick(R.id.newMsg)
    public void newMessageBtnClicked(){
        Intent intent = new Intent(this, NewMessageActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
        finish();
    }
}
