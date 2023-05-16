package com.example.emp.controller;

import com.example.emp.common.utils.Response;
import com.example.emp.dto.UserForWebClientResult;
import com.example.emp.dto.UserDTO;
import com.example.emp.dto.UserWebClientResult;
import com.example.emp.dto.payload.UserRequest;
import com.example.emp.service.UserForCheckin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final WebClient webClient;

    private final UserForCheckin userService;

    public UserController(WebClient webClient, UserForCheckin userService) {
        this.webClient = webClient;
        this.userService = userService;
    }

    @GetMapping()
    public Response findAll(@RequestParam(value = "field", required = false) Optional<String> field, @RequestParam(value = "sort", required = false) Optional<String> sort, @RequestParam(value = "page", required = false) Optional<Integer> page) {
        return Response.success("Bringing information to success").withData(userService.fillAll(field, sort, page));
    }

    @GetMapping("{id}")
    public Response findById(@PathVariable Long id) {
        return Response.success("Bringing information to success").withData(userService.findById(id));
    }


    @PostMapping
    public Response save(@ModelAttribute UserRequest form) {
        return Response.success("Successfully added new").withData(userService.save(form));
    }

    @PutMapping("{id}")
    public Response update(@PathVariable Long id, @RequestBody UserDTO form) {
        return Response.success("Update successful").withData(userService.update(id, form));
    }

    @DeleteMapping(path = "/{id}")
    public Response delete(@PathVariable Long id) {
        return Response.success("Delete successfully").withData(userService.delete(id));
    }

    @GetMapping(value = "/GetUserForCheckinWebclient")
    public Response findAll() {
        Flux<com.example.emp.dto.UserWebClientResult> testFlux = webClient.get().uri("/GetUserForCheckIn").retrieve().bodyToFlux(UserWebClientResult.class);
        List<UserForWebClientResult> userTests = testFlux.toIterable().iterator().next().getResult();
//        for (UserForWebClientResult userTest : userTests) {
//            System.out.println(userTest.getEmail());
//        }
        return Response.success("Bringing information to success").withData(userTests);
    }

}
