package com.AuthenticationApps.BasicAuth.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;

@Component
public class Encoder {

    public String base64Encoder(@NotNull String stringToEncode){
        String encodedString = Base64.getEncoder().encodeToString(stringToEncode.getBytes());
        System.out.println("************Encoded String: "+encodedString);
        return encodedString;
    }

    public String base64Decoder(String encodedString){
        String decodedString = new String(Base64.getDecoder().decode(encodedString));
        System.out.println("************Decoded String: "+decodedString.toString());
        return decodedString;
    }

    public String encryptingUsingSecretKey(String stringToEncrypt, String secretKey){
        String encryptedString="";
        try{
            Key aesKey = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            encryptedString = Arrays.toString(cipher.doFinal(stringToEncrypt.getBytes()));
            return encryptedString;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("*********"+e.getCause()+("*************"));
        }
        return encryptedString;
    }

    public String dencryptingUsingSecretKey(String encryptString, String secretKey){
        String dencryptedString="";
        try{
            Key aesKey = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            dencryptedString = Arrays.toString(cipher.doFinal(encryptString.getBytes()));
            return dencryptedString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return dencryptedString;
    }
}
