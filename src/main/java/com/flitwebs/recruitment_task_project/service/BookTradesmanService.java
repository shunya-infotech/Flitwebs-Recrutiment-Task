package com.flitwebs.recruitment_task_project.service;

import com.flitwebs.recruitment_task_project.model.BookTradesmanModel;
import com.flitwebs.recruitment_task_project.model.request.BookTradesmanRequest;

public interface BookTradesmanService {

  BookTradesmanModel bookTradesman(BookTradesmanRequest bookTradesmanRequest);

  BookTradesmanModel bookTradesman(BookTradesmanModel bookTradesman);
}
