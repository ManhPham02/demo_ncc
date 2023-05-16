package com.example.emp.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EmployeeResponse {
    private Long id;
    private String username;
    private String checkin;
    private String checkout;
    private Date createAt;

    public EmployeeResponse(String username) {
        this.username = username;
    }

    public EmployeeResponse(Long id, String username, String checkin, String checkout, Date createAt) {
        this.id = id;
        this.username = username;
        this.checkin = checkin;
        this.checkout = checkout;
        this.createAt = createAt;
    }

    public EmployeeResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public EmployeeResponse(Long id) {
        this.id = id;
    }
}
