package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mariusz.securesms.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import model.Contact;
import model.Message;
import utils.CryptoUtil;

/**
 * Created by mariusz on 24.01.17.
 */
public class ConversationAdapter extends ArrayAdapter {
    List<Message> messages;
    Context mContext;
    String password;
    public ConversationAdapter(Context context, int resource, List<Message> messages, String password) {
        super(context, resource);
        this.mContext = context;
        if(messages==null){
            messages = new ArrayList<>();
        }
        this.messages = messages;
        this.password = password;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Message msg = messages.get(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(msg.isYou()){
            row = inflater.inflate(R.layout.row_conversation_layout_me, parent, false);
            YouHolder holder = new YouHolder(row);
            holder.contact.setText(R.string.me);
            try {
                holder.message.setText(CryptoUtil.decryptData(password.toCharArray(), msg.getCipher()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            row = inflater.inflate(R.layout.row_conversation_layout, parent, false);
            ContactHolder holder = new ContactHolder(row);
            holder.contact.setText(msg.getContact().getName());
            try {
                holder.message.setText(CryptoUtil.decryptData(password.toCharArray(), msg.getCipher()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return row;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    static class ContactHolder{
        @Bind(R.id.message)
        TextView message;
        @Bind(R.id.contactId)
        TextView contact;

        public ContactHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class YouHolder{
        @Bind(R.id.message)
        TextView message;
        @Bind(R.id.contactId)
        TextView contact;

        public YouHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
