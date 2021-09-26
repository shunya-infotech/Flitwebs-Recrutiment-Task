package com.flitwebs.recruitment_task_project.repository;

import com.flitwebs.recruitment_task_project.model.TradesmanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradesmanRepository extends JpaRepository<TradesmanModel, Integer> {}
