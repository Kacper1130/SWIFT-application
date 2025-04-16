package org.example.swiftcodesapplication.dto;

import java.util.List;

public record CountrySwiftCodesDetailsDto(
        String countryISO2,
        String countryName,
        List<SwiftCodeDto> swiftCodes
) {
}
