package com.flitwebs.recruitment_task_project.service.impl;

import com.flitwebs.recruitment_task_project.enums.Status;
import com.flitwebs.recruitment_task_project.model.BookTradesmanModel;
import com.flitwebs.recruitment_task_project.model.request.BookTradesmanRequest;
import com.flitwebs.recruitment_task_project.repository.BookTradesmanRepository;
import com.flitwebs.recruitment_task_project.service.BookTradesmanService;
import com.flitwebs.recruitment_task_project.service.CustomerService;
import com.flitwebs.recruitment_task_project.service.TradesmanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class BookTradesmanServiceImpl implements BookTradesmanService {


  private final TradesmanService tradesmanService;
  private final CustomerService customerService;

  private final BookTradesmanRepository bookTradesmanRepository;

  @Override
  public BookTradesmanModel bookTradesman(BookTradesmanRequest bookTradesmanRequest) {
    log.info(">>>bookTradesman(BookTradesmanRequest) called inside BookTradesmanServiceImpl");

    return bookTradesman(
        BookTradesmanModel.builder()
            .customer(customerService.findById(bookTradesmanRequest.getCustomerId()))
            .date(bookTradesmanRequest.getDate())
            .status(Status.BOOKED.toString())
            .time(bookTradesmanRequest.getTime())
            .tradesman(tradesmanService.findById(bookTradesmanRequest.getTradesmanId()))
            .build());
  }

  @Override
  public BookTradesmanModel bookTradesman(BookTradesmanModel bookTradesman) {
    log.info(">>>bookTradesman(BookTradesmanModel) called inside BookTradesmanServiceImpl");

    return bookTradesmanRepository.save(bookTradesman);
  }
}
