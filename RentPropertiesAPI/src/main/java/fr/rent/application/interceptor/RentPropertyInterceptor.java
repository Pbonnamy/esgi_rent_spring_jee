package fr.rent.application.interceptor;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RentPropertyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Dotenv dotenv = Dotenv.load();

        String token = dotenv.get("AUTH_TOKEN");

        if (request.getHeader("Authorization") == null
                || request.getHeader("Authorization").split(" ").length != 2
                || !request.getHeader("Authorization").split(" ")[0].equals("Bearer")
                || !request.getHeader("Authorization").split(" ")[1].equals(token)) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
}