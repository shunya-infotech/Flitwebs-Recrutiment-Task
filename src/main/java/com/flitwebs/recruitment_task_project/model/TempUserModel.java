package com.flitwebs.recruitment_task_project.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "temp_user")
public class TempUserModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String otp;
  String mobile;

  @Column(name = "expire_time")
  Time expireTime;

  @Column(name = "created_time")
  Time createdTime;
}
