package org.example.swiftcodesapplication.dto;

public record CsvSwiftCode(
        String countryIso2Code,
        String swiftCode,
        String codeType,
        String bankName,
        String address,
        String townName,
        String countryName,
        String timeZone
) {
}
