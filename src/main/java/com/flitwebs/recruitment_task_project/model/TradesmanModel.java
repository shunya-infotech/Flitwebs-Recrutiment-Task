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
@Table(name = "tradesman")
public class TradesmanModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String status;

  @Column(name = "created_date")
  private Date createdDate;

  @ManyToOne
  @JoinColumn(name = "trade_type", referencedColumnName = "id")
  private TradeTypeModel tradeType;
}
