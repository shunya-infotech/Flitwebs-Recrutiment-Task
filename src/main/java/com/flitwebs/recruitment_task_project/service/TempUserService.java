package com.flitwebs.recruitment_task_project.service;

import com.flitwebs.recruitment_task_project.model.TempUserModel;

public interface TempUserService {
  TempUserModel saveTempUser(String mobile, Integer otp);
  TempUserModel findByMobile(String mobile);
}
