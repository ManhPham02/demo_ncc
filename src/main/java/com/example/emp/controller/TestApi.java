//package com.example.emp.controller;
//
//import com.example.emp.common.utils.PsException;
//import com.example.emp.common.utils.Response;
//import com.example.emp.dao.entity.User;
//import com.example.emp.dao.repository.RoleRepository;
//import com.example.emp.dao.repository.UserRepository;
//import com.example.emp.dto.UserForWebClientResult;
//import com.example.emp.dto.UserWebClientResult;
//import com.example.emp.mapper.RoleMapper;
//import com.example.emp.dto.payload.LoginRequest;
//import com.example.emp.scheduled.ImportUserByWebClient;
//import com.example.emp.service.EmailService;
//import com.example.emp.service.TimeKeepService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//
//import javax.mail.MessagingException;
//import java.util.List;
//import java.util.Locale;
//
//@RestController
//public class TestApi {
//    @Autowired
//    PasswordEncoder bCryptPasswordEncoder;
//    @Autowired
//    EmailService emailService;
//    @Autowired
//    RoleMapper roleMapper;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    RoleRepository roleRepository;
//    @Autowired
//    Environment environment;
//    @Autowired
//    TimeKeepService timeKeepService;
//    @Autowired
//    private WebClient webClient;
//    @Autowired
//    ResourceBundleMessageSource messageSource;
//
//
//    ImportUserByWebClient importUserByWebClient =new ImportUserByWebClient(webClient,userRepository);
//
//
//    @GetMapping("/testProjections")
//    public Response test() {
//        return Response.success("Customizing the Result with Spring Data Projection (open/close)").withData(roleRepository.findAllRoleUseInterface());
//    }
//
//
//    //    @Scheduled(cron = "0 15 10 ? * *")Chạy lúc 10 giờ 15 phút sáng hàng ngày
////    0 0 18 ? * MON-FRI  	Chạy lúc 18 giờ 00 phút các ngày thứ 2 đến thứ 6
//    //send mail
//    @GetMapping("/sendmail")
//    public Response sendMail() {
//        emailService.sendSimpleMail("2222", "manh01qn@gmail.com");
//        return Response.success("test send mail");
//    }
//
//    @GetMapping("/sendmail2")
//    public Response sendMail2(@RequestParam(value = "lang") String lang) throws MessagingException {
//        emailService.sendHtmlMessage(new User(), "123456", new Locale(lang));
//        return Response.success("test send mail");
//    }
//
//
//
//    @GetMapping(value = "/testWebClient")
//    public List<UserForWebClientResult> findAll() {
//        Flux<com.example.emp.dto.UserWebClientResult> testFlux = webClient.get()
//                .uri("/GetUserForCheckIn")
//                .retrieve()
//                .bodyToFlux(UserWebClientResult.class);
//        List<UserForWebClientResult> userTests = testFlux.toIterable().iterator().next().getResult();
//        for (UserForWebClientResult userTest : userTests) {
//            System.out.println(userTest.getEmail());
//        }
//        return userTests;
//    }
//
//
//    @GetMapping("/testMultiLanguage")
//    public String test(@RequestParam(value = "lang") String lang) {
//        if (lang.isEmpty()) {
//            throw new PsException("lang is empty", new NullPointerException());
//        }
//        return messageSource.getMessage("body", null, new Locale(lang));
//    }
//
//    @GetMapping("/testJoinTable")
//    public Response testJoinTable() {
//        return Response.success("test join table").withData(userRepository.findAll().get(0).getRoles());
//    }
//    @GetMapping("/importUser")
//    public Response importUser() {
//        importUserByWebClient.importUserByWebClient();
//        return Response.success("test import User to Webclient");
//    }
//}
