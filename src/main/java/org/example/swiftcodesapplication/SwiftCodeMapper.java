package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SwiftCodeMapper {

    public SwiftCodeDto toSwiftCodeDto(SwiftCode swiftCode) {
        return new SwiftCodeDto(
                swiftCode.getAddress(),
                swiftCode.getBankName(),
                swiftCode.getCountryIso2Code(),
                swiftCode.isHeadquarter(),
                swiftCode.getSwiftCode()
        );
    }

    public BranchSwiftCodeDetailsDto toBranchSwiftCodeDetailsDto(SwiftCode swiftCode) {
        return new BranchSwiftCodeDetailsDto(
                swiftCode.getAddress(),
                swiftCode.getBankName(),
                swiftCode.getCountryIso2Code(),
                swiftCode.getCountryName(),
                swiftCode.isHeadquarter(),
                swiftCode.getSwiftCode()
        );
    }

    public HeadquarterSwiftCodeDetailsDto toHeadquarterSwiftCodeDetailsDto(SwiftCode swiftCode, List<SwiftCode> branches) {
        return new HeadquarterSwiftCodeDetailsDto(
                swiftCode.getAddress(),
                swiftCode.getBankName(),
                swiftCode.getCountryIso2Code(),
                swiftCode.getCountryName(),
                swiftCode.isHeadquarter(),
                swiftCode.getSwiftCode(),
                branches.stream().map(this::toSwiftCodeDto).toList()
        );
    }

    public CountrySwiftCodesDetailsDto toCountrySwiftCodeDetailsDto(List<SwiftCode> swiftCodes) {
        return new CountrySwiftCodesDetailsDto(
                swiftCodes.get(0).getCountryIso2Code(),
                swiftCodes.get(0).getCountryName(),
                swiftCodes.stream().map(this::toSwiftCodeDto).toList()
        );
    }

    public SwiftCode toEntity(SwiftCodeRequest swiftCodeRequest) {
        return new SwiftCode(
                swiftCodeRequest.swiftCode(),
                swiftCodeRequest.countryISO2().toUpperCase(),
                null,
                swiftCodeRequest.bankName(),
                swiftCodeRequest.address(),
                null,
                swiftCodeRequest.countryName().toUpperCase(),
                null,
                swiftCodeRequest.isHeadquarter()
                );
    }
}
