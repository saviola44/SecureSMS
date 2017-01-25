package receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.mariusz.securesms.MainActivity;
import com.example.mariusz.securesms.R;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import model.Contact;
import model.Message;
import utils.CryptoUtil;

/**
 * Created by mariusz on 23.01.17.
 */
public class IncomingSms extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT < 19) {
            if(Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())){
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs;
                String sender;
                if (bundle != null){
                    //---retrieve the SMS message received---
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            sender = msgs[i].getOriginatingAddress();
                            String messageBody = msgs[i].getMessageBody();
                            String messageEncoded = CryptoUtil.decryptData("mistrz".toCharArray(), messageBody);

                            sendNotification(context, sender, messageEncoded, messageBody);
                        }
                    }
                    catch(Exception e){
                        Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }
        else{
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String messageSecretBody = smsMessage.getMessageBody();
                    String sender = smsMessage.getOriginatingAddress();
                    try {
                        String key = PreferenceManager.getDefaultSharedPreferences(context).getString(sender,
                                context.getString(R.string.secret));
                        String messageEncoded = CryptoUtil.decryptData(key.toCharArray(), messageSecretBody);
                        sendNotification(context, sender, messageEncoded, messageSecretBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private void sendNotification(Context context, String phone, String msg, String cipher) {
        Contact contact = getContact(phone);
        if(contact==null){
            return;
        }

        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =

                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.contact_icon)
                        .setContentTitle(context.getString(R.string.new_sms_from) + contact.getName())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        Random random = new Random(10000);
        mNotificationManager.notify(random.nextInt(), mBuilder.build());


        Message message = new Message(cipher, contact, false);
        message.save();
    }

    private Contact getContact(String phone){
        List<Contact> contacts = Contact.find(Contact.class, "phone = ?", phone);
        if(contacts.size()==1){
            List<Contact> contactslist = Contact.listAll(Contact.class);
            return contacts.get(0);
        }
        else {
            return null;
        }
    }
}
