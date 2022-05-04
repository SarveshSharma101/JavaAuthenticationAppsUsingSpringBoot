package com.AuthenticationApps.BasicAuth.Services;

import com.AuthenticationApps.BasicAuth.entity.BasicAuthUser;
import com.AuthenticationApps.BasicAuth.utils.BasicAuthResponseUtils;

import java.util.List;

public interface AuthenticationService {

    BasicAuthResponseUtils registerUser(BasicAuthUser user);

    BasicAuthResponseUtils loginUser(BasicAuthUser user);

    List<BasicAuthUser> getAllUsers();

    BasicAuthResponseUtils getUserByUname(String uname);

    BasicAuthResponseUtils logoutUser(String userName);
}
