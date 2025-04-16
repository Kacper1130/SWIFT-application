package org.example.swiftcodesapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SwiftCodeRequest(
        @NotBlank(message = "Address is required")
        String address,
        @NotBlank(message = "Bank name is required")
        String bankName,
        @NotBlank(message = "Country ISO2 code is required")
        String countryISO2,
        @NotBlank(message = "Country name is required")
        String countryName,
        @NotNull(message = "Headquarter status is required")
        boolean isHeadquarter,
        @NotBlank(message = "SWIFT code is required")
        String swiftCode
) {
}
