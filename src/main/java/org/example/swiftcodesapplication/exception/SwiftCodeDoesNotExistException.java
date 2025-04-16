package org.example.swiftcodesapplication.exception;

public class SwiftCodeDoesNotExistException extends RuntimeException {

    public SwiftCodeDoesNotExistException(String swiftCode) {
        super(String.format("SWIFT code '%s' does not exist", swiftCode));
    }
}
