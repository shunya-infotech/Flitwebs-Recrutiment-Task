package com.flitwebs.recruitment_task_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookTradesmanRequest {

  Integer customerId;
  Integer tradesmanId;
  Date date;
  Time time;
}
