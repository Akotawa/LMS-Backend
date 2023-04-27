package com.laundrymanagementsystem.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.FeedbackRequestDto;
import com.laundrymanagementsystem.app.service.IFeedbackService;

@CrossOrigin(origins = "*", maxAge = 36000000)
@RestController
@RequestMapping(Constants.API_BASE_URL)
public class FeedbackController {

	@Autowired
	private IFeedbackService feedbackService;

	@PostMapping(value = "feedback/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addFeedback(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.addFeedback(feedbackRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "feedbacks/laundry/{laundryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getFeedbackListByLaundryId(@PathVariable(required = true) long laundryId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.getFeedbackListByLaundryId(laundryId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "feedbacks/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getFeedbackListByCustomerId(@PathVariable(required = true) long customerId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.getFeedbackListByCustomerId(customerId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "sendFeedBackResponse/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto sendFeedBackResponseByFeedbackId(@PathVariable(required = true) long id,
			@RequestParam(required = true) String response) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.sendFeedBackResponseByFeedbackId(id, response, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
