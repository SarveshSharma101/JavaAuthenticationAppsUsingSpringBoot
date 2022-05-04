package com.AuthenticationApps.BasicAuth.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicAuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", unique = true)
    @NotNull
    private String uName;

    @Column(name = "password")
    private String pwd;

    @Column(name = "loginStatus")
    private boolean loginStatus = false;

    @Column(name="encryptionType")
    private  String encryptionType;

    @Column(name="digestKeyValue")
    private String digestKeyValue;
}
