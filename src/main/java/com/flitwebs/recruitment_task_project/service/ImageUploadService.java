package com.flitwebs.recruitment_task_project.service;

import com.flitwebs.recruitment_task_project.model.ImageUploadModel;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

  ImageUploadModel uploadImage(String mobile, MultipartFile image);
}
