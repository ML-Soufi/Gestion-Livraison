package ma.gstcmd.demo.securities;

import ma.gstcmd.demo.dtos.UserDto;
import ma.gstcmd.demo.exceptions.RestAccessDeniedHandler;
import ma.gstcmd.demo.exceptions.RestAuthenticationEntryPoint;
import ma.gstcmd.demo.filter.JwtAuthenticationFilter;
import ma.gstcmd.demo.filter.JwtAuthorizationFilter;
import ma.gstcmd.demo.services.IUserService;
import ma.gstcmd.demo.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private IUserService userService;
    private RestAccessDeniedHandler accessDeniedHandler;
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    private Tool tool;

    @Autowired
    public SecurityConfiguration(IUserService userService, RestAccessDeniedHandler accessDeniedHandler, RestAuthenticationEntryPoint authenticationEntryPoint, Tool tool) {
        this.userService = userService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.tool = tool;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow signup EndPoint without Authentication
        web.ignoring().antMatchers(HttpMethod.POST,"/users/v1/signup");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // to allow POST methods
        http.csrf().disable();
        // Allow refreshToken EndPoint without Authentication
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/users/v1/refreshToken").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.POST,"/users/v1/login").authenticated();
        http.authorizeRequests().anyRequest().authenticated();
        // to indicated Stateless authentication
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
        // add filters
        http.addFilter(getJWTAuthenticationFilter());
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), userService, authenticationEntryPoint));
        http.addFilterBefore(new JwtAuthorizationFilter(accessDeniedHandler, tool), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
                 UserDto dto = userService.getUser(userName);
                 Collection<GrantedAuthority> authorities = new ArrayList<>();
                 authorities.add(new SimpleGrantedAuthority(dto.getUserRole()));
                 return new User(dto.getUserId(), dto.getUserPassword(), authorities);
            }
        });
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    public JwtAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager(), userService, authenticationEntryPoint);
        filter.setFilterProcessesUrl("/users/v1/login");
        return filter;
    }

}
