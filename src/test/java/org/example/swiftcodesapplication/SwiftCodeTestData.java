package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.*;
import java.util.List;

public class SwiftCodeTestData {

    public static final SwiftCode HEADQUARTER_SWIFT_CODE;
    public static final SwiftCode BRANCH_SWIFT_CODE;
    public static final HeadquarterSwiftCodeDetailsDto HEADQUARTER_DETAILS_DTO;
    public static final BranchSwiftCodeDetailsDto BRANCH_DETAILS_DTO;
    public static final CountrySwiftCodesDetailsDto COUNTRY_DETAILS_DTO;

    static {
        HEADQUARTER_SWIFT_CODE = new SwiftCode();
        HEADQUARTER_SWIFT_CODE.setSwiftCode("PLBPKOPLPWXXX");
        HEADQUARTER_SWIFT_CODE.setCountryIso2Code("PL");
        HEADQUARTER_SWIFT_CODE.setCodeType("BIC11");
        HEADQUARTER_SWIFT_CODE.setBankName("PKO BANK POLSKI S.A.");
        HEADQUARTER_SWIFT_CODE.setAddress("UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515");
        HEADQUARTER_SWIFT_CODE.setTownName("WARSZAWA");
        HEADQUARTER_SWIFT_CODE.setCountryName("POLAND");
        HEADQUARTER_SWIFT_CODE.setTimeZone("Europe/Warsaw");
        HEADQUARTER_SWIFT_CODE.setHeadquarter(true);

        BRANCH_SWIFT_CODE = new SwiftCode();
        BRANCH_SWIFT_CODE.setSwiftCode("PLBPKOPLPWWAA");
        BRANCH_SWIFT_CODE.setCountryIso2Code("PL");
        BRANCH_SWIFT_CODE.setCodeType("BIC11");
        BRANCH_SWIFT_CODE.setBankName("PKO BANK POLSKI S.A.");
        BRANCH_SWIFT_CODE.setAddress("SIENKIEWICZA 12/14 WARSZAWA, MAZOWIECKIE, 00-944");
        BRANCH_SWIFT_CODE.setTownName("WARSZAWA");
        BRANCH_SWIFT_CODE.setCountryName("POLAND");
        BRANCH_SWIFT_CODE.setTimeZone("Europe/Warsaw");
        BRANCH_SWIFT_CODE.setHeadquarter(false);

        BRANCH_DETAILS_DTO = new BranchSwiftCodeDetailsDto(
                BRANCH_SWIFT_CODE.getAddress(),
                BRANCH_SWIFT_CODE.getBankName(),
                BRANCH_SWIFT_CODE.getCountryIso2Code(),
                BRANCH_SWIFT_CODE.getCountryName(),
                BRANCH_SWIFT_CODE.isHeadquarter(),
                BRANCH_SWIFT_CODE.getSwiftCode()
        );

        HEADQUARTER_DETAILS_DTO = new HeadquarterSwiftCodeDetailsDto(
                HEADQUARTER_SWIFT_CODE.getAddress(),
                HEADQUARTER_SWIFT_CODE.getBankName(),
                HEADQUARTER_SWIFT_CODE.getCountryIso2Code(),
                HEADQUARTER_SWIFT_CODE.getCountryName(),
                HEADQUARTER_SWIFT_CODE.isHeadquarter(),
                HEADQUARTER_SWIFT_CODE.getSwiftCode(),
                List.of(
                        new SwiftCodeDto(
                                BRANCH_SWIFT_CODE.getAddress(),
                                BRANCH_SWIFT_CODE.getBankName(),
                                BRANCH_SWIFT_CODE.getCountryIso2Code(),
                                BRANCH_SWIFT_CODE.isHeadquarter(),
                                BRANCH_SWIFT_CODE.getSwiftCode()
                        )
                )
        );

        COUNTRY_DETAILS_DTO = new CountrySwiftCodesDetailsDto(
                "PL",
                "POLAND",
                List.of(
                        new SwiftCodeDto(
                                HEADQUARTER_SWIFT_CODE.getAddress(),
                                HEADQUARTER_SWIFT_CODE.getBankName(),
                                HEADQUARTER_SWIFT_CODE.getCountryIso2Code(),
                                HEADQUARTER_SWIFT_CODE.isHeadquarter(),
                                HEADQUARTER_SWIFT_CODE.getSwiftCode()
                        ),
                        new SwiftCodeDto(
                                BRANCH_SWIFT_CODE.getAddress(),
                                BRANCH_SWIFT_CODE.getBankName(),
                                BRANCH_SWIFT_CODE.getCountryIso2Code(),
                                BRANCH_SWIFT_CODE.isHeadquarter(),
                                BRANCH_SWIFT_CODE.getSwiftCode()
                        )
                )
        );



    }
}
