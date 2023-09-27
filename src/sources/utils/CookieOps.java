package utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieOps {
    public  static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge){
        Cookie newCookie = new Cookie(cookieName, cookieValue);

        newCookie.setMaxAge(maxAge);
        response.addCookie(newCookie);
    }

    public static void updateCookie(HttpServletResponse response, Cookie cookie, String newValue){
        cookie.setValue(newValue);
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)){
                return cookie;
            }
        }
        return null;
    }

    public static void invalidateCookie(HttpServletResponse response, Cookie cookie){
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }

    public static void flushCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] listaCookie = request.getCookies();

        for(Cookie cookie:listaCookie){
            invalidateCookie(response, cookie);
        }
    }

    public static boolean areThereCookies(HttpServletRequest request){
        if(request.getCookies() != null && request.getCookies().length > 0){
            return true;
        } else return false;
    }

}
