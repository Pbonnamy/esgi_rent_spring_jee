package fr.rent.application.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@ComponentScan("fr.rent.application")
public class RentPropertyInterceptorAppConfig implements WebMvcConfigurer {

    private final RentPropertyInterceptor rentPropertyInterceptor;


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(rentPropertyInterceptor);
    }


}