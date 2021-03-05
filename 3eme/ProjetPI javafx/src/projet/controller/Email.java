/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.controller;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author yassine
 */
public class Email {
    
    public void sendMail(String email,int id){
        

            String toEmail = email;

            String filename = "C:\\Users\\solta\\Desktop\\ProjetPI\\src\\projet\\files\\Facture"+id+".pdf";
                
                
                
                 try {
            // TODO code application logic here
            String port="587";
            Properties props = new Properties();
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.port", port );
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.user", "worldfriendship19@gmail.com");
            props.setProperty("mail.password","worldfriend2019");
            props.setProperty("mail.smtp.auth", "true");
             Authenticator auth;
                            auth = new Authenticator() {
                                @Override
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication("worldfriendship19@gmail.com", "worldfriend2019");
                                }
                            };
            Session mailSession = Session.getDefaultInstance(props, auth);
            Transport transport = mailSession.getTransport();
 
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject("Testing javamail plain");
            message.setContent("This is a test", "text/plain");
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toEmail));
 
            MimeBodyPart attachmentPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(filename) {
 
                @Override

                public String getContentType() {

              return "application/octet-stream";

                }

            };
            
            attachmentPart.setDataHandler(new DataHandler(fileDataSource));
 
            attachmentPart.setFileName(fileDataSource.getName());
            
            Multipart multipart = new MimeMultipart();
 
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message,
                    message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
                 
                 
                 
                 
                 
                 
                 
                 
              
    }
}
