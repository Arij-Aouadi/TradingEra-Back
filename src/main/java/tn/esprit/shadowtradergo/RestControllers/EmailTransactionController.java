package tn.esprit.shadowtradergo.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
@RestController
@CrossOrigin(origins = "*",maxAge=3600)

public class EmailTransactionController {
    @Autowired
    public JavaMailSender emailSender;



    @ResponseBody
    @GetMapping("/sendAttachmentEmailTransaction/{ReciverEmail}")
    public String sendAttachmentEmail(@PathVariable("ReciverEmail") String ReciverEmail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        int max = 999999 ;
        int min = 9999 ;
        SecureRandom secureRandom = new SecureRandom();
        int randomWithSecureRandomWithinARange = secureRandom.nextInt(max - min) + min;

        String htmlMsg = "<h3>Validate this Transaction by Using this number  </h3>"
                +"<img src='http://www.apache.org/images/asf_logo_wide.gif'>"
                + randomWithSecureRandomWithinARange;


        message.setContent(htmlMsg, "text/html");

        helper.setTo(ReciverEmail);

        helper.setSubject("GrowFunds Transaction endorsment ");


        this.emailSender.send(message);

        return "Email Sent!";
    }

}

