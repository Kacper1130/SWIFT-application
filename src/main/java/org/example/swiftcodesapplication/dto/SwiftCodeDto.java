package org.example.swiftcodesapplication.dto;

public record SwiftCodeDto(
        String address,
        String bankName,
        String countryISO2,
        boolean isHeadquarter,
        String swiftCode
) {
}
