/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.cloudnerds.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.base.JRBaseElementDataset;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import tn.cloudnerds.entities.Cart;
import tn.cloudnerds.entities.CartLine;

import tn.cloudnerds.entities.Product;
import tn.cloudnerds.services.ProductService;

/**
 *
 * @author solta
 */

public class JasperReportAPI {

	public static void jasperReport(int parentId) {
		/** pdf name **/
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss-");
		String strDate = dateFormat.format(date);
		String pdfName = strDate + "user-"+String.valueOf(parentId);
		/** end pdf name **/
		ProductService productService = new ProductService();

		List<CartLine> productList = productService.CartLineForJasperReport(parentId);

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productList);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("dataSource", dataSource);

		String path = "C:\\Jee environment\\eclipse-workspace\\cloudkids_javaee\\kindergarten-ejb\\src\\main\\java\\tn\\cloudnerds\\utilities\\Pdf_template.jrxml";
		try {

//        	 
//        	 JasperDesign jasperDesign = JRXmlLoader.load(new File("")
//        	            .getAbsolutePath()
//        	            + "/src/main/java/tn/cloudnerds/utilities/Cherry.jrxml");
//        	 
//        	   JasperReport jasperReport = JasperCompileManager
//                       .compileReport(jasperDesign);

			JasperReport jr = JasperCompileManager.compileReport(path);
			JasperPrint jp = JasperFillManager.fillReport(jr, parameters, new JREmptyDataSource());
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);

			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					new FileOutputStream("C:\\Jee environment\\eclipse-workspace\\pdfGenerated\\"+pdfName+ ".pdf")); // your output goes
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaazzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");																						// here
			//JasperReportAPI.sendPdfInMail(pdfName);
			exporter.exportReport();
			JasperReportAPI.sendPdfInMail(pdfName);
			

//            JasperViewer.viewReport(jp, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public static void sendPdfInMail(String pdfName) {
		final String username = "ahmedamine.soltani12121@gmail.com";
		final String password = "--------------";
		String fromEmail = "ahmedamine.soltani12121@gmail.com";
		String toEmail = "ahmedamine.soltani12121@gmail.com";

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
		// Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject("your bill");

			Multipart emailContent = new MimeMultipart();

			// Text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Your products you just bought");

			try {

				MimeBodyPart qrCode = new MimeBodyPart();
				qrCode.attachFile("C:\\Jee environment\\eclipse-workspace\\pdfGenerated\\"+pdfName+".pdf");

				// Attach body parts
				emailContent.addBodyPart(textBodyPart);
				emailContent.addBodyPart(qrCode);
			} catch (Exception e) {
				System.err.println(e);
			}

			// Attach multipart to message
			msg.setContent(emailContent);

			Transport.send(msg);
			System.out.println("Sent message");
		} catch (MessagingException e) {
			e.printStackTrace();
			// } catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

}
