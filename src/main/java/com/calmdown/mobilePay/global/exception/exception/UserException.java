package com.calmdown.mobilePay.global.exception.exception;

import com.calmdown.mobilePay.global.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException{
	
	private final ErrorCode errorCode;

}
