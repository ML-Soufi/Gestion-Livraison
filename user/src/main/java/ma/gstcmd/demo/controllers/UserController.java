package ma.gstcmd.demo.controllers;

import ma.gstcmd.demo.dtos.UserDto1;
import ma.gstcmd.demo.dtos.UserDto2;
import ma.gstcmd.demo.requests.UserRequest;
import ma.gstcmd.demo.services.IUserService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users/v1")
public class UserController {
    private IUserService1 userService;

    @Autowired
    public UserController(IUserService1 userService) {
        this.userService = userService;
    }

    @GetMapping("/page/{page}")
    public UserDto2 getUsers(@PathVariable int page){
        return userService.getUsers(page);
    }

    @GetMapping("/contains/{userLastName}")
    public UserDto2 getUsers(@PathVariable String userLastName){
        return userService.getUsers(userLastName);
    }

    @GetMapping("/{userId}")
    public UserDto1 getUser(@PathVariable String userId){
        return userService.getUser(userId);
    }

    @GetMapping("/active/{userId}")
    public UserDto1 activeUser(@PathVariable String userId){
        return userService.activateUser(userId);
    }

    @GetMapping("/refreshToken")
    private void getNewAccessToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        userService.refreshToken(httpServletRequest, httpServletResponse);
    }

    @PostMapping("/signup")
    public UserDto1 addUser(@Valid @RequestBody UserRequest request){
        return userService.addUser(request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }
}
