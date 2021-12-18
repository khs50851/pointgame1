package com.ks.pointgame.handler.ex;

import java.util.Map;

public class CustomNumberFormatException extends NumberFormatException{
	
	// 객체를 구분할때
	private static final long serialVersionUID = 1L;
	
	public CustomNumberFormatException(String message) {
		super(message);
	}
	
	
}
