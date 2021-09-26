package com.flitwebs.recruitment_task_project.model;

import com.flitwebs.recruitment_task_project.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "customer")
public class CustomerModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Date dob;
  private String email;
  private String mobile;
  private String status;

  @Column(name = "created_date")
  private Date createdDate;
}
