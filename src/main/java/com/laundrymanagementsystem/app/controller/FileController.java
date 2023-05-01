package com.laundrymanagementsystem.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.service.IFileService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping("/api/file")
public class FileController {

	@Autowired
	private IFileService fileService;

	@Autowired
	private Environment environment;

	/**
	 * Upload File
	 * 
	 * @param file
	 * @param type
	 * @return
	 */

	@PostMapping(value = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto uploadFile(@RequestParam(required = true) MultipartFile file, @RequestParam String fileName) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		fileService.uploadFile(file, fileName, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/uploadMultipleFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto uploadMultipleFile(@RequestParam(required = true) MultipartFile[] files,
			@RequestParam String type, @RequestParam String fileName) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		fileService.uploadMultipleFile(files, type, fileName, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	/**
	 * Get File
	 * 
	 * @param type
	 * @param file
	 * @return
	 */

	@GetMapping("getFile/{type}/{file}")
	public ResponseEntity<byte[]> getFile(@PathVariable("type") String type, @PathVariable("file") String file) {
		byte[] image = null;
		try {
			image = Files.readAllBytes(Paths.get(environment.getProperty("file.upload-dir") + File.separator + file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	}

	/**
	 * Download File
	 * 
	 * @param type
	 * @param fileName
	 * @param request
	 * @return
	 */

	@GetMapping("/downloadFile/{type}/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String type, @PathVariable String fileName,
			HttpServletRequest request) {
		// Load file as Resource
		Resource resource = null;
		resource = fileService.loadFileAsResource(fileName, type, environment.getProperty("file.upload-dir"));
		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			System.out.println("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
