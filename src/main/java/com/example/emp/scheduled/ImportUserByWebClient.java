//package com.example.emp.scheduled;
//
//import com.example.emp.common.consts.Constants;
//import com.example.emp.dao.entity.User;
//import com.example.emp.dao.repository.UserRepository;
//import com.example.emp.dto.UserForWebClientResult;
//import com.example.emp.dto.UserWebClientResult;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//
//import java.util.List;
//
//public class ImportUserByWebClient {
//
//    private final WebClient webClient;
//
//    private final UserRepository userService;
//
//    public ImportUserByWebClient(WebClient webClient, UserRepository userService) {
//        this.webClient = webClient;
//        this.userService = userService;
//    }
//
//
//
//    @Async
//    @Scheduled(cron = "0 0 0 * * ?")
//    @Transactional(rollbackFor = Exception.class)
//    public void importUserByWebClient() {
//        List<User> users = userService.findAll();
//        Flux<UserWebClientResult> resultFlux = webClient.get().uri("/GetUserForCheckIn").retrieve().bodyToFlux(UserWebClientResult.class);
//        List<UserForWebClientResult> userWebClient = resultFlux.toIterable().iterator().next().getResult();
//
//
//        userWebClient.forEach(user -> {
//            if (users.stream().noneMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {
//                User usernew = new User();
//                usernew.setEmail(user.getEmail());
//                usernew.setFirstName(user.getFirstName());
//                usernew.setLastName(user.getLastName());
//                usernew.setCode(Constants.randomCodeEMP());
//                usernew.setImgUser("No image");
//                usernew.setPassword("$2a$10$Lrn5vm..TJg8pJ4gDI047uHfzFT67ftcXeYM8YYmris3PcBNje77G");
//                usernew.setUsername(user.getEmail().substring(0, user.getEmail().indexOf("@")));
//                userService.save(usernew);
//            }
//        });
//    }
//}
