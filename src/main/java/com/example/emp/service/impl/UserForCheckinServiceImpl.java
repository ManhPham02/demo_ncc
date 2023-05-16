package com.example.emp.service.impl;

import com.example.emp.common.consts.Constants;
import com.example.emp.common.utils.CustomException;
import com.example.emp.common.utils.PsException;
import com.example.emp.dao.entity.Author;
import com.example.emp.dao.entity.User;
import com.example.emp.dao.repository.AuthorRepository;
import com.example.emp.dao.repository.UserRepository;
import com.example.emp.dto.UserDTO;
import com.example.emp.dto.UserForWebClientResult;
import com.example.emp.dto.UserWebClientResult;
import com.example.emp.dto.payload.EmployeeResponse;
import com.example.emp.dto.payload.UserRequest;
import com.example.emp.mapper.UserMapper;
import com.example.emp.service.EmailService;
import com.example.emp.service.UserForCheckin;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.mail.MessagingException;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserForCheckinServiceImpl implements UserForCheckin {

    private final UserMapper userMapper;


    private final UserRepository userRepository;


    private final PasswordEncoder bCryptPasswordEncoder;

    private final WebClient webClient;


    EmailService emailService;

    AuthorRepository authorRepository;

    public UserForCheckinServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, WebClient webClient, EmailService emailService, AuthorRepository authorRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.webClient = webClient;
        this.emailService = emailService;
        this.authorRepository = authorRepository;
    }

    @Override
    @Cacheable("user")
    public List fillAll(Optional<String> field, Optional<String> sortType, Optional<Integer> page) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortType.orElse("ASC")), field.orElse("username"));
        Pageable pageable = PageRequest.of(page.orElse(0), 2, sort);
        Page<User> pageUsers = Optional.of(userRepository.findAll(pageable)).orElseThrow(() -> new CustomException("Not found", HttpStatus.NOT_FOUND));
        if (!pageUsers.getContent().isEmpty()) {
            return userMapper.toDTOs(pageUsers.getContent());
        } else {
            throw new CustomException("Not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO save(UserRequest form) {
        try {
            String image = "NoImage.png";
            Path path = Paths.get("uploads/");

            if (form.getImgUser() != null) {
                File file = new File("uploads/", form.getImgUser().getOriginalFilename());
                if (file.exists()) {
                    image = form.getImgUser().getOriginalFilename();
                } else {
                    try {
                        InputStream inputStream = form.getImgUser().getInputStream();
                        Files.copy(inputStream, path.resolve(form.getImgUser().getOriginalFilename()), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        image = form.getImgUser().getOriginalFilename();
                    } catch (Exception e) {
                        throw new CustomException("Error", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
            String password = Constants.randomPassword();
            User user = userMapper.toEntity(form);
            user.setCode(Constants.randomCodeEMP());
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setImgUser(image);
            user.setCreateAt(new Date());
            if (userRepository.save(user) != null) {
                user.setAuthorList(Arrays.asList(new Author(user.getId(), 2L)));
                emailService.sendHtmlMessage(user, password, new Locale("en"));
                return userMapper.toDTO(user);
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            throw new CustomException("Save fail ", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @CachePut(value="userid")
//    @Cacheable("userid")
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("not found", HttpStatus.NOT_FOUND));
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO update(Long id, UserDTO form) {
        User user = userMapper.toEntity(form);
        user.setId(id);
        user.setCode(userRepository.findById(id).get().getCode());
        user.setPassword(userRepository.findById(id).get().getPassword());
        user.setCreateAt(userRepository.findById(id).get().getCreateAt());
        if (userRepository.save(user) != null) {
            return userMapper.toDTO(user);
        } else {
            throw new CustomException("Update fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO delete(Long id) {
        User user = userRepository.findById(id).get();
        if (user != null) {
            userRepository.delete(user);
            return userMapper.toDTO(user);
        } else {
            throw new CustomException("Delete fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable("timekeepingByUser")
    public List fillAllTimkeepingByEMP(Long idUser, Date start, Date end) {
        List<EmployeeResponse> list = userRepository.findTimeKeepByCodeEmpBetweenDate(idUser, start, end);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new CustomException("Not found", HttpStatus.NOT_FOUND);
        }
    }

    // Cachea put

    @Override
    public List findUserNotCheckinOrError(Optional<Integer> month) {
        if (month.isPresent()) {
            List<EmployeeResponse> list = userRepository.findUserNotCheckinOrError(month.get());
            if (!list.isEmpty()) {
                return list;
            } else {
                throw new CustomException("Not found", HttpStatus.NOT_FOUND);
            }
        } else {
            month = Optional.of(new java.sql.Date(new Date().getTime()).getMonth());
            return userRepository.findUserNotCheckinOrError(month.get());
        }
    }


    @Override
    public List findUserNotCheckinOrErrorByEMP(Optional<Integer> month, Long idUser) {
        if (month.isPresent()) {
            List<EmployeeResponse> list = userRepository.findUserNotCheckinOrErrorByEMP(month.get(), idUser);
            if (!list.isEmpty()) {
                return list;
            } else {
                throw new CustomException("Not found", HttpStatus.NOT_FOUND);
            }
        } else {
            month = Optional.of(new Date().getMonth() + 1);
            return userRepository.findUserNotCheckinOrErrorByEMP(month.get(), idUser);
        }
    }

    @Override
    @Cacheable("userByWebClient")
    public List<UserForWebClientResult> findAllUserToWebClient() {
        Flux<UserWebClientResult> testFlux = webClient.get().uri("/GetUserForCheckin").retrieve().bodyToFlux(UserWebClientResult.class);
        return testFlux.toIterable().iterator().next().getResult();
    }

}

