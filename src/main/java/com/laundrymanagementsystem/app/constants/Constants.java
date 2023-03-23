package com.laundrymanagementsystem.app.constants;

public class Constants {

	// Api base url
	public static final String API_BASE_URL = "/api";

	// Tables
	public static final String USER_TABLE_NAME = "user_details";
	public static final String INVENTORY_TABLE_NAME = "inventory";
	public static final String LAUNDRY_TABLE_NAME = "laundry_details";
	public static final String BOOKING_TABLE_NAME = "booking_details";
	public static final String PROMO_CODE_TABLE_NAME = "promo_code";
	public static final String VERIFICATION_TOKEN_TABLE_NAME = "verification_token_details";
	public static final String RATING_TABLE_NAME = "rating_details";
	public static final String TIP_TABLE_NAME = "tip_details";
	public static final String FEEDBACK_TABLE_NAME = "feedback_details";
	public static final String LICENSE_TABLE_NAME = "license_details";
	public static final String ORDER_TABLE_NAME="order_details";
	public static final String SERVICES_TABLE_NAME="services";

	// Token
	public static final String TOKEN_INVALID = "invalidToken";
	public static final String TOKEN_EXPIRED = "expired";
	public static final String TOKEN_VALID = "valid";

	// Application property
	public static final String SERVER_DOMAIN_URL = "server.domain.url";
	public static final String FILE_UPLOAD_DIR = "file.upload-dir";
	public static final String SUPPORT_EMAIL = "support.email";

	// User
	public static final String EMAIL_EXISTS = "Email Exists";
	public static final String NO_EMAIL_EXISTS = "No Email Exists";
	public static final String NO_Mobile_EXISTS = "Mobile number not registered";
	public static final String PASSWORD_CHANGED = "Password Changed";
	public static final String EMAIL_ALREADY_EXISTS = "Email Already Exists";
	public static final String USER_ADDED_SUCCESSFULLY = "User Added Successfully";
	public static final String USER_MOBILE_NUMBER_ALREADY_EXISTS = "User mobile number Already Exist";
	public static final String NO_USER_EXISTS = "No User Exists";
	public static final String USER_DELETED_SUCCESSFULLY = "User Deleted Successfully";
	public static final String USER_UPDATED_SUCCESSFULLY = "User Updated Successfully";
	public static final String STATUS_CHANGED = "Status Changed";
	public static final String USER_IS_ACTIVE = "User Is Active";
	public static final String USER_IS_INACTIVE = "User Is InActive";
	public static final String PERMISSION_CHANGED = "Can Prepare FS Permission Changed";
	public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password.";
	public static final String OLD_PASSWORD_ID_WRONG = "Old password is wrong";
	public static final String SEND_DETAILS_TO_YOUR_EMAIL = "We have sent details to your email.";
	public static final String CONFIRMATION_EMAIL_SENT = "Confimation email has been sent";
	public static final String REGISTRATION_CONFIRMATION = "Sponsorship Registration Confirmation";
	public static final String REGISTRATION_SUCCESS = "Your account registered successfully. Please click on below link to confirm your account";
	public static final String YOU_ARE_SUCCESSFULLY_REGISTERED_AS_SUPER_ADMIN = "You are successfully registered as super admin";
	public static final String SUCCESSFULLY_REGISTERED_AS_ADMIN = "Successfully registered as admin";
	public static final String YOU_ARE_SUCCESSFULLY_REGISTERED = "You are successfully registered";
	public static final String USER_ACTIVED = "User activated";
	public static final String USER_INACTIVATED = "User inactivated";

	// File Upload
	public static final String FILE_UPLOAD_SUCCESSFULLY = "File Upload Successfully";
	public static final String FILENAME_CONTAINS_INVALID_PATH_SEQUENCE = "Sorry! Filename contains invalid path sequence ";
	public static final String FILE_NOT_FOUND = "File not found ";
	public static final String COULD_NOT_CREATE_THE_DIRECTORY_WHERE_THE_UPLOAD_FILES_WILL_BE_STORED = "Could not create the directory where the uploaded files will be stored.";
	public static final String FILE_DELETED_SUCCESSFULLY = "File deleted successfully";

	public static final String TEMPORARY_PASSWORD = "Temporary Password";
	public static final String ALREADY_REGISTERED = "Already registered";


	// Laundry 
	public static final String NO_LAUNDRY_EXISTS = "No Laundry Exists";
	public static final String LAUNDRY_NOT_FOUND = "Laundry not found";
	public static final String LAUNDRY_ACTIVE_SUCCESS = "Laundry activated successfully";

	public static final String LAUNDRY_DEACTIVE_SUCCESS = "Laundry deactivated successfully";
	public static final String LAUNDRY_APPROVE_SUCCESS = "Laundry approved";

	public static final String LAUNDRY_REJECT_SUCCESS = "Laundry rejected";



	// Notification
	public static final String NOTIFICATION_ADDED_SUCCESSFULLY = "Booking Added Successfully";
	public static final String BOOKING_SCHEDULE = "Booking scheduled Successfully";
	public static final String NO_NOTIFICATION_EXISTS = "No Booking Exists";
	public static final String NOTIFICATION_UPDATED_SUCCESSFULLY = "Booking Status_Updated Successfully";

	// Images
	public static final String IMAGES_ADDED_SUCCESSFULLY = "Images Added Successfully";
	public static final String NO_IMAGES_EXISTS = "No images Exists";
	public static final String IMAGES_ALLREADY_EXISTS = "Images allready Exists";
	public static final String IMAGES_DELETED_SUCCESSFULLY = "Image Deleted Successfully";
	public static final String IMAGES_UPDATED_SUCCESSFULLY = "Image Updated Successfully";

	public static final String SOMETHING_WENT_WRONG = "something went wrong";
	public static final String DATA_LIST = "Data List";
	public static final String MOBILE_NUMBER_ALREADY_EXISTS = "Mobile number already exists";
	public static final String USER_ADD_SUCCESS = "Your are registered successfully";


	// Promo Code
	public static final String PROMO_CODE_ALREADY_EXIST = "Promo code already exist";
	public static final String PROMO_CODE_ADD_SUCCESS = "Promo code added successfully";
	public static final String PROMO_CODE_NOT_FOUND = "Promo code not found";
	public static final String PROMO_CODE_INFO = "Promo code info";
	public static final String PROMO_CODE_LIST = "Promo code list";

	public static final String INVALID_USERNAME = "Invalid username";
	public static final String UNAUTHORIZED = "You are not authorized";
	public static final String USER_ACTIVE_SUCCESS = "User activated successfully";
	public static final String USER_DEACTIVE_SUCCESS = "User deactivated successfully";

//	public static final String DRIVER_ACTIVE_SUCCESS = " Driver status is online";
//	public static final String DRIVER_DEACTIVE_SUCCESS = " Driver status is offline";
	public static final String USER_NOT_FOUND = "User not found";


//

	public static final String RATING_ADD_SUCCESS = "Rating added";

//	public static final String DRIVER_NOT_FOUND = "Driver not found";

	public static final String REWARD_SENT_SUCCESSFULLY = "Reward sent successfully";

	// Feedback
	public static final String FEEDBACK_SENT_SUCCESSFULLY = "Feedback response sent successfully";
	public static final String FEEDBACK_NOT_FOUND = "Feedback not found";
	
	//Inventory
	public static final String NO_ITEM_EXISTS = "No Item Exists";
	
	//Service
	public static final String NO_SERVICE_EXISTS = "No Service Exists";

	//Order
	public static final String CONTACT_NUMBER_ALREADY_EXISTS ="Contact Number Already Exists";
	public static final String NO_ORDER_EXISTS = "No Order Exists";
}
