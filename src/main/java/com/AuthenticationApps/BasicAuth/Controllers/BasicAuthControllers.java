package com.AuthenticationApps.BasicAuth.Controllers;

import com.AuthenticationApps.BasicAuth.Services.Impl.BasicAuthSvcImp;
import com.AuthenticationApps.BasicAuth.entity.BasicAuthUser;
import com.AuthenticationApps.BasicAuth.utils.BasicAuthResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BasicAuthControllers {
    @Autowired
    BasicAuthSvcImp service;

    @PostMapping("/basicUserRegister")
    public ResponseEntity<BasicAuthResponseUtils> register(@RequestBody BasicAuthUser user){
        return new ResponseEntity<>(service.registerUser(user), HttpStatus.CREATED);
    }

    @PatchMapping("/basicUserLogin")
    public ResponseEntity<BasicAuthResponseUtils> login(@RequestBody BasicAuthUser user){
        return new ResponseEntity<>(service.loginUser(user),HttpStatus.OK);
    }

    @PatchMapping("/basicUserLogout/{uname}")
    public BasicAuthResponseUtils logout(@PathVariable("uname") String userName){
        return service.logoutUser(userName);
    }

    @GetMapping("/get")
    public List<BasicAuthUser> getAllUser(){
        return service.getAllUsers();
    }

    @GetMapping("/get/{uname}")
    public BasicAuthResponseUtils getUser(@PathVariable("uname") String uname){
        return service.getUserByUname(uname);
    }
}

