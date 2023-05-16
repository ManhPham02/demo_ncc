package com.example.emp.service;

import com.example.emp.common.utils.PsException;
import com.example.emp.common.utils.Response;
import com.example.emp.dao.entity.Timekeeping;

import java.util.Date;
import java.util.List;

public interface TimeKeepService {
    String checkin(Integer code);

    String checkout(Integer code);

    List<String> findAllEMPNotCheckin(Date date);
    List<String> findAllEMPNotCheckout(Date date);
}
