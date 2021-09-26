package com.flitwebs.recruitment_task_project.service.impl;

import com.flitwebs.recruitment_task_project.model.TempUserModel;
import com.flitwebs.recruitment_task_project.repository.TempUserRepository;
import com.flitwebs.recruitment_task_project.service.TempUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TempUserServiceImpl implements TempUserService, UserDetailsService {

  private final BCryptPasswordEncoder bcryptEncoder;

  private final TempUserRepository tempUserRepository;

  @Override
  public TempUserModel saveTempUser(String mobile, Integer otp) {
    log.info(">>>saveTempUser() called inside TempUserServiceImpl");

    return Optional.ofNullable(tempUserRepository.findByMobile(mobile))
        .map(
            t -> {
              t.setOtp(bcryptEncoder.encode(otp.toString()));
              t.setExpireTime(Time.valueOf(LocalTime.now().plusHours(1)));
              t.setCreatedTime(Time.valueOf(LocalTime.now()));
              return tempUserRepository.save(t);
            })
        .orElseGet(
            () ->
                tempUserRepository.save(
                    TempUserModel.builder()
                        .mobile(mobile)
                        .otp(bcryptEncoder.encode(otp.toString()))
                        .createdTime(Time.valueOf(LocalTime.now()))
                        .expireTime(Time.valueOf(LocalTime.now().plusHours(1)))
                        .build()));
  }

  @Override
  public TempUserModel findByMobile(String mobile) {
    log.info(">>>findByMobile() called inside TempUserServiceImpl");

    return tempUserRepository.findByMobile(mobile);
  }

  @Override
  public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {

    TempUserModel tempUser = tempUserRepository.findByMobile(mobile);

    if (tempUser == null) throw new UsernameNotFoundException("Invalid Mobile Number.");

    return new org.springframework.security.core.userdetails.User(
        tempUser.getMobile(), tempUser.getOtp().toString(), getAuthority());
  }

  private List<SimpleGrantedAuthority> getAuthority() {
    return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
  }
}
