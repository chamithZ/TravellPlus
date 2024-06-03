package com.travelPlus.v1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private long userId;
    private String name;
    private String email;
    private String contactNo;
    private String nic;
    private String userType;
    private String password;
    private int age;
    private long propertyId;
    private boolean userStatus;
}

