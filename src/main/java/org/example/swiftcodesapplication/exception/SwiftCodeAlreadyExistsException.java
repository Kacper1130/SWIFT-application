package org.example.swiftcodesapplication.exception;

public class SwiftCodeAlreadyExistsException extends RuntimeException {

    public SwiftCodeAlreadyExistsException(String swiftCode) {
        super(String.format("SWIFT code '%s' already exists", swiftCode));
    }
}
