package com.laundrymanagementsystem.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.FeedbackRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Feedback;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.FeedbackRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.service.IFeedbackService;
import com.laundrymanagementsystem.app.utility.Utility;

@Service
public class FeedbackServiceImpl implements IFeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private CustomMapper customMapper;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void addFeedback(@Valid FeedbackRequestDto feedbackRequestDto, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		if (Utility.getSessionUser(userRepository).getRole() == 3) {
			if (!feedbackRepository.existsByCustomerId(Utility.getSessionUser(userRepository).getId())) {
				Feedback feedback = customMapper.feedbackRequestDtoToFeedback(feedbackRequestDto);
				feedback.setCreatedAt(new Date());
				feedbackRepository.save(feedback);
				apiResponseDtoBuilder.withMessage(Constants.FEEDBACK_ADD_SUCCESS).withStatus(HttpStatus.OK)
						.withData(feedback);
			} else {
				apiResponseDtoBuilder.withMessage("feedback already exist").withStatus(HttpStatus.ALREADY_REPORTED);
			}
		} else {
			apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);

		}
	}

	@Override
	public void getFeedbackListByLaundryId(long laundryId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Feedback> feedbacks = feedbackRepository.findByLaundryId(laundryId);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(feedbacks);
	}

	@Override
	public void getFeedbackListByCustomerId(long customerId, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		List<Feedback> feedbacks = feedbackRepository.findByCustomerId(customerId);
		apiResponseDtoBuilder.withMessage(Constants.SUCCESSFULLY).withStatus(HttpStatus.OK).withData(feedbacks);
	}

	@Override
	public void sendFeedBackResponseByFeedbackId(long feedbackId, String response,
			ApiResponseDtoBuilder apiResponseDtoBuilder) {
		Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
		User sessionUser = Utility.getSessionUser(userRepository);
		if (feedback.isPresent()) {
			if (sessionUser.getRole() == 0 || sessionUser.getRole() == 1) {
				Feedback feedBackResponse = new Feedback();
				feedBackResponse.setLaundryId(feedback.get().getLaundryId());
				feedBackResponse.setFeedback(response);
				feedBackResponse.setCustomerId(feedback.get().getCustomerId());
				feedBackResponse.setCreatedAt(new Date());
				feedbackRepository.save(feedBackResponse);
				apiResponseDtoBuilder.withMessage(Constants.FEEDBACK_SENT_SUCCESSFULLY).withStatus(HttpStatus.OK);
				return;
			} else {
				apiResponseDtoBuilder.withMessage(Constants.UNAUTHORIZED).withStatus(HttpStatus.UNAUTHORIZED);
			}

		} else {
			apiResponseDtoBuilder.withMessage(Constants.FEEDBACK_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
		}

	}
}
