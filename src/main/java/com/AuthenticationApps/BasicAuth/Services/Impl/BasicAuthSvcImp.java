package com.AuthenticationApps.BasicAuth.Services.Impl;

import com.AuthenticationApps.BasicAuth.Repository.BasicAuthRepo;
import com.AuthenticationApps.BasicAuth.Services.AuthenticationService;
import com.AuthenticationApps.BasicAuth.entity.BasicAuthUser;
import com.AuthenticationApps.BasicAuth.utils.BasicAuthReqBodyValidator;
import com.AuthenticationApps.BasicAuth.utils.BasicAuthResponseUtils;
import com.AuthenticationApps.BasicAuth.utils.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
public class BasicAuthSvcImp implements AuthenticationService {

    @Autowired
    Encoder encoder;

    @Autowired
    BasicAuthReqBodyValidator validator;

    @Autowired
    BasicAuthRepo repo;

    private BasicAuthResponseUtils response;

    @Override
    public BasicAuthResponseUtils registerUser(BasicAuthUser user) throws ResponseStatusException {
        String uname = user.getUName();
        String password = user.getPwd();
        response = validator.checkIfUnameEmpty(uname);
        if (response != null) return response;

        response = validator.checkPassword(password);
        if (response!=null) return response;
        String encryptedPassword = "";
        if (user.getEncryptionType().equals("base64"))
            encryptedPassword = encoder.base64Encoder(user.getPwd());
        else {
            encryptedPassword = encoder.encryptingUsingSecretKey(user.getPwd(), user.getDigestKeyValue());
            if (encryptedPassword.isEmpty())
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED,
                        "There was some error in encoding");
        }
        user.setPwd(encryptedPassword);
        BasicAuthUser registeredUser = repo.save(user);
        response = BasicAuthResponseUtils.builder()
                .message("Registration was successful")
                .user(registeredUser)
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return response;
    }

    @Override
    public BasicAuthResponseUtils loginUser(BasicAuthUser user) throws ResponseStatusException {
        String uname = user.getUName();
        String password = user.getPwd();
        response = validator.checkIfUnameEmpty(uname);
        if (response != null) return response;

        response = validator.checkPassword(password);
        if (response!=null) return response;

        BasicAuthUser actualUser = repo.findByuName(user.getUName());
        if (actualUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No user found with the provided username");
        }

        response = validator.authenticateUser(actualUser, user);
        if (response!=null) return response;

        int value = repo.updateLoginStatus(true, uname);
        BasicAuthUser loggedInUser = repo.findByuName(uname);
        response = BasicAuthResponseUtils.builder()
                .message("Login was successful")
                .user(loggedInUser)
                .statusCode(HttpStatus.OK.value())
                .build();

        return response;
    }

    @Override
    public List<BasicAuthUser> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public BasicAuthResponseUtils getUserByUname(String uname) {
        BasicAuthUser actualUser = repo.findByuName(uname);
        if (actualUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No user found with the provided username");
        }
        BasicAuthUser user = repo.findByuName(uname);
        response = BasicAuthResponseUtils.builder()
                .message("Login was successful")
                .user(user)
                .statusCode(HttpStatus.OK.value())
                .build();
        return response;
    }

    @Override
    public BasicAuthResponseUtils logoutUser(String userName) {
        response = validator.checkIfUnameEmpty(userName);
        if (response != null) return response;

        BasicAuthUser actualUser = repo.findByuName(userName);
        if (actualUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No user found with the provided username");
        }

        int value = repo.updateLoginStatus(false, userName);
        BasicAuthUser loggedInUser = repo.findByuName(userName);
        response = BasicAuthResponseUtils.builder()
                .message("Login was successful")
                .user(loggedInUser)
                .statusCode(HttpStatus.OK.value())
                .build();

        return response;
    }
}
