package com.flitwebs.recruitment_task_project.model;

import com.flitwebs.recruitment_task_project.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "trade_type")
public class TradeTypeModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String status;

  @Column(name = "trade_type")
  private String tradeType;
}
