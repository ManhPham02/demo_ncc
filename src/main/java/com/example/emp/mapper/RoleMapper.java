package com.example.emp.mapper;
import com.example.emp.dao.entity.Role;
import com.example.emp.dto.RoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDTO roleDTO);
    RoleDTO toDTO(Role role);

    List<RoleDTO> toDTOs(List<Role> roles);
}
