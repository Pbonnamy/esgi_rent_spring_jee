package fr.esgi.rent.interceptor;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Dotenv dotenv = Dotenv.load();

        String token = dotenv.get("AUTH_TOKEN");

        if (request.getHeader("Authorization") == null || !request.getHeader("Authorization").equals("Bearer " + token)) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
