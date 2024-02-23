package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="User")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;
  private String name;
  private String email;
  private String contactNo;
  private String nic;
  private int age;
}
