package edu.esprit.cryfty.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class smsapi {
    public static final String ACCOUNT_SID = "AC9e2a04a58eb7173bf6c77a21ba9f08d6";
    public static final String AUTH_TOKEN = "34f03ffe77268678bb2d42bce7fa72ff";

    public static void sendSMS(String num, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(num),new PhoneNumber("+16127784838"), msg).create();

        System.out.println(message.getSid());

    }
}
