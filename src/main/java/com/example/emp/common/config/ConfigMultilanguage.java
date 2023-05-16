package com.example.emp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;


@Configuration
@RestController
public class ConfigMultilanguage implements WebMvcConfigurer {

    //    xác định ngôn ngữ hiện tại dựa trên phiên, cookie, tiêu đề Ngôn ngữ chấp nhận hoặc giá trị cố định.
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("vi"));
        return slr;
    }

    //    Chúng ta cần thêm một bean đánh chặn sẽ chuyển sang một ngôn ngữ mới dựa trên giá trị của tham số Lang khi có mặt trên yêu cầu:
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    //    Để LocaleChangeInterceptor này có hiệu lực, chúng tôi cần thêm nó vào sổ đăng ký đánh chặn của ứng dụng.
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");

        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }


}
