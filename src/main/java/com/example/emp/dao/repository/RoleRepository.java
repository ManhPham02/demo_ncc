package com.example.emp.dao.repository;

import com.example.emp.dao.entity.Role;
import com.example.emp.dto.RoleInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r.name from User u join Author a on u.id = a.idUser\n" +
            "join Role r on a.idRole=r.id\n" +
            "where u.username = ?1")
    List<String> findRoleNameByUser(String username);


    @Query(value = "select r.* from role r ", nativeQuery = true)
    List<Role> findAllRole();

    @Query(value = "select r.name from role r", nativeQuery = true)
    List<RoleInterface> findAllRoleUseInterface();
}
