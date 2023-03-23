package com.laundrymanagementsystem.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

	@PostMapping(value = "/feedback/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto addFeedback(@Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.addFeedback(feedbackRequestDto, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/laundry/{laundryId}/feedbacks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getFeedbackListByLaundryId(@PathVariable(required = true) long laundryId) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.getFeedbackListByLaundryId(laundryId, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@GetMapping(value = "/customer/{id}/feedbacks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto getFeedbackListByUserId(@PathVariable(required = true) long id) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.getFeedbackListByUserId(id, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}

	@PostMapping(value = "/customer/{id}/feedback", produces = MediaType.APPLICATION_JSON_VALUE)
	public ApiResponseDto sendFeedBackResponseByUserId(@PathVariable(required = true) long id,
			@RequestParam(required = true) String response) {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		feedbackService.sendFeedBackResponseByUserId(id, response, apiResponseDtoBuilder);
		return apiResponseDtoBuilder.build();
	}
}
