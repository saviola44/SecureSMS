package com.example.mariusz.securesms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

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
}
