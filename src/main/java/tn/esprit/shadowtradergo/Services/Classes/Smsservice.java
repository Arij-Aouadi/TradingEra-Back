package tn.esprit.shadowtradergo.Services.Classes;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



import tn.esprit.shadowtradergo.DAO.Entities.User;
import tn.esprit.shadowtradergo.DAO.Entities.*;
import tn.esprit.shadowtradergo.DAO.Repositories.UserRepository;
import tn.esprit.shadowtradergo.Services.Interfaces.ISmsservice;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;


@Service
@Slf4j

public class Smsservice implements ISmsservice {
    private UserRepository userRepository;
    // identifiants Twilio
   public static final String ACCOUNT_SID = "AC3e06bfe243eb3273f78bcdd2e31fc9d2";
    public static final String AUTH_TOKEN = "30bb26a6a1abbb0c1c5f64fa4b6e4baa";
    public static final String OUTGOING_SMS_NUMBER = "+16173263407";

    @PostConstruct
    private void setup() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public String sendSms(String smsNumber, String smsMessage) {
        Message message = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(OUTGOING_SMS_NUMBER),
                smsMessage).create();

        return message.getStatus().toString();

    }


  @Scheduled(fixedRate = 86400000)
    @Override
    public void sendSmsReminders() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getRole().equals(TypeRole.CLIENT)) {
                String smsMessage = "Dear " + user.getUsername() + "  has been confirmed. Thank you for your business!";

                sendSms("+21697928950", smsMessage);
            }
        }
    }
}






