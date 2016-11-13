package ru.sbt.bit.ood.solid.homework;


import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SalaryHtmlReportSender {
    private final String recipients;
    private final StringBuilder htmlReport;

    public SalaryHtmlReportSender(String recipients, StringBuilder htmlReport) {
        this.recipients = recipients;
        this.htmlReport = htmlReport;
    }

    void sendSalaryReport(){
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("mail.google.com");
            mailSender.send(constructMiMeMessage(mailSender));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private MimeMessage constructMiMeMessage(JavaMailSenderImpl mailSender) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        setMessageHelper(message);
        return message;
    }

    private void setMessageHelper(MimeMessage message) throws MessagingException{
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        messageHelper.setTo(recipients);
        messageHelper.setText(htmlReport.toString(), true);
        messageHelper.setSubject("Monthly department salary report");
    }
}
