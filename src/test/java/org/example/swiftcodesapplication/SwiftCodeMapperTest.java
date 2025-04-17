package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwiftCodeMapperTest {

    private final SwiftCodeMapper swiftCodeMapper = new SwiftCodeMapper();

    @Test
    void toSwiftCodeDto_ShouldMapSwiftCodeToDto() {
        SwiftCode swiftCode = new SwiftCode(
                "PLBPKOPLPWXXX",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw",
                true
        );

        SwiftCodeDto result = swiftCodeMapper.toSwiftCodeDto(swiftCode);

        assertNotNull(result);
        assertEquals(swiftCode.getAddress(), result.address());
        assertEquals(swiftCode.getBankName(), result.bankName());
        assertEquals(swiftCode.getCountryIso2Code(), result.countryISO2());
        assertEquals(swiftCode.isHeadquarter(), result.isHeadquarter());
        assertEquals(swiftCode.getSwiftCode(), result.swiftCode());
    }

    @Test
    void toBranchSwiftCodeDetailsDto_ShouldMapSwiftCodeToBranchDto() {
        SwiftCode swiftCode = new SwiftCode(
                "PLBPKOPLPWXXX",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw",
                false
        );

        BranchSwiftCodeDetailsDto result = swiftCodeMapper.toBranchSwiftCodeDetailsDto(swiftCode);

        assertNotNull(result);
        assertEquals(swiftCode.getAddress(), result.getAddress());
        assertEquals(swiftCode.getBankName(), result.getBankName());
        assertEquals(swiftCode.getCountryIso2Code(), result.getCountryISO2());
        assertEquals(swiftCode.getCountryName(), result.getCountryName());
        assertEquals(swiftCode.isHeadquarter(), result.isHeadquarter());
        assertEquals(swiftCode.getSwiftCode(), result.getSwiftCode());
    }

    @Test
    void toHeadquarterSwiftCodeDetailsDto_ShouldMapSwiftCodeToHeadquarterDto() {
        SwiftCode swiftCode = new SwiftCode(
                "PLBPKOPLPWXXX",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw",
                true
        );
        List<SwiftCode> branches = List.of(
                new SwiftCode(
                        "PLBPKOPLPWAAA",
                        "PL",
                        "BIC11",
                        "PKO BANK POLSKI S.A.",
                        "SIENKIEWICZA 12/14 WARSZAWA, MAZOWIECKIE, 00-944",
                        "WARSZAWA",
                        "POLAND",
                        "Europe/Warsaw",
                        false
                )
        );

        HeadquarterSwiftCodeDetailsDto result = swiftCodeMapper.toHeadquarterSwiftCodeDetailsDto(swiftCode, branches);

        assertNotNull(result);
        assertEquals(swiftCode.getAddress(), result.getAddress());
        assertEquals(swiftCode.getBankName(), result.getBankName());
        assertEquals(swiftCode.getCountryIso2Code(), result.getCountryISO2());
        assertEquals(swiftCode.getCountryName(), result.getCountryName());
        assertEquals(swiftCode.isHeadquarter(), result.isHeadquarter());
        assertEquals(swiftCode.getSwiftCode(), result.getSwiftCode());
        assertEquals(1, result.getBranches().size());
    }

    @Test
    void toCountrySwiftCodeDetailsDto_ShouldMapListOfSwiftCodesToCountryDto() {
        SwiftCode swiftCode1 = new SwiftCode(
                "PLBPKOPLPWXXX",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw",
                true
        );
        SwiftCode swiftCode2 = new SwiftCode(
                "PLBPKOPLPWAAA",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "SIENKIEWICZA 12/14 WARSZAWA, MAZOWIECKIE, 00-944",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw",
                false
        );
        List<SwiftCode> swiftCodes = List.of(swiftCode1, swiftCode2);

        CountrySwiftCodesDetailsDto result = swiftCodeMapper.toCountrySwiftCodeDetailsDto(swiftCodes);

        assertNotNull(result);
        assertEquals(swiftCodes.get(0).getCountryIso2Code(), result.countryISO2());
        assertEquals(swiftCodes.get(0).getCountryName(), result.countryName());
        assertEquals(2, result.swiftCodes().size());
    }

    @Test
    void toEntity_ShouldMapSwiftCodeRequestToEntity() {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                true,
                "PLBPKOPLPWXXX"
        );

        SwiftCode result = swiftCodeMapper.toEntity(request);

        assertNotNull(result);
        assertEquals(request.swiftCode(), result.getSwiftCode());
        assertEquals(request.countryISO2().toUpperCase(), result.getCountryIso2Code());
        assertEquals(request.bankName(), result.getBankName());
        assertEquals(request.address(), result.getAddress());
        assertEquals(request.countryName().toUpperCase(), result.getCountryName());
        assertEquals(request.isHeadquarter(), result.isHeadquarter());
    }
}
