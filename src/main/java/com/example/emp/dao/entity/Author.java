package com.example.emp.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author", schema = "public")
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "id_role")
    private Long idRole;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_role", insertable = false, updatable = false)
    private Role role;

    @Override
    public String toString() {
        return role.getName();
    }

    public Author(Long idUser, Long idRole) {
        this.idUser = idUser;
        this.idRole = idRole;
    }

    public Author(Long id, Long idUser, Long idRole) {
        this.id = id;
        this.idUser = idUser;
        this.idRole = idRole;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User user;
}
