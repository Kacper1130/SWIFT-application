package org.example.swiftcodesapplication.exception;

public class CountryISO2CodeDoesNotExistException extends RuntimeException  {

    public CountryISO2CodeDoesNotExistException(String countryISO2Code) {
        super(String.format("Country with ISO2 code '%s' does not exist", countryISO2Code));
    }
}
