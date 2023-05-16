package com.example.emp.controller;

import com.example.emp.common.consts.Constants;
import com.example.emp.common.utils.Response;
import com.example.emp.service.UserForCheckin;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserTimeKeepController {
    private final UserForCheckin userService;

    public UserTimeKeepController(UserForCheckin userService) {
        this.userService = userService;
    }

    @GetMapping("time-keep-between-date-emp/{id}")
    public Response findTimkeepBetweenDateByEmployee(@PathVariable Long id, @RequestParam(value = "start", required = false) Optional<String> start, @RequestParam(value = "end", required = false) Optional<String> end) {
        if (start.isPresent() && end.isPresent()) {
            return Response.success("Get successful Timekeeping").withData(userService.fillAllTimkeepingByEMP(id, Constants.stringToDate(start.get()), Constants.stringToDate(end.get())));
        } else {
            return Response.success("Get successful Timekeeping").withData(userService.fillAllTimkeepingByEMP(id, new Date(Constants.getStartWeekAndEndWeekNow()[0].getTime()), new Date(Constants.getStartWeekAndEndWeekNow()[1].getTime())));
        }
    }

    @GetMapping("time-keep-user-not-checkin")
    public Response findUserNotCheckinOrError(@RequestParam(value = "month", required = false) Optional<Integer> month) {
        return Response.success("Get successful Timekeeping").withData(userService.findUserNotCheckinOrError(month));
    }

    @GetMapping("time-keep-user-not-checkin-by-emp/{id}")
    public Response findUserNotCheckinOrErrorByEMP(@PathVariable Long id, @RequestParam(value = "month", required = false) Optional<Integer> month) {
        return Response.success("Get successful Timekeeping").withData(userService.findUserNotCheckinOrErrorByEMP(month, id));
    }
}
