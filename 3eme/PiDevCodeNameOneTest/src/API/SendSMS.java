/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

/**
 *
 * @author solta
 */
public class SendSMS {

    public static final String ACCOUNT_SID = "AC92321fb722a833528acbcb8828493ab4";
    public static final String AUTH_TOKEN = "9f86775ea7d77c13e0f51bec8bcecad1";

    public static void sendSMSreservation(String nomEvenement) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new com.twilio.type.PhoneNumber("+21697338094"),//to
                new com.twilio.type.PhoneNumber("+12053040135"),//from 
                "Bonjour monsieur vous avez reservé l'événement " + nomEvenement).create();
    }

}
