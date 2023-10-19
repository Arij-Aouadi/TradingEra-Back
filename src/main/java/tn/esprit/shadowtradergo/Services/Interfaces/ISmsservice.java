package tn.esprit.shadowtradergo.Services.Interfaces;

public interface ISmsservice {
    public  String sendSms(String smsNumber,String smsMessage);
    public void sendSmsReminders();


}
