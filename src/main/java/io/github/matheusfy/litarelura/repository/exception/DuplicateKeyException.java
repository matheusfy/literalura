package io.github.matheusfy.litarelura.repository.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateKeyException extends DataIntegrityViolationException {

	public DuplicateKeyException(String message) {
		super(message);
	}

}
