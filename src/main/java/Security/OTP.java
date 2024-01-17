package Security;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class OTP {
    private static final int OTP_LENGTH = 6;
    private static final int EXPIRY_TIME_SECONDS = 30; // 5 minutes
    private static final int MAX_ATTEMPTS = 3;

    private static Map<String, OtpSession> userSessions = new HashMap<>();

    public static String generateOtp(String userId) {
        String otp = String.format("%0" + OTP_LENGTH + "d", new Random().nextInt((int) Math.pow(10, OTP_LENGTH)));
        OtpSession otpSession = new OtpSession(otp);
        userSessions.put(userId, otpSession);
        return otp;
    }

    public static boolean verifyOtp(String userId, String enteredOtp) {
        OtpSession otpSession = userSessions.get(userId);

        if (otpSession != null && otpSession.isValid() && otpSession.getAttempts() < MAX_ATTEMPTS) {
            boolean isOtpValid = otpSession.getOtp().equals(enteredOtp);
            if (isOtpValid) {
                otpSession.setValid(false); // Mark OTP as used
            } else {
                otpSession.incrementAttempts();
            }
            return isOtpValid;
        }

        return false;
    }

    private static class OtpSession {
        @Getter
        private String otp;
        private long creationTime;
        private boolean isValid;
        @Getter
        private int attempts;

        public OtpSession(String otp) {
            this.otp = otp;
            this.creationTime = System.currentTimeMillis();
            this.isValid = true;
            this.attempts = 0;
        }

        public boolean isValid() {
            return isValid && (System.currentTimeMillis() - creationTime) < (EXPIRY_TIME_SECONDS * 1000);
        }

        public void incrementAttempts() {
            attempts++;
        }

        public void setValid(boolean isValid) {
            this.isValid = isValid;
        }
    }
}