package ma.gstcmd.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ma.gstcmd.demo.exceptions.RestAccessDeniedHandler;
import ma.gstcmd.demo.tools.Tool;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private RestAccessDeniedHandler accessDeniedHandler;
    private Tool tool;

    public JwtAuthorizationFilter(RestAccessDeniedHandler accessDeniedHandler, Tool tool) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.tool = tool;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().contains("/users/v1/login")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        if(httpServletRequest.getServletPath().contains("/users/v1/refreshToken")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        else {
            try {
                    String jwt = tool.extractJWTToken(httpServletRequest, httpServletResponse);
                    Algorithm algorithm = Algorithm.HMAC256("P@5sw0rd");
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    if(roles == null)
                        throw new Exception("Attention ! vous utilisez le refreshToken.");
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
            catch (Exception e){
                accessDeniedHandler.handle(httpServletRequest, httpServletResponse,new AccessDeniedException(e.getMessage()));
            }
        }
    }

}
