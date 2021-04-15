package com.oci;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class OCIEmailSend {
	String FROM;
	String FROMNAME;
	String SMTP_USERNAME;
	String SMTP_PASSWORD;
	String HOST;
	int PORT;
	
	public OCIEmailSend() {
		// Replace FROM with your "From" address.
	    // This address must be added to Approved Senders in the console.
		FROM = "andy@andypandi.ml";
		FROMNAME = "AndyPandi";
	 
	    // Replace smtp_username with your Oracle Cloud Infrastructure SMTP username generated in console.
	    SMTP_USERNAME = "ocid1.user.oc1..aaaaaaaasvt2woaogg2kuzw23b62v55bnejacdfisskg6qysykvs7xqqyfwq@ocid1.tenancy.oc1..aaaaaaaasoeyqjtyaapfdp3vltaaydqqzmclwdkx2qyihdoogu3nio4qcobq.kl.com";
	 
	    // Replace smtp_password with your Oracle Cloud Infrastructure SMTP password generated in console.
	    SMTP_PASSWORD = "ki7aVTzr9rK]##y2:WS0";
	 
	    // Oracle Cloud Infrastructure Email Delivery hostname.
	    HOST = "smtp.email.ap-seoul-1.oci.oraclecloud.com";
	 
	    // The port you will connect to on the SMTP endpoint. Port 25 or 587 is allowed.
		PORT = 587;
	}
	
	public String send(EmailDTO emailDTO) {
		String mailto = emailDTO.getMailto();
		String title = emailDTO.getTitle();
		String body = emailDTO.getBody();
		Transport transport = null;
		
        // Create a Properties object to contain connection configuration information. 
       Properties props = System.getProperties();
       props.put("mail.transport.protocol", "smtp");
       props.put("mail.smtp.port", PORT);
 
       //props.put("mail.smtp.ssl.enable", "true"); //the default value is false if not set
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.auth.login.disable", "true");  //the default authorization order is "LOGIN PLAIN DIGEST-MD5 NTLM". 'LOGIN' must be disabled since Email Delivery authorizes as 'PLAIN'
       props.put("mail.smtp.starttls.enable", "true");   //TLSv1.2 is required
       props.put("mail.smtp.starttls.required", "true");  //Oracle Cloud Infrastructure required
 
        // Create a Session object to represent a mail session with the specified properties.
       Session session = Session.getDefaultInstance(props);
       
       try {
	       // Create a message with the specified information.
	       MimeMessage msg = new MimeMessage(session);
	       msg.setFrom(new InternetAddress(FROM,FROMNAME));
	       msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailto));
	       msg.setSubject(title);
	       msg.setContent(body,"text/html; charset=utf8");
	
	       // Create a transport.
	       transport = session.getTransport();
	       
	       // Send the message.
           System.out.println("Sending Email now...standby..."); 

           // Connect to OCI Email Delivery using the SMTP credentials specified.
           transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);    

           // Send email.
           transport.sendMessage(msg, msg.getAllRecipients());
           System.out.println("Email sent!"); 
       } 
       catch (Exception ex) { 
           System.out.println("The email was not sent.");
           System.out.println("Error message: " + ex.getMessage());
           return "F";
       }
       finally
       {
    	   try {
    		   // Close & terminate the connection.
    		   transport.close();
    	   } catch (MessagingException e) {
    		   // TODO Auto-generated catch block
    		   e.printStackTrace();
    	   }
       }
       
       return "S";
	}
}