package org.example.swiftcodesapplication.exception;

import org.example.swiftcodesapplication.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SwiftCodeAlreadyExistsException.class)
    public ResponseEntity<ResponseDto> handleSwiftCodeAlreadyExistsException(SwiftCodeAlreadyExistsException exception) {
        ResponseDto responseDto = new ResponseDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    }

    @ExceptionHandler(SwiftCodeDoesNotExistException.class)
    public ResponseEntity<ResponseDto> handleSwiftCodeDoesNotExistException(SwiftCodeDoesNotExistException exception) {
        ResponseDto responseDto = new ResponseDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(InvalidSwiftCodeException.class)
    public ResponseEntity<ResponseDto> handleInvalidSwiftCodeException(InvalidSwiftCodeException exception) {
        ResponseDto responseDto = new ResponseDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    @ExceptionHandler(CountryISO2CodeDoesNotExistException.class)
    public ResponseEntity<ResponseDto> handleCountryISO2CodeDoesNotExistException(CountryISO2CodeDoesNotExistException exception) {
        ResponseDto responseDto = new ResponseDto(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

}
