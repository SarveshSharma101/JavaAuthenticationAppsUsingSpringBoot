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
            System.out.println("********************LOLLOLOLOLOL"+encryptString);
            String[] array = encryptString.split(",");

            byte[] bytesArray = new byte[array.length];

            for (int i=0; i<array.length; i++){
                if (i == 0) {
                    bytesArray[i] = Byte.parseByte(array[i].substring(1).trim() );
                    continue;
                }
                else if (i==array.length-1) {
                    bytesArray[i] = Byte.parseByte(array[i].substring(1, 3).trim());
                    continue;
                }
                bytesArray[i] = Byte.parseByte(array[i].trim());
            }

            for (Byte b: bytesArray) {
                System.out.println("&&&&&&&&&&&&&&&&&&&&"+b);
            }
            dencryptedString = new String(cipher.doFinal(bytesArray));
            System.out.println("********************LOLLOLOLOLOL"+dencryptedString);

            return dencryptedString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return dencryptedString;
    }
}
