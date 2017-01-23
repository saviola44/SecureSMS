package receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mariusz on 23.01.17.
 */
public class IncomingSms extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();

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
                            Log.d("jestem", "odebrałem sms " + sender + messageBody);
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
                    String messageBody = smsMessage.getMessageBody();
                    String sender = smsMessage.getOriginatingAddress();
                    Log.d("jestem", "odebrałem sms " + sender + messageBody);
                }
            }
        }
    }
}
