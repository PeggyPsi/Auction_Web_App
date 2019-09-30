package com.ted.auction_app.Utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    // Functions to provide a safe random number to be used as the public id of the user inside the database
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }

    public static boolean compareDates(Date endDate) {
        // get current time
        LocalDateTime start = LocalDateTime.now();

        LocalDateTime end = Instant.ofEpochMilli( endDate.getTime() )
                .atZone( ZoneId.systemDefault() )
                .toLocalDateTime();


//        System.out.println("start "+start);
//        System.out.println("end "+end);

        if(start.isAfter(end)) return false;
        else return true;
    }

}
