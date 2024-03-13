package com.travelPlus.v1.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Table(name="User")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId;
  private String name;
  private String email;
  private String contactNo;
  private String nic;
  private int age;
  private  String userType;
  private String password;
  LocalDateTime createdAt;

  LocalDateTime updatedAt;


  @OneToMany(mappedBy = "user")
  private List<Reservation> reservation;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(userType));
  }

  @Override
  public String getUsername() {
    return email;
  }



  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
