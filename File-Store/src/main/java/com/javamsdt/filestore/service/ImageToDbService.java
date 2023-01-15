package com.javamsdt.filestore.service;

import com.javamsdt.filestore.dto.ImageDto;
import com.javamsdt.filestore.mapper.ImageMapper;
import com.javamsdt.filestore.model.Image;
import com.javamsdt.filestore.repository.ImageRepository;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ImageToDbService {
  private final ImageRepository imageRepository;

  private final ImageMapper imageMapper;

  public Long saveImageReturnId(MultipartFile multipartImage) throws Exception {
    Image dbImage = new Image();
    dbImage.setName(extractImageName(multipartImage.getOriginalFilename()));
    dbImage.setContent(multipartImage.getBytes());

    return imageRepository.save(dbImage)
      .getId();
  }

  public List<Long> saveImages(List<MultipartFile> multipartFiles) {
    return imageRepository.saveAll(toImages(multipartFiles))
      .stream()
      .map(Image::getId)
      .collect(Collectors.toList());
  }

  public Long saveImageReturnIdFromImage(Image image) {
    return imageRepository.save(image)
      .getId();
  }

  public Image getImageById(Long imageId) {
    return imageRepository.findById(imageId)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public List<Image> findByImageIds(List<Long> ids) {
    return imageRepository.findByImageIds(ids);
  }

  public Image findImageByName(String name) {
    return imageRepository.findByName(name)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public ImageDto downloadImageById(Long imageId) {
    return imageMapper.toImageDto(getImageById(imageId));
  }

  private String extractImageName(String imagePath) {
    int lastDot = imagePath.lastIndexOf(".");
    return imagePath.substring(0, lastDot);
  }

  private List<Image> toImages(List<MultipartFile> multipartFiles) {
    return multipartFiles.stream()
      .map(fileImage -> {
        Image image = new Image();
        image.setName(extractImageName(fileImage.getOriginalFilename()));
        try {
          image.setContent(fileImage.getBytes());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        return image;
      })
      .collect(Collectors.toList());
  }

}
