package com.laundrymanagementsystem.app.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.laundrymanagementsystem.app.constants.ResponseMessage;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.exception.FileStorageException;
import com.laundrymanagementsystem.app.exception.MyFileNotFoundException;
import com.laundrymanagementsystem.app.properties.FileStorageProperties;
import com.laundrymanagementsystem.app.service.IFileService;

@Service
public class FileServiceImpl implements IFileService {

	private Path fileStorageLocation;

	@Autowired
	private Environment environment;

	@Autowired
	public FileServiceImpl(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new FileStorageException(
					ResponseMessage.COULD_NOT_CREATE_THE_DIRECTORY_WHERE_THE_UPLOAD_FILES_WILL_BE_STORED, ex);
		}
	}

	@Override
	public String uploadFile(MultipartFile file, String fileName, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		String fileUrl = storeFile(file, fileName);
		apiResponseDtoBuilder.withMessage(ResponseMessage.FILE_UPLOAD_SUCCESSFULLY).withStatus(HttpStatus.OK)
				.withData(fileUrl);
		return fileUrl;
	}

	@Override
	public void uploadMultipleFile(MultipartFile[] files, String type, String fileName,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<String> imageList = new ArrayList<String>();
		int count = 1;
		for (MultipartFile file : files) {
			imageList.add(storeFile(file, new Date().getTime() + "_" + count));
			count++;
		}
		apiResponseDtoBuilder.withMessage(ResponseMessage.FILE_UPLOAD_SUCCESSFULLY).withStatus(HttpStatus.OK)
				.withData(imageList);
	}

	public String storeFile(MultipartFile file, String fileName) {
		// Normalize file name
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		File fileDirectory = new File(environment.getProperty("file.upload-dir"));
		if (!fileDirectory.isDirectory()) {
			fileDirectory.mkdir();
		}
		fileStorageLocation = Paths.get(environment.getProperty("file.upload-dir")).toAbsolutePath().normalize();
		originalFileName = fileName + "." + extension;
		try {
			// Check if the file's name contains invalid characters
			if (originalFileName.contains("..")) {
				throw new FileStorageException(
						ResponseMessage.FILENAME_CONTAINS_INVALID_PATH_SEQUENCE + originalFileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)

			Path targetLocation = this.fileStorageLocation.resolve(originalFileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return File.separator + originalFileName;
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + originalFileName + ". Please try again!", ex);
		}
	}

	public Resource loadFileAsResource(String fileName, String type, String directory) {
		try {
			Path filePath = Paths.get(directory + File.separator + type).toAbsolutePath().normalize().resolve(fileName)
					.normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException(ResponseMessage.FILE_NOT_FOUND + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new MyFileNotFoundException(ResponseMessage.FILE_NOT_FOUND + fileName, ex);
		}
	}

}
