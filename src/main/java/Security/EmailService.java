package Security;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class EmailService {

        private static final String SMTP_HOST = "smtp.gmail.com"; // Replace with your SMTP host
        private static final String SMTP_PORT = "587"; // Replace with your SMTP port
        private static final String EMAIL_USERNAME = "janathhma@gmail.com"; // Replace with your email username
        private static final String EMAIL_PASSWORD = "ustn jlfs slmu xbcg"; // Replace with your email password

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

    }

