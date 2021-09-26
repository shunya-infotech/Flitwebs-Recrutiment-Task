package com.flitwebs.recruitment_task_project.controller;

import com.flitwebs.recruitment_task_project.constants.ResponseStatus;
import com.flitwebs.recruitment_task_project.model.CustomerModel;
import com.flitwebs.recruitment_task_project.service.CustomerService;
import com.flitwebs.recruitment_task_project.service.ImageUploadService;
import com.flitwebs.recruitment_task_project.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerService customerService;
  private final ImageUploadService imageUploadService;

  @GetMapping("/profile")
  public ResponseEntity<?> getCustomerProfile(@RequestParam String mobile) {
    log.info(">>>>url:'/customer/profile' with method:'get' called in CustomerController");
    log.info(">>>>get customer profile for mobile number: " + mobile);

    ResponseWrapper<CustomerModel> responseWrapper = new ResponseWrapper();

    CustomerModel customer = customerService.findByMobile(mobile);
    responseWrapper.bundleResponse(ResponseStatus.get(1006), "", customer);

    return ResponseEntity.ok(responseWrapper);
  }

  @PostMapping(
      value = "/profile/image",
      consumes = {"multipart/form-data"})
  public ResponseEntity<?> uploadImage(
      @RequestParam String mobile, @RequestPart MultipartFile image) {
    log.info(">>>>url:'/customer/profile/image' with method:'post' called in CustomerController");
    log.info(">>>>upload image for customer profile with mobile number: " + mobile);

    ResponseWrapper<CustomerModel> responseWrapper = new ResponseWrapper();

    imageUploadService.uploadImage(mobile, image);
    responseWrapper.bundleResponse(
        ResponseStatus.get(1005), "Profile Image Updated Successfully", null);

    return ResponseEntity.ok(responseWrapper);
  }
}
