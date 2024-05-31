package io.github.matheusfy.litarelura.mapper.exception;

import java.io.IOException;

public class JsonMappingFailException extends IOException {

	public JsonMappingFailException(String message) {
		super(message);
	}

	public JsonMappingFailException(String message, String json) {
		super(message + "\n" + json);
	}

}
