package ma.gstcmd.delivrer.shareds;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Toll {
    private final Random RANDOM = new SecureRandom();
    private final String ALPHANUMERIC = "0123456789AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn";
    public String generateDeliverId(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            returnValue.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        return new String(returnValue);
    }
}
