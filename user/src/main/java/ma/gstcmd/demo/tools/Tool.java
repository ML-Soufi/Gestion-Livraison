package ma.gstcmd.demo.tools;

import ma.gstcmd.demo.exceptions.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class Tool {
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public Tool(RestAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    private final Random RANDOM = new SecureRandom();
    private final String ALPHANUMERIC = "0123456789AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn";
    public String generateUserId(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            returnValue.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
        return new String(returnValue);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(4, new SecureRandom());
    }

    public String extractJWTToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader("Authorization");

        // check if Authorization header exist
        if (authorizationHeader == null)
            accessDeniedHandler.handle(request, response, new AccessDeniedException("Authorization header n'éxiste pas."));

        // check if Authorization header empty
        if (authorizationHeader.isEmpty())
            accessDeniedHandler.handle(request, response,new AccessDeniedException("Authorization header est vide."));

        // to trim Authorization content to : Bearer and jwt
        String authorizationHeaderContent = authorizationHeader.trim();
        String[] components = authorizationHeaderContent.split("\\s");

        // check if Authorization content includes 2 parts : Bearer and jwt
        if (components.length != 2)
            accessDeniedHandler.handle(request, response,new AccessDeniedException("Le contenu Authorization header est  mal écrit."));

        // check if Authorization content 1 part : include Bearer
        if (!components[0].equals("Bearer"))
            accessDeniedHandler.handle(request, response,new AccessDeniedException("Le contenu Authorization header ne contient pas Bearer."));

        // check if Authorization content 2 part : include jwt
        if(components[1].trim().chars().filter(ch -> ch == '.').count() != 2)
            accessDeniedHandler.handle(request, response,new AccessDeniedException("Le contenu du JWT est mal écrit."));

        // check if Authorization content 2 part : include a valid jwt
        String[] parts = components[1].split("\\.");
        if(parts[0].length() == 0 || parts[1].length() == 0 || parts[2].length() == 0)
            accessDeniedHandler.handle(request, response,new AccessDeniedException("Le contenu du JWT est mal écrit."));

        return components[1].trim();
    }
}
