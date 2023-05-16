package com.example.emp.dao.entity;

import com.example.emp.dto.payload.EmployeeResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "user", schema = "public")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private Integer code;
    @CreationTimestamp
    private Date createAt;

    @Column(name = "img_user")
    private String imgUser;

    @JsonIgnore
    @OneToMany(mappedBy = "idUser", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Timekeeping> timekeepingList;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Author> authorList;


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "author", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    private List<Role> roles;


    public User(Long id, String username, String password, String firstName, String lastName, String email, Integer code, Date createAt, String imgUser, List<Timekeeping> timekeepingList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.code = code;
        this.createAt = createAt;
        this.imgUser = imgUser;
        this.timekeepingList = timekeepingList;
    }
}
