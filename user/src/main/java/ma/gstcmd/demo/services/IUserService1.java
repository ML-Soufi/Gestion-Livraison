package ma.gstcmd.demo.services;

import ma.gstcmd.demo.dtos.UserDto1;
import ma.gstcmd.demo.dtos.UserDto2;
import ma.gstcmd.demo.requests.UserRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService1 {
    UserDto2 getUsers(int page);
    UserDto2 getUsers(String userLastName);
    UserDto1 getUser(String userId);
    Boolean existUser(String userId);
    Boolean isCredentialsValid(String userId, String password);
    Boolean isUserActive(String userId);
    UserDto1 activateUser(String userId);
    void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;
    UserDto1 addUser(UserRequest request);
    void deleteUser(String userId);
}
