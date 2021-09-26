package com.flitwebs.recruitment_task_project.repository;

import com.flitwebs.recruitment_task_project.model.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {

  CustomerModel findByMobile(String mobile);
}
