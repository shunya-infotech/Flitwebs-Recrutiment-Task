package com.flitwebs.recruitment_task_project.service.impl;

import com.flitwebs.recruitment_task_project.service.TwilioService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TwilioServiceImpl implements TwilioService {

  @Value("${TWILIO_SID}")
  private String twilio_service_id;

  @Value("${TWILIO_AUTH_TOKEN}")
  private String twilio_authorization_token;

  @Value("${TWILIO_NUMBER}")
  private String twilio_trial_number;

  /*
   * method to send OTP using twilio api.
   */
  @Override
  public void sendMessage(String mobile, String message) {
    log.info(">>>sendMessage() called inside TwilioServiceImpl");

    Twilio.init(twilio_service_id, twilio_authorization_token);
    Message.creator(new PhoneNumber(mobile), new PhoneNumber(twilio_trial_number), message)
        .create();
  }
}
