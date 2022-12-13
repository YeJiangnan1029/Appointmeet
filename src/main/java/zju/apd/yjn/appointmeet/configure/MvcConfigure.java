package zju.apd.yjn.appointmeet.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zju.apd.yjn.appointmeet.interceptor.LoginInterceptor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MvcConfigure implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("register").setViewName("register");
        registry.addViewController("login").setViewName("login");
//        registry.addViewController("personal_info").setViewName("personal_info");
//        registry.addViewController("my_meetings").setViewName("my_meetings");
//        registry.addViewController("search_meeting").setViewName("search_meeting");
//        registry.addViewController("meeting_detail").setViewName("meeting_detail");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new LoginInterceptor();
        List<String> patterns = new ArrayList<>();
        patterns.add("/static/**");
        patterns.add("/upload/**");
        patterns.add("/login");
        patterns.add("/register");
        patterns.add("/api/register");
        patterns.add("/api/login");

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:C:/Users/Asus/Desktop/temp/");
                .addResourceLocations("file:/root/appointmeet/resource/");

    }

}
