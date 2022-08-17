package com.kimyoong.practice.utill;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

        public static <T extends UserDetails> T  getAuthenticationPrincipal(){

            T type = null;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object detail = authentication.getDetails();
            if(detail != null){
                type = (T) detail;
            }

            return type;
        }
        public static <T> Boolean checkRole(String Role){

            T userDetails = getAuthenticationPrincipal();
            boolean result = false;
            if(userDetails !=null){

            }
            return result;
        }

}
