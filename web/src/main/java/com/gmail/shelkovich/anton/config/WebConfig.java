package com.gmail.shelkovich.anton.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import javax.servlet.ServletContext;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages={"com.gmail.shelkovich.anton.web.controller"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ServletContext servletContext;

    @Bean
    public TilesViewResolver getTilesViewResolver() {
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setViewClass(TilesView.class);
        tilesViewResolver.setExposeContextBeansAsAttributes(true);
        tilesViewResolver.setExposedContextBeanNames("bucketService");
        return tilesViewResolver;
    }

    /**
     * <code>Configures Apache tiles definitions bean used by Apache TilesViewResolver to resolve views selected for rendering by @Controllers</code>
     */
    @Bean
    public TilesConfigurer getTilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setCheckRefresh(true);
        tilesConfigurer.setDefinitionsFactoryClass(TilesConfig.class);

        // Add apache tiles definitions
        TilesConfig.addDefinitions(servletContext.getRealPath("/WEB-INF/views/"), LayoutType.STANDART);
        TilesConfig.addDefinitions(servletContext.getRealPath("/WEB-INF/views/ajax/"), LayoutType.AJAX);
        TilesConfig.addDefinitions(servletContext.getRealPath("/WEB-INF/views/admin/"), LayoutType.ADMIN);

        return tilesConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("enterprise.java.project@gmail.com");
        mailSender.setPassword("zeftvgjy");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(1000240); //1000Kb
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        return resolver;
    }
}
