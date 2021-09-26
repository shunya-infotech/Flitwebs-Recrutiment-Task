package com.flitwebs.recruitment_task_project.repository;

import com.flitwebs.recruitment_task_project.model.BookTradesmanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTradesmanRepository extends JpaRepository<BookTradesmanModel, Integer> {}
