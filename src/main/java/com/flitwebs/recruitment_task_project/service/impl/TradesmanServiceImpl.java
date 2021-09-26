package com.flitwebs.recruitment_task_project.service.impl;

import com.flitwebs.recruitment_task_project.model.TradesmanModel;
import com.flitwebs.recruitment_task_project.repository.TradesmanRepository;
import com.flitwebs.recruitment_task_project.service.TradesmanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class TradesmanServiceImpl implements TradesmanService {

  private final TradesmanRepository tradesmanRepository;

  @Override
  public TradesmanModel findById(Integer id) {
    log.info(">>>findById() called inside TradesmanServiceImpl");

    return tradesmanRepository.findById(id).orElse(null);
  }
}
