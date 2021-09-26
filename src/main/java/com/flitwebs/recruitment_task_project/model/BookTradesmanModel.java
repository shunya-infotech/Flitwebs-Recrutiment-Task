package com.flitwebs.recruitment_task_project.model;

import com.flitwebs.recruitment_task_project.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "book_tradesman")
public class BookTradesmanModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Date date;
  private Time time;
  private String status;

  @ManyToOne
  @JoinColumn(name = "customer", referencedColumnName = "id")
  private CustomerModel customer;

  @ManyToOne
  @JoinColumn(name = "tradesman", referencedColumnName = "id")
  private TradesmanModel tradesman;
}
