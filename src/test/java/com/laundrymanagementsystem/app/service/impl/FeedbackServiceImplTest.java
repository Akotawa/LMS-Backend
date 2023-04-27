package com.laundrymanagementsystem.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.dto.FeedbackRequestDto;
import com.laundrymanagementsystem.app.mapper.CustomMapper;
import com.laundrymanagementsystem.app.model.Feedback;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.repository.FeedbackRepository;
import com.laundrymanagementsystem.app.repository.PromoCodeRepository;
import com.laundrymanagementsystem.app.repository.UserRepository;
import com.laundrymanagementsystem.app.utility.Utility;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceImplTest {
	@InjectMocks
	FeedbackServiceImpl feedbackServiceImpl;
	@Mock
	UserRepository userRepository;
	@Mock
	FeedbackRepository feedbackRepository;
	@Mock
	PromoCodeRepository promoCodeRepository;
	@Mock
	CustomMapper customMapper;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		User user = new User();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Test
	public void addFeedback() {
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		FeedbackRequestDto feedbackRequestDto = new FeedbackRequestDto();
		feedbackRequestDto.setCustomerId(1L);
		feedbackRequestDto.setLaundryId(1L);
		feedbackRequestDto.setFeedback("test");
		feedbackRequestDto.setOrderId(1L);
		Feedback feedback = new Feedback();
		User sessionUser = new User();
		sessionUser.setActive(true);
		sessionUser.setEmail("test");
		sessionUser.setFullName("test");
		sessionUser.setId(1l);
		sessionUser.setRole(3);
		when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
		when(customMapper.feedbackRequestDtoToFeedback(feedbackRequestDto)).thenReturn(feedback);
		feedbackServiceImpl.addFeedback(feedbackRequestDto, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.FEEDBACK_ADD_SUCCESS));

	}

	@Test
	public void getFeedbackListByLaundryId() {
		long laundryId = 1L;
		List<Feedback> feedbacks = new ArrayList<Feedback>();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		when(feedbackRepository.findByLaundryId(laundryId)).thenReturn(feedbacks);
		feedbackServiceImpl.getFeedbackListByLaundryId(laundryId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}

	@Test
	public void getFeedbackListByCustomerId() {
		long customerId = 1L;
		List<Feedback> feedbacks = new ArrayList<>();
		ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
		when(feedbackRepository.findByCustomerId(customerId)).thenReturn(feedbacks);
		feedbackServiceImpl.getFeedbackListByCustomerId(customerId, apiResponseDtoBuilder);
		assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.SUCCESSFULLY));

	}
@Test
	public void sendFeedBackResponseByFeedbackId() {
	long feedbackId = 1L;
	String response="test";
	List<Feedback> feedbacks = null;
	ApiResponseDtoBuilder apiResponseDtoBuilder = new ApiResponseDtoBuilder();
	User sessionUser = new User();
	sessionUser.setActive(true);
	sessionUser.setEmail("test");
	sessionUser.setFullName("test");
	sessionUser.setId(1l);
	sessionUser.setRole(1);
	when(Utility.getSessionUser(userRepository)).thenReturn(sessionUser);
	Feedback feedback=new Feedback();
	feedback.setId(1l);
	Optional<Feedback> feed =Optional.ofNullable(feedback);
	when(feedbackRepository.findById(feedbackId)).thenReturn(feed);
	feedbackServiceImpl.sendFeedBackResponseByFeedbackId(feedbackId,response, apiResponseDtoBuilder);
	assertTrue(apiResponseDtoBuilder.getMessage().equals(Constants.FEEDBACK_SENT_SUCCESSFULLY));

	}



}
