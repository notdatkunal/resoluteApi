package com.resolute.zero.utilities;

import com.resolute.zero.exceptions.LogoutException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;



public class LoginUtility {
    public static void authenticate(HttpSession session) throws LogoutException {
        String user = (String) session.getAttribute("user");
        if(user==null){
            throw new LogoutException("user not logged in ");
        }
    }
}
