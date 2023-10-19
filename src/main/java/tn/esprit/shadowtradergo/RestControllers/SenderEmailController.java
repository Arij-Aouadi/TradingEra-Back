package tn.esprit.shadowtradergo.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.shadowtradergo.DAO.Entities.Email;
import tn.esprit.shadowtradergo.Services.Classes.EmailSenderService;
@RestController
public class SenderEmailController {


        @Autowired
        private EmailSenderService service;

        // @PostMapping  ("/sendemail") //oubien get ssss

        @PostMapping("/sendMail")
        public String
        sendMail(@RequestBody Email details)
        {
            String status
                    = service.sendSimpleEmail(details);

            return status;
        }

    }

