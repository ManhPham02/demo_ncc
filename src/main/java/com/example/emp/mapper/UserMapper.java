package com.example.emp.mapper;

import com.example.emp.dao.entity.User;
import com.example.emp.dto.UserDTO;
import com.example.emp.dto.payload.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDTO(User user);
    List<UserDTO> toDTOs(List<User> users);
    @Mapping(target = "imgUser",source = "imgUser.originalFilename")
    User toEntity(UserRequest userRequest);

//    @Named("imgUserToString")
//    default String imgUserToString(MultipartFile imgUser){
//        return imgUser.getOriginalFilename();
//    }
}
