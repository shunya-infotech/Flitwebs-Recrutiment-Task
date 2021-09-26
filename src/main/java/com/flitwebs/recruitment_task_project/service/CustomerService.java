package com.flitwebs.recruitment_task_project.service;

import com.flitwebs.recruitment_task_project.model.CustomerModel;

public interface CustomerService {

  CustomerModel saveCustomer(String mobile);

  CustomerModel findById(Integer id);

  CustomerModel findByMobile(String mobile);
}
