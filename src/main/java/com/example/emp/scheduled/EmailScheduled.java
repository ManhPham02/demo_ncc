package com.example.emp.scheduled;

import com.example.emp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailScheduled {
    @Autowired
    private EmailService emailService;

    @Async
    @Scheduled(cron = "0 0 7 ? * MON-FRI")
    public void sendMailCheckinOrCheckout() {
//        List<String> listIdUserCheckIn = timeKeepService.findAllEMPNotCheckin(new Date());
        emailService.sendSimpleMail("You have not checked in the system today", "manh01qn@gmail.com");
//        for (String email : listIdUserCheckIn) {
//            emailService.sendSimpleMail("Bạn chưa checkin vào hệ thống", email);
//        }
//        List<String> listIdUserCheckOut = timeKeepService.findAllEMPNotCheckout(new Date());
//        for (String email : listIdUserCheckOut) {
//            emailService.sendSimpleMail("Bạn chưa checkout vào hệ thống", email);
//        }
    }

    //to do - cài dược Scheduled theo ý muốn
}
