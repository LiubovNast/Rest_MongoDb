package ua.test.task.rest_mongodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AppException {

    private static final String NOT_FOUND = " not found";

    public AppException() {
    }

    public static ResponseStatusException currencyNotFound(String name) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency " + name + NOT_FOUND);
    }

    public static ResponseStatusException errorIO(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }
}
