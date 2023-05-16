package com.example.emp.controller;

import com.example.emp.common.utils.Response;
import com.example.emp.dao.repository.UserRepository;
import com.example.emp.service.TimeKeepService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/time-keep")
public class TimeKeepController {

    private final TimeKeepService timeKeepService;


    UserRepository userService;

    public TimeKeepController(TimeKeepService timeKeepService, UserRepository userService) {
        this.timeKeepService = timeKeepService;
        this.userService = userService;
    }

    @GetMapping("/checkin/{code}")
    public Response checkin(@PathVariable Integer code) {
        String result = timeKeepService.checkin(code);
        return Response.success(result);
    }

    @GetMapping("/checkout/{code}")
    public Response checkout(@PathVariable Integer code) {
        String result = timeKeepService.checkout(code);
        return Response.success(result);
    }

    @PostMapping("/checkin")
    public Response checkin() {
        return Response.success("khong que security");
    }
}
