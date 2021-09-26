package com.flitwebs.recruitment_task_project.controller;

import com.flitwebs.recruitment_task_project.constants.ResponseStatus;
import com.flitwebs.recruitment_task_project.enums.Status;
import com.flitwebs.recruitment_task_project.model.BookTradesmanModel;
import com.flitwebs.recruitment_task_project.model.TradesmanModel;
import com.flitwebs.recruitment_task_project.model.request.BookTradesmanRequest;
import com.flitwebs.recruitment_task_project.service.BookTradesmanService;
import com.flitwebs.recruitment_task_project.service.CustomerService;
import com.flitwebs.recruitment_task_project.service.TradesmanService;
import com.flitwebs.recruitment_task_project.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/book-tradesman")
public class BookTradesmanController {

  private final TradesmanService tradesmanService;
  private final BookTradesmanService bookTradesmanService;
  private final CustomerService customerService;

  @PostMapping
  public ResponseEntity<?> bookTradesman(@RequestBody BookTradesmanRequest bookTradesmanRequest) {
    log.info(">>>>url:'/book-tradesman' with method:'post' called in BookTradesmanController");
    log.info(">>>>book tradesman");

    ResponseWrapper<BookTradesmanModel> responseWrapper = new ResponseWrapper<>();

    if (customerService.findById(bookTradesmanRequest.getCustomerId()) == null)
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(
              ResponseStatus.get(2001),
              "Customer not exist with given id: " + bookTradesmanRequest.getCustomerId(),
              null));

    TradesmanModel tradesman = tradesmanService.findById(bookTradesmanRequest.getTradesmanId());
    if (tradesman == null)
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(
              ResponseStatus.get(2001),
              "Tradesman not exist with given id: " + bookTradesmanRequest.getTradesmanId(),
              null));

    if (!Status.ACTIVE.toString().equalsIgnoreCase(tradesman.getStatus().toString()))
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(
              ResponseStatus.get(2001), "Tradesman is not active, id: " + tradesman.getId(), null));

    bookTradesmanService.bookTradesman(bookTradesmanRequest);
    responseWrapper.bundleResponse(ResponseStatus.get(1004), "Request Added Successfully", null);
    return ResponseEntity.ok(responseWrapper);
  }
}
