package com.flitwebs.recruitment_task_project.repository;

import com.flitwebs.recruitment_task_project.model.TempUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempUserRepository extends JpaRepository<TempUserModel, Integer> {

  TempUserModel findByMobile(String mobile);
}
