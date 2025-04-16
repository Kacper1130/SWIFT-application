package org.example.swiftcodesapplication.dto;

public class BranchSwiftCodeDetailsDto extends SwiftCodeDetailsDto {
    public BranchSwiftCodeDetailsDto(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        super(address, bankName, countryISO2, countryName, isHeadquarter, swiftCode);
    }
}
