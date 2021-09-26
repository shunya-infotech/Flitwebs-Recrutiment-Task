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
@Table(name = "image_upload")
public class ImageUploadModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String status;

  @Column(name = "image_path")
  private String imagePath;

  @Column(name = "created_date")
  private Date createdDate;

  @ManyToOne
  @JoinColumn(name = "customer", referencedColumnName = "id")
  private CustomerModel customer;
}
