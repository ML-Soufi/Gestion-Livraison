package ma.gstcmd.demo.filter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.gstcmd.demo.dtos.UserDto;
import ma.gstcmd.demo.exceptions.RestAuthenticationEntryPoint;
import ma.gstcmd.demo.services.IUserService;
import ma.gstcmd.demo.tools.MyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private IUserService userService;
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, IUserService userService, RestAuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MyBean myBean = null;
        String userId = "";
        String password = "";
        try {
            myBean = new ObjectMapper().readValue(request.getInputStream(), MyBean.class);
        }catch (IOException ex){
            throw new BadCredentialsException(((IOException) ex).getMessage());
        }
        try {
            userId = myBean.getUserId();
            password = myBean.getPassword();
            if(!userService.existUser(userId)){
                AuthenticationException ex = new BadCredentialsException("ce compte n'éxiste pas.");
                authenticationEntryPoint.commence(request, response, ex);
                return null;
            }else{
                if(!userService.isCredentialsValid(userId, password)){
                    AuthenticationException ex = new BadCredentialsException("username ou password incorrect.");
                    authenticationEntryPoint.commence(request, response, ex);
                    return null;
                }
                if(!userService.isUserActive(userId)){
                    AuthenticationException ex = new BadCredentialsException("ce compte est désactiver pour le moment.");
                    authenticationEntryPoint.commence(request, response, ex);
                    return null;
                }
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
            return authenticationManager.authenticate(authenticationToken);
        }catch (IOException | AuthenticationException | ServletException ex){
                throw new BadCredentialsException("");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User user = (User) authResult.getPrincipal();
        UserDto dto = userService.getUser(user.getUsername());
        Algorithm algorithm = Algorithm.HMAC256("P@5sw0rd");

        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("fullName", dto.getUserLastName().toUpperCase()+" "+dto.getUserFirstName())
                .withClaim("roles", user.getAuthorities().stream().map(g -> g.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);

        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        Map<String, String> idToken = new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefreshToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);
    }
}
