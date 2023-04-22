package com.laundrymanagementsystem.app.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.laundrymanagementsystem.app.constants.Constants;
import com.laundrymanagementsystem.app.dto.ApiResponseDto.ApiResponseDtoBuilder;
import com.laundrymanagementsystem.app.model.Customer;
import com.laundrymanagementsystem.app.model.Employee;
import com.laundrymanagementsystem.app.model.Order;
import com.laundrymanagementsystem.app.model.User;
import com.laundrymanagementsystem.app.model.VerificationToken;
import com.laundrymanagementsystem.app.repository.VerificationTokenRepository;
import com.laundrymanagementsystem.app.service.IEmailService;
import com.laundrymanagementsystem.app.service.IUserService;
import com.laundrymanagementsystem.app.service.IVerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements IVerificationTokenService {

	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private IUserService userService;

	@Autowired
	private IEmailService emailService;

	@Override
	public String validateToken(String token) {
		final String result = validateVerificationToken(token);
		if (result.equals(TOKEN_VALID)) {

			return "Thank you for verify your email!!";
		} else {

			return "Try again";
		}
	}

	public String validateVerificationToken(String token) {
		final VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if (verificationToken == null) {
			return TOKEN_INVALID;
		}

		final User user = userService.findById(verificationToken.getUser());
		final Calendar cal = Calendar.getInstance();
		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			verificationTokenRepository.delete(verificationToken);
			return TOKEN_EXPIRED;
		}

		if (user.getRole() == 2) {
			Customer customer = userService.findCustomerById(user.getId());
			customer.setIsVerified(true);
			userService.saveCustomer(customer);
		} else if (user.getRole() == 3) {
			Employee customer = userService.findEmployeeById(user.getId());
			customer.setIsVerified(true);
			userService.saveEmployee(customer);
		}
		return TOKEN_VALID;
	}

	@Override
	public void resendRegistrationToken(Long id, ApiResponseDtoBuilder apiResponseDtoBuilder) {
		User user = userService.findById(id);
		if (user == null) {
			apiResponseDtoBuilder.withMessage(Constants.USER_NOT_FOUND).withStatus(HttpStatus.NOT_FOUND);
			return;
		}
		sendVerificationToken(user, null);
		apiResponseDtoBuilder.withMessage("Confirmation email has been sent").withStatus(HttpStatus.OK);
	}

	@Override
	public void sendVerificationToken(User user, String password) {
		new Thread(() -> {
			String subject = "laundry managementsystem Account verification";
			String body = createEmailBody(user.getFullName(), registrationConfirmUrl(user.getId()), password);
			emailService.sendEmail(user.getEmail(), subject, body, "", null, null);
		}).start();
	}

	public String createEmailBody(String name, String url, String password) {
		final String body = "<html><body><h3>Hello " + name.toUpperCase() + "</h3>"
				+ "<br>You registered an account on <br>" + name + "<br>" + password
				+ "</b>, before being able to use your account you need to verify that this is your email verification by clicking here</p>"
				+ "<a href=\"" + url + "\">Clicking Here </a>"
				+ "<br><br><p>Kind Regards,<br>Team Laundry Management System<br>Thank You !</body></html>";
		return body;
	}

	public String registrationConfirmUrl(Long userId) {
		VerificationToken token = createVerificationToken(userId);
		String url = environment.getProperty(Constants.SERVER_DOMAIN_URL) + Constants.API_BASE_URL
				+ "/registrationConfirm?token=" + token.getToken();
		return url;
	}

	private VerificationToken createVerificationToken(Long userId) {
		VerificationToken vToken = verificationTokenRepository.findByUser(userId);
		if (vToken == null) {
			vToken = new VerificationToken();
			vToken.setUser(userId);
			vToken.setCreatedAt(new Date());
		}
		vToken.updateToken(UUID.randomUUID().toString().toUpperCase());
		verificationTokenRepository.save(vToken);
		return vToken;
	}


	@Override
	public void sendBookingToken(Order order) {
		new Thread(() -> {
			String subject = "Order booking  Account verification";
			String body = createBookingEmailBody(order.getCustomerName(), registrationConfirmUrl(order.getId()));
			emailService.sendEmail(order.getEmail(), subject, body, null, null, null);
		}).start();

	}

	private String createBookingEmailBody(String customerName, String registrationConfirmUrl) {
		final String body = "<html><body><h3>Hello " + customerName.toUpperCase() + "</h3>"
				+ "<br>Welcome to Luandry Management System Your Order booking <br>" 
				+ "<br>Team Laundry Management System<br>Thank You !2</body></html>";
		return body;
	}

	@Override
	public void sendWelcomeToken(String password, String email) {
		new Thread(() -> {
			String subject = "Welcome Token";
			String body = createWelcomeEmailBody(email, password);
			emailService.sendEmail(email, subject, body, null, null, null);
		}).start();
	}

	private String createWelcomeEmailBody(String email, String password) {

		final String body = "<html><body><h3>Hello " + email.toUpperCase() + "</h3>"
				+ "<br>Welcome to Luandry Management System You registered an account on <br>" + email +"<br>Password<br>"+ password
				+ "<br>Team Laundry Management System<br>Thank You !2</body></html>";
		return body;

	}

	@Override
	public void sendPassword(String email, String password) {
		new Thread(() -> {
			String subject = "Password Token";
			String body = createNewPasswordEmailBody(email, password);
			emailService.sendEmail(email, subject, body, null, null, null);
		}).start();
	}

	private String createNewPasswordEmailBody(String email, String password) {
		final String body = "<html><body><h3>Hello " + email.toUpperCase() + "</h3>" + "<br>Your New Password is<br>"
				+ password + "<br>Team Laundry Management System<br>Thank You !</body></html>";
		return body;
	}

	@Override
	public void sendBill(Order order) {
		new Thread(() -> {
			String subject = "Bill Token";
			String body = createBillEmailBody(order);
			emailService.sendEmail(order.getEmail(), subject, body, null, null, null);
		}).start();
	}

	private String createBillEmailBody(Order order) {
		final String body = "<html><body><h3>Hello " + order.getEmail().toUpperCase() + "</h3>"
				+ "<br>Your Order Id <br>" + order.getId() + "<br>Service Type <br>" + order.getServiceId()
				+ "<br>Invoice Amount  <br>" + order.getInvoicedAmount()
				+ "<br>Team Laundry Management System<br>Thank You !</body></html>";
		return body;
	}
}