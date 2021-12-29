package ma.gstcmd.product.shareds;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class Toll {
    private final Random RANDOM = new SecureRandom();
    private final String NUMBERS = "0123456789";
    private final String ALPHANUMERIC = "0123456789AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn";
    public String generateProductId(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            returnValue.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        return new String(returnValue);
    }

    public String generateImageSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            returnValue.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
        return new String(returnValue);
    }
}
