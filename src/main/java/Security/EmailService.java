package Security;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;
public class EmailService {

        private static final String SMTP_HOST = "smtp.gmail.com"; // Replace with your SMTP host
        private static final String SMTP_PORT = "587"; // Replace with your SMTP port
        private static final String EMAIL_USERNAME = "janathhma@gmail.com"; // Replace with your email username
        private static final String EMAIL_PASSWORD = "owjj sgjj vdfu ppkd"; // Replace with your email password

        public static void sendOtpByEmail(String toEmail, String otp) {
            // Set the properties for the email server
            Properties properties = new Properties();
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", SMTP_PORT);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");

            // Create a session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
                }
            });

            try {
                // Create a MimeMessage object
                Message message = new MimeMessage(session);

                // Set the sender and recipient addresses
                message.setFrom(new InternetAddress(EMAIL_USERNAME));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

                // Set the subject and content
                message.setSubject("Verification OTP");
                message.setText("Your OTP for verification is: " + otp);

                // Send the message
                Transport.send(message);

                System.out.println("Email sent successfully.");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    public static void sendEmailWithAttachment(String toEmail, String subject, String body, String pdfFilePath) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Set the subject and content
            message.setSubject(subject);
            message.setText(body);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create body part for the email content
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(body);

            // Add the text body part to the multipart
            multipart.addBodyPart(textBodyPart);

            // Create body part for the PDF attachment
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(new File(pdfFilePath));
            pdfBodyPart.setDataHandler(new DataHandler(source));
            pdfBodyPart.setFileName(new File(pdfFilePath).getName());

            // Add the PDF attachment body part to the multipart
            multipart.addBodyPart(pdfBodyPart);

            // Set the multipart as the content of the message
            message.setContent(multipart);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    }

