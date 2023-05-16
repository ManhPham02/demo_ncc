package com.example.emp.controller;

import com.example.emp.common.utils.Response;
import com.example.emp.dto.payload.LoginRequest;
import com.example.emp.dto.payload.LoginResponse;
import com.example.emp.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

    LoginServiceImpl loginService;

    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/login")
    public Response authenticateUser(@RequestBody LoginRequest loginRequest) {
        return Response.success("Login success").withData(new LoginResponse(loginService.login(loginRequest)));
    }

    @RequestMapping("/oath2/login/success")
    public Response loginGoogle(HttpServletRequest request) {
        return Response.success("Login success").withData(new LoginResponse(loginService.loginGG(request)));
    }


    // api để trả về không có quyền truy cập
    @GetMapping("/api/access-denied")
    public Response accessDenied() {
        return Response.error("Access denied");
    }


    @GetMapping("/api/logout/success")
    public Response logoutSuccess() {
        return Response.success(loginService.logout());
    }

    //failure login
    @GetMapping("oath2/login/failure")
    public Response loginFailure() {
        return Response.error("Login failure");
    }


}
