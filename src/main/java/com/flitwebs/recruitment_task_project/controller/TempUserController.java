package com.flitwebs.recruitment_task_project.controller;

import com.flitwebs.recruitment_task_project.constants.ResponseStatus;
import com.flitwebs.recruitment_task_project.model.TempUserModel;
import com.flitwebs.recruitment_task_project.security.JwtTokenUtil;
import com.flitwebs.recruitment_task_project.service.CustomerService;
import com.flitwebs.recruitment_task_project.service.TempUserService;
import com.flitwebs.recruitment_task_project.service.TwilioService;
import com.flitwebs.recruitment_task_project.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("")
public class TempUserController {

  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;

  private final TwilioService twilioService;
  private final TempUserService tempUserService;
  private final CustomerService customerService;

  @PostMapping("/send-otp")
  public ResponseEntity<?> sendOTP(@RequestParam String mobile) {
    log.info(">>>>url:'/send-otp' with method:'post' called in TempUserController");
    log.info(">>>>sending otp to mobile number: " + mobile);

    ResponseWrapper<?> responseWrapper = new ResponseWrapper();
    TempUserModel tempUser = tempUserService.findByMobile(mobile);
    if (tempUser != null) {

      int isOtpExpired = tempUser.getExpireTime().compareTo(Time.valueOf(LocalTime.now()));
      if (isOtpExpired > 0) {
        log.info(">>>>resending otp to mobile number: " + mobile);

        twilioService.sendMessage("+91" + mobile, "OTP for verification is: " + tempUser.getOtp());
        responseWrapper.bundleResponse(
            ResponseStatus.get(1003), "Verification OTP already sent on the mobile number", null);
        return ResponseEntity.ok(responseWrapper);
      }
    }

    Integer otp = (int) (Math.random() * 9000) + 1000;
    tempUserService.saveTempUser(mobile, otp);
    twilioService.sendMessage("+91" + mobile, "OTP for verification is: " + otp);

    responseWrapper.bundleResponse(
        ResponseStatus.get(1003), "Verification OTP sent on the mobile number", null);

    return ResponseEntity.ok(responseWrapper);
  }

  @PostMapping("/verify-otp")
  public ResponseEntity<?> verifyOTP(@RequestParam String mobile, @RequestParam Integer otp)
      throws Exception {
    log.info(">>>>url:'/verify-otp' with method:'post' called in TempUserController");
    log.info(">>>>verifying otp: " + otp + ", for mobile number: " + mobile);

    ResponseWrapper<Map<String, String>> responseWrapper = new ResponseWrapper();
    TempUserModel tempUser = tempUserService.findByMobile(mobile);
    if (tempUser == null)
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(ResponseStatus.get(2001), "User not exists", null));

    if (tempUser.getOtp() == null)
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(
              ResponseStatus.get(2001), "No OTP exists for given number", null));

    int isOtpExpired = tempUser.getExpireTime().compareTo(Time.valueOf(LocalTime.now()));
    if (isOtpExpired < 0)
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(
              ResponseStatus.get(2001), "OTP has expired, please generate a new OTP", null));

    if (String.valueOf(otp).equals(tempUser.getOtp()))
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(ResponseStatus.get(2001), "Incorrect OTP entered", null));

    if (customerService.findByMobile(mobile) != null)
      return ResponseEntity.ok(
          responseWrapper.bundleResponse(
              ResponseStatus.get(1001), "", Map.of("token", getLoginToken(mobile, otp))));

    customerService.saveCustomer(mobile);
    return ResponseEntity.ok(
        responseWrapper.bundleResponse(
            ResponseStatus.get(1002), "", Map.of("token", getLoginToken(mobile, otp))));
  }

  public String getLoginToken(String mobile, Integer otp)
      throws BadCredentialsException, Exception {
    log.info(">>>>getLoginToken() called in TempUserController");

    Authentication authentication = null;
    String token = null;

    authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(mobile, String.valueOf(otp)));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    token = jwtTokenUtil.generateToken(authentication);
    return token;
  }
}
