package com.AuthenticationApps.BasicAuth.Repository;

import com.AuthenticationApps.BasicAuth.entity.BasicAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BasicAuthRepo extends JpaRepository<BasicAuthUser, Long> {
    BasicAuthUser findByuName(String uname);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update basic_auth_user set login_status=:status where username=:uname")
    int updateLoginStatus(boolean status, String uname);
}
