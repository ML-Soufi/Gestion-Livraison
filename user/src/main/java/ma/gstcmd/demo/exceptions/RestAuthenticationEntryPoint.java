package ma.gstcmd.demo.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        UserExceptionTemplate template = new UserExceptionTemplate(HttpStatus.BAD_REQUEST.value(), e.getMessage(), new Date());
        OutputStream out = httpServletResponse.getOutputStream();
        httpServletResponse.setStatus(template.getErrorStatus());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, template);
        out.flush();
    }
}