package tn.esprit.shadowtradergo.Services.Interfaces;

import tn.esprit.shadowtradergo.DAO.Entities.Email;

public interface IEmailService {
    // Method
    // To send a simple email
    String sendSimpleEmail(Email details);
}
