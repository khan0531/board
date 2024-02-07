package com.example.board.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false, length = 100, unique = true)
  private String username;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(nullable = false, length = 50)
  private String email;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  private LoginType loginType;

  @CreationTimestamp
  private Timestamp createDate;
}
