package com.findar.bookkeeper.constants;

public interface Message {
    String BOOK_REGISTERED_SUCCESSFULLY = "Book Registered Successfully";
    String BOOK_UPDATED_SUCCESSFULLY = "Book Updated Successfully";
    String BOOK_RETRIEVED_SUCCESSFULLY = "Book(s) Retrieved Successfully";
    String USER_REGISTERED_SUCCESSFULLY = "User Registered Successfully";
    String USER_LOGGED_IN_SUCCESSFULLY = "User Logged In Successfully";
    String BOOK_DELETED_SUCCESSFULLY = "Book Deleted Successfully";


    String FIRST_NAME_IS_REQUIRED = "First name is required";
    String LAST_NAME_IS_REQUIRED = "Last name is required";
    String EMAIL_IS_REQUIRED = "Email is required";
    String PASSWORD_IS_REQUIRED = "Password is required";
    String BOOK_ID_IS_REQUIRED = "Book Id Is Required";


    String INVALID_EMAIL = "Invalid email";
    String INVALID_PASSWORD = "Invalid password";
    String UNAUTHORIZED = "You need to be authenticated to access this resource";
    String INVALID_EXPIRED_TOKEN = "Token appears to be Invalid/Expired";
    String BOOK_WITH_ID_NOT_FOUND = "Book With Id `%s` Does Not Exist";
    String USER_WITH_EMAIL_ALREADY_EXIST = "User With email `%s` Already Exist";


    String ROLE_ALLOWABLE_VALUES = "Role AllowableValues: ADMIN | USER";
    String STATUS_ALLOWABLE_VALUES = "Invalid State Provided. AllowableValues: AVAILABLE | NOT_AVAILABLE";
}
