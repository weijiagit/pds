package com.fykj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fykj.pds.interceptor.LoginInterceptor;
import com.fykj.web.filter.ServerSessionFilter;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by J on 2016/3/9.
 */

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Application extends SpringBootServletInitializer implements
        EmbeddedServletContainerCustomizer, WebApplicationInitializer {

    @Autowired
    private AppConfig._Config config;

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName + "->" + ctx.getBean(beanName).getClass().getName());
        }

        Environment environment = ctx.getEnvironment();
        int port = environment.getProperty("server.port", int.class);

        System.err.println("startup completely....\n"
                + "access to {swagger2} http://localhost:" + port + "/api/swagger-ui.html");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    public void customize(ConfigurableEmbeddedServletContainer container) {

    }

    @Bean
    public ServerSessionFilter serverSessionFilter() {
        return new ServerSessionFilter();
    }
    
    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(serverSessionFilter());
        registration.addUrlPatterns("/*");
        registration.setName("serverSessionFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MappingJackson2HttpMessageConverter converter =
                new MappingJackson2HttpMessageConverter(mapper);
        return converter;
    }

    @Configuration
    public static class EhcacheConfig {

//        @Bean
//        public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
//           return new EhCacheCacheManager(bean.getObject());
//        }
       
        /*
           * 据shared与否的设置,
           * Spring分别通过CacheManager.create()
           * 或new CacheManager()方式来创建一个ehcache基地.
           *
           * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
           *
           */
//          @Bean
//          public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
//            EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
//            cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
//            cacheManagerFactoryBean.setShared(true);
//            return cacheManagerFactoryBean;
//          }


    }

    @Bean(name = "kaptchaProducer")
    public Producer kaptcha() throws Exception {
        Properties properties = new Properties();
        properties.load(new ClassPathResource("kaptcha.properties").getInputStream());
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public WebMvcConfigurerAdapter corsConfigurer(@Autowired AppConfig._Config config) {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(
                        config.getCors().getOrigins().toArray(new String[]{}));
            }
            
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
            	InterceptorRegistration addInterceptor =registry.addInterceptor(loginInterceptor());
            	addInterceptor.excludePathPatterns("/jk/*");
            	addInterceptor.excludePathPatterns("/sso/*");
            	addInterceptor.excludePathPatterns("/file/*");
            	addInterceptor.excludePathPatterns("/projectReport/projectReportExportExcel");
            	addInterceptor.excludePathPatterns("/project/projectExportExcel");
            	addInterceptor.excludePathPatterns("/task/taskExportExcel");
            	super.addInterceptors(registry);
            }
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        List<String> list = new ArrayList<>();
        list.add("*");
        corsConfiguration.setAllowedOrigins(list);
    /*
    // 请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
    */
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }


}
