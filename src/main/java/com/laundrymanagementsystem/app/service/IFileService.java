package com.laundrymanagementsystem.app.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;

@Service
public interface IFileService {

	String uploadFile(MultipartFile file, String fileName, ApiResponseDtoBuilder apiResponseDtoBuilder);

	Resource loadFileAsResource(String fileName, String type, String property);

	void uploadMultipleFile(MultipartFile[] files, String type, String fileName,
			ApiResponseDtoBuilder apiResponseDtoBuilder);

}
