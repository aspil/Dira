package di.uoa.gr.dira.util;

import java.security.SecureRandom;

public class PinGenerator {
    private static final SecureRandom rnd = new SecureRandom();
    public static long generateRandomPin() {
        return rnd.nextInt(100_000);
    }
}
