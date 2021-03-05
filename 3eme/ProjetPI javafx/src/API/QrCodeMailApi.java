/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 * @author solta
 */
public class QrCodeMailApi {

    public static void envoyerQrCode(String nomUtilisater, String nomEvenement,Date dateDebutEvenement) {
        final String username = "soltani.ahmed1994@gmail.com";
        final String password = "kG7604@/12ab1OuUch";
        String fromEmail = "soltani.ahmed1994@gmail.com";
        String toEmail = "soltani.ahmed1994@gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");    

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Bienvenue à notre Evenement ' " + nomEvenement + " ' Monsieur ' " + nomUtilisater + " '");

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("My multipart text");

            try {
                String qrCodeData = "Bonjour Monsieur : ' " +  nomUtilisater + 
                        " ' , ceci votre ticket vous avez participé à l\'événement : ' " + nomUtilisater + 
                        " Date debut sera : '"+ dateDebutEvenement +
                        " \nTicket recu le :" + Timestamp.valueOf(LocalDateTime.now());
                
                
                String filePath = "src\\projet\\files\\qrGenerated.png";
                String charset = "UTF-8"; // or "ISO-8859-1"
                Map< EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap< EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                BitMatrix matrix = new MultiFormatWriter().encode(
                        new String(qrCodeData.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 200, 200, hintMap);
                MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath
                        .lastIndexOf('.') + 1), new File(filePath));
                System.out.println("QR Code image created successfully!");

            } catch (Exception e) {
                System.err.println(e);
            }

            try {
                //Attachment body part.
                MimeBodyPart qrCode = new MimeBodyPart();
                qrCode.attachFile("src\\projet\\files\\qrGenerated.png");

                //Attach body parts
                emailContent.addBodyPart(textBodyPart);
                emailContent.addBodyPart(qrCode);
            } catch (Exception e) {
                System.err.println(e);
            }

            //Attach multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
            //} catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

    }
    
       public static void envoyerMailAnnulation(String nomUtilisater, String nomEvenement) {
        final String username = "soltani.ahmed1994@gmail.com";
        final String password = "kG7604@/12ab1OuUch";
        String fromEmail = "soltani.ahmed1994@gmail.com";
        String toEmail = "soltani.ahmed1994@gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Bonjour Monsieur ' " + nomUtilisater + " ' votre reservation pour l\'événement ' " + nomEvenement + " ' est annulé " );

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("My multipart text");

        

            try {

                //Attach body parts
                emailContent.addBodyPart(textBodyPart);
            } catch (Exception e) {
                System.err.println(e);
            }

            //Attach multipart to message
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
            //} catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

    }

}
