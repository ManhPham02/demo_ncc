package com.example.emp.dao.repository;

import com.example.emp.dao.entity.Timekeeping;
import com.example.emp.dao.entity.User;
import com.example.emp.dto.payload.EmployeeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByCode(Integer code);

    User findByEmail(String email);

    User findByEmailAndCode(String email, Integer code);

    @Query(value = "select new com.example.emp.dto.payload.EmployeeResponse(u.id,u.username,t.checkin,t.checkout,t.createAt) from User u left join Timekeeping t on u.id = t.idUser.id\n" + "where u.id = ?1 and t.createAt between ?2 and ?3")
    List<EmployeeResponse> findTimeKeepByCodeEmpBetweenDate(Long codeEmp, Date dateFrom, Date dateTo);

    @Query("select new com.example.emp.dto.payload.EmployeeResponse(u.id,u.username,t.checkin,t.checkout,t.createAt) from User u  join Timekeeping t on u.id = t.idUser.id where t.checkin > '08:30:00' or t.checkout is null or\n" + "t.checkin is null and extract(month from t.createAt) = ?1")
    List<EmployeeResponse> findUserNotCheckinOrError(Integer month);

    @Query(value = "select new  com.example.emp.dto.payload.EmployeeResponse(u.id,u.username,t.checkin,t.checkout,t.createAt) from User u  join Timekeeping t on u.id = t.idUser.id where extract(month from t.createAt) = ?1 and t.idUser.id = ?2 and t.checkin > '08:30:00' or t.checkout is null or\n" + "t.checkin is null")
    List<EmployeeResponse> findUserNotCheckinOrErrorByEMP(Integer month, Long idUser);

    @Query("select distinct u.email from User u Left JOIN  Timekeeping t on u.id = t.idUser.id where t.createAt = ?1 and t.checkin is null")
    List<String> listEmpNotCheckin(Date date);

    @Query("select distinct u.email from User u Left JOIN  Timekeeping t on u.id = t.idUser.id where t.createAt = ?1 and t.checkout is null")
    List<String> listEmpNotCheckOut(Date date);
}
