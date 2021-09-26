package com.flitwebs.recruitment_task_project.service.impl;

import com.flitwebs.recruitment_task_project.enums.Status;
import com.flitwebs.recruitment_task_project.model.CustomerModel;
import com.flitwebs.recruitment_task_project.repository.CustomerRepository;
import com.flitwebs.recruitment_task_project.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  @Override
  public CustomerModel saveCustomer(String mobile) {
    log.info(">>>saveCustomer() called inside CustomerServiceImpl");

    return Optional.ofNullable(customerRepository.findByMobile(mobile))
        .orElseGet(
            () ->
                customerRepository.save(
                    CustomerModel.builder()
                        .createdDate(Date.valueOf(LocalDate.now()))
                        .mobile(mobile)
                        .status(Status.INACTIVE.toString())
                        .build()));
  }

  @Override
  public CustomerModel findById(Integer id) {
    log.info(">>>findById() called inside CustomerServiceImpl");

    return customerRepository.findById(id).orElse(null);
  }

  @Override
  public CustomerModel findByMobile(String mobile) {
    log.info(">>>findByMobile() called inside CustomerServiceImpl");

    return customerRepository.findByMobile(mobile);
  }
}
