package com.example.emp.service.impl;

import com.example.emp.common.consts.Constants;
import com.example.emp.common.consts.ExceptionConst;
import com.example.emp.common.utils.CustomException;
import com.example.emp.common.utils.PsException;
import com.example.emp.dao.entity.Timekeeping;
import com.example.emp.dao.entity.User;
import com.example.emp.dao.repository.TimekeepingRepository;
import com.example.emp.dao.repository.UserRepository;
import com.example.emp.dto.CustomUserDetails;
import com.example.emp.service.TimeKeepService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TimeKeepServiceImpl implements TimeKeepService {
    private final TimekeepingRepository timeKeepingRepository;


    private final UserRepository userRepository;

    public TimeKeepServiceImpl(TimekeepingRepository timeKeepingRepository, UserRepository userRepository) {
        this.timeKeepingRepository = timeKeepingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String checkin(Integer code) {
        String email = returnEmail();

        //Kiểm tra xem nhân viên đã có trong hệ thống không
//            if (!email.isEmpty()) {
//                for (int i = 0; i < userForCheckin.findAllUserToWebClient().size(); i++) {
//                    if (userForCheckin.findAllUserToWebClient().get(i).getEmail().equals(email)) {
//                        if (email.equals(userRepository.findByEmail(email))) {
//                            email = userForCheckin.findAllUserToWebClient().get(i).getEmail();
//                            Timekeeping timekeeping = new Timekeeping();
//                            timekeeping.setCheckin(Constants.getTimeNow());
//                            timekeeping.setIdUser(userRepository.findByEmail(email));
//
//                            timeKeepingRepository.save(timekeeping);
//                            return Response.success("Checkin thành công");
//                        } else {
//                            return Response.error("Email không tồn tại trong hệ thống");
//                        }
//                    } else {
//                        return Response.error("Email không tồn tại trong hệ thống công ty");
//                    }
//                }
//            }

        User user = userRepository.findByEmailAndCode(email, code);
        if (user == null) {
            return "Employee ID does not exist or is not your code";
        } else {
            Timekeeping timekeeping = new Timekeeping();
            timekeeping.setCheckin(Constants.getTimeNow());
            timekeeping.setIdUser(user);
            timeKeepingRepository.save(timekeeping);
            return user.getUsername() + " Successfully checked in";
        }
    }

    public String returnEmail() {
        String email = "";
        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (context instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            email = user.getUsername();
        } else {
            CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            email = user.getUser().getEmail();
        }
//        }
        return email;
    }

    @Override
    @Transactional
    public String checkout(Integer code) {
        String email = returnEmail();
        User user = userRepository.findByEmailAndCode(email, code);
        if (user == null) {
            return "Employee ID does not exist or is not your code";
        } else {

            Timekeeping timekeeping = timeKeepingRepository.findByCodeEmpAndCheckin(userRepository.findByCode(code).getId(), new Date());
            if (timekeeping.getCheckin().isEmpty()) {
                timekeeping.setCheckin(Constants.getTimeNow());
            }
            timekeeping.setCheckout(Constants.getTimeNow());
            timeKeepingRepository.save(timekeeping);
            return user.getUsername() + " Checkout successful";
        }
    }

    @Override
    public List<String> findAllEMPNotCheckin(Date date) {
        List<String> result = userRepository.listEmpNotCheckin(date);
        if (result.isEmpty()) {
            throw new CustomException("There are no employees who have not checked in", HttpStatus.NOT_FOUND);
        } else {
            return result;
        }
    }

    @Override
    public List<String> findAllEMPNotCheckout(Date date) {
        List<String> result = Optional.ofNullable(userRepository.listEmpNotCheckOut(date)).orElseThrow(() -> new CustomException("There are no employees who have not checked out", HttpStatus.NOT_FOUND));
        return result;
    }
}

