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

/**
 * Created by mariusz on 23.01.17.
 */
public class ContactAdapter extends ArrayAdapter {
    List<Contact> contacts;
    Context mContext;
    public ContactAdapter(Context context, int resource, List<Contact> contacts) {
        super(context, resource);
        if(contacts==null){
            contacts = new ArrayList<>();
        }
        this.contacts = contacts;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContactHolder holder;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_contact_layout, parent, false);
            holder = new ContactHolder(row);
            row.setTag(holder);
        }
        else{
            holder = (ContactHolder) row.getTag();
        }
        Contact contact = contacts.get(position);
        holder.contactName.setText(contact.getName());
        holder.phoneNumber.setText(contact.getPhoneNumber());
        return row;
    }

    public void addContact(Contact newContact){
        contacts.add(newContact);
        this.notifyDataSetChanged();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    static class ContactHolder{
        @Bind(R.id.contactName)
        TextView contactName;
        @Bind(R.id.phoneNumber)
        TextView phoneNumber;

        public ContactHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
