package ma.gstcmd.demo.services;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.gstcmd.demo.dtos.UserDto1;
import ma.gstcmd.demo.dtos.UserDto2;
import ma.gstcmd.demo.entities.UserEntity;
import ma.gstcmd.demo.exceptions.RestAccessDeniedHandler;
import ma.gstcmd.demo.exceptions.UserException;
import ma.gstcmd.demo.mapper.Mapper;
import ma.gstcmd.demo.repositories.UserRepository;
import ma.gstcmd.demo.requests.UserRequest;
import ma.gstcmd.demo.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Service
public class UserServiceImp1 implements IUserService1 {

    private UserRepository userRepository;
    private Mapper mapper;
    private Tool tool;
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public UserServiceImp1(UserRepository userRepository, Mapper mapper, Tool tool, RestAccessDeniedHandler accessDeniedHandler) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.tool = tool;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public UserDto2 getUsers(int page) {
        page = page > 0 ? page-1 : page;
        Page<UserEntity> entities = userRepository.findAll(PageRequest.of(page, 5));
        UserDto2 dto = new UserDto2();
        dto.setDtos(mapper.entityToDto1(entities.getContent()));
        dto.setPageSizes(entities.getTotalPages());
        return dto;
    }

    @Override
    public UserDto2 getUsers(String userLastName) {
        Page<UserEntity> entities = userRepository.findByUserLastNameIsContaining(userLastName.toLowerCase(), PageRequest.of(0,5));
        UserDto2 dto = new UserDto2();
        dto.setDtos(mapper.entityToDto1(entities.getContent()));
        dto.setPageSizes(entities.getTotalPages());
        return dto;
    }

    @Override
    public UserDto1 getUser(String userId) {
        if(userRepository.existsByUserId(userId)){
            return mapper.entityToDto1(userRepository.findByUserId(userId));
        }else{
            throw new UserException("cet utilisateur n'éxiste pas.");
        }
    }

    @Override
    public Boolean existUser(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public Boolean isCredentialsValid(String userId, String password) {
        UserEntity entity = userRepository.findByUserId(userId);
        return tool.passwordEncoder().matches(password, entity.getUserPassword());
    }

    @Override
    public Boolean isUserActive(String userId) {
        return userRepository.findByUserId(userId).getUserState();
    }

    @Override
    public UserDto1 activateUser(String userId) {
        if(userRepository.existsByUserId(userId)){
            UserEntity entity = userRepository.findByUserId(userId);
            entity.setUserState(!entity.getUserState());
            return mapper.entityToDto1(userRepository.save(entity));
        }else{
            throw new UserException("cet utilisateur n'éxiste pas.");
        }
    }

    @Override
    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        try {
            List<String> roles = new ArrayList<>();
            String jwt = tool.extractJWTToken(httpServletRequest, httpServletResponse);
            Algorithm algorithm = Algorithm.HMAC256("P@5sw0rd");
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            String username = decodedJWT.getSubject();
            UserEntity user = userRepository.findByUserId(username);
            roles.add(user.getUserRole());
            String jwtAccessToken = JWT.create()
                    .withSubject(user.getUserId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .withIssuer(httpServletRequest.getRequestURL().toString())
                    .withClaim("fullName", user.getUserLastName().toUpperCase()+" "+user.getUserFirstName())
                    .withClaim("roles", roles)
                    .sign(algorithm);

            String jwtRefreshToken = JWT.create()
                    .withSubject(user.getUserId())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .withIssuer(httpServletRequest.getRequestURL().toString())
                    .sign(algorithm);

            Map<String, String> idToken = new HashMap<>();
            idToken.put("access-token", jwtAccessToken);
            idToken.put("refresh-token", jwtRefreshToken);
            httpServletResponse.setContentType("application/json");
            new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), idToken);
        }
        catch (Exception e){
            accessDeniedHandler.handle(httpServletRequest, httpServletResponse,new AccessDeniedException(e.getMessage()));
        }
    }

    @Override
    public UserDto1 addUser(UserRequest request) {
        String newUserId = tool.generateUserId(8);
        System.out.println(request.toString());
        if(!userRepository.existsByUserId(newUserId)){
            UserEntity userEntity = mapper.requestToEntity(request);
            userEntity.setUserId(newUserId);
            userEntity.setUserPassword(tool.passwordEncoder().encode(userEntity.getUserPassword()));
            return mapper.entityToDto1(userRepository.save(userEntity));
        }else{
            throw new UserException("cet utilisateur déjà éxiste pas.");
        }
    }

    @Override
    public void deleteUser(String userId) {
        if(userRepository.existsByUserId(userId)){
            userRepository.delete(userRepository.findByUserId(userId));
        }else{
            throw new UserException("cet utilisateur n'éxiste pas.");
        }
    }
}
