package com.AuthenticationApps.BasicAuth.utils;

import com.AuthenticationApps.BasicAuth.entity.BasicAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BasicAuthReqBodyValidator {

    @Autowired
    Encoder encoder;

    public BasicAuthResponseUtils checkIfUnameEmpty(String uname){
        if (uname == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "'uName' field is missing in the req body");
        if ("".equalsIgnoreCase(uname))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Username cannot be empty");
        return null;
    }

    public BasicAuthResponseUtils checkPassword(String password){
        if (password == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "'pwd' field is missing in the req body");
        else if("".equalsIgnoreCase(password))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "password cannot be empty");
        else if (password.length()<8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password to short, should be more than 8 characters.");
        }
        return null;
    }

    public BasicAuthResponseUtils authenticateUser(BasicAuthUser actualUser, BasicAuthUser user) {
        String encryptedPwd = actualUser.getPwd();
        String decryptedPwd = "";
        if (actualUser.getEncryptionType().equals("base64"))
            decryptedPwd = encoder.base64Decoder(encryptedPwd);
        else
            decryptedPwd = encoder.encryptingUsingSecretKey(encryptedPwd, user.getDigestKeyValue());
        System.out.println("***************&&&&&&&&&&&&"+decryptedPwd);
        System.out.println("**************&&&&&&&&&&"+user.getPwd());
        if (!(user.getPwd().equals(decryptedPwd))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Password did not match. Login failed");
        }
        return null;
    }
}
