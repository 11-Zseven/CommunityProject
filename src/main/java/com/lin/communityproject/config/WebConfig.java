package com.lin.communityproject.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.lin.communityproject.interceptor.LoginJudgeInterceptor;
/**
 *@program: CommunityProject
 *@description:
 *@author: lin han
 *@date: 2022/09/05
 */
/*
@EnableWebMvc : 全面接管SpringMVC 所有规则都自己定义.
当只配置了该注解后,会发现原本可以直接访问的静态资源已经无法访问了,需要自己去定义规则,因为已经全面接管了,而该配置类中没有对此的操作.
 */
//@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginJudgeInterceptor loginInterceptor;

    /**
     * private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
     * WebMvcAutoConfiguraionAdapter中默认放行 CLASSPATH_RESOURCE_LOCATIONS 这些目录下的资源(静态资源)(addResourceLocations())
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
//                .excludePathPatterns("/","/loginForGit","/static/**");
                .excludePathPatterns("/loginForGit","/","/css/**","/js/**","/fonts/**");
    }
}
