package com.flitwebs.recruitment_task_project.service.impl;

import com.flitwebs.recruitment_task_project.enums.Status;
import com.flitwebs.recruitment_task_project.exception.FileNotAnImageException;
import com.flitwebs.recruitment_task_project.exception.FileSizeExceedException;
import com.flitwebs.recruitment_task_project.model.ImageUploadModel;
import com.flitwebs.recruitment_task_project.repository.ImageUploadRepository;
import com.flitwebs.recruitment_task_project.service.CustomerService;
import com.flitwebs.recruitment_task_project.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

  private final CustomerService customerService;

  private final ImageUploadRepository imageUploadRepository;

  @Override
  public ImageUploadModel uploadImage(String mobile, MultipartFile image) {
    log.info(">>>uploadImage() called inside ImageUploadServiceImpl");

    return Optional.ofNullable(customerService.findByMobile(mobile))
        .map(
            customer -> {
              String imagePath = saveImage(image, String.valueOf(customer.getId()));
              return imageUploadRepository.save(
                  ImageUploadModel.builder()
                      .imagePath(imagePath)
                      .createdDate(Date.valueOf(LocalDate.now()))
                      .customer(customer)
                      .status(Status.ACTIVE.toString())
                      .build());
            })
        .orElseThrow(
            () ->
                new EntityNotFoundException("Customer not found for the mobile number: " + mobile));
  }

  private String saveImage(MultipartFile image, String folderName) {
    log.info(">>>saveImage() called inside ImageUploadServiceImpl");

    if (!(image.getContentType().split("/")[0]).equals("image"))
      throw new FileNotAnImageException("The file is not an image");

    if (image.getSize() > (2 * 2048 * 2048))
      throw new FileSizeExceedException("File must be less then 2 MB");

    try {

      File imageDir = new File("images/" + folderName);
      if (!imageDir.exists()) imageDir.mkdirs();
      String filename =
          imageDir.getAbsolutePath()
              + "/profile-image"
              + image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));

      try (InputStream is = image.getInputStream()) {
        Files.copy(is, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
      }

      return filename;
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
