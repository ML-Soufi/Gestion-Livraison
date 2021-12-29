package ma.gstcmd.demo.services;

import ma.gstcmd.demo.dtos.UserDto;
import ma.gstcmd.demo.requests.UserRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IUserService {
    List<UserDto> getUsers();
    UserDto getUser(String userId);
    Boolean existUser(String userId);
    Boolean isCredentialsValid(String userId, String password);
    Boolean isUserActive(String userId);
    UserDto activateUser(String userId);
    void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception;
    UserDto addUser(UserRequest request);
    void deleteUser(String userId);
}
