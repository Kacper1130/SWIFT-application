package org.example.swiftcodesapplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeadquarterSwiftCodeDetailsDto extends SwiftCodeDetailsDto {
    private List<SwiftCodeDto> branches;

    public HeadquarterSwiftCodeDetailsDto(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode, List<SwiftCodeDto> branches) {
        super(address, bankName, countryISO2, countryName, isHeadquarter, swiftCode);
        this.branches = branches;
    }
}
