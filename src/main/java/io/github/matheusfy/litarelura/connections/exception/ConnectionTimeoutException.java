package io.github.matheusfy.litarelura.connections.exception;

import java.net.http.HttpTimeoutException;

public class ConnectionTimeoutException extends HttpTimeoutException {

	public ConnectionTimeoutException(String message) {
		super(message);
	}
}
