package com.intrafind.user.controller.v1;

import com.intrafind.user.model.dao.User;
import com.intrafind.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@Api(value = "User control", tags = {"User control"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "find all users", tags = {"find all users"})
    @GetMapping(path = "/findAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @ApiOperation(value = "get a user", tags = {"get a user"})
    @GetMapping(path = "/getUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable int id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @ApiOperation(value = "add a user", tags = {"add a user"})
    @PostMapping(path = "/addUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @ApiOperation(value = "delete a user", tags = {"delete a user"})
    @DeleteMapping(path = "/deleteUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "update a user", tags = {"update a user"})
    @PutMapping(path = "/updateUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int id) {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
    }
}
