package org.example.swiftcodesapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SwiftCodeDetailsDto {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
    private String swiftCode;

    protected SwiftCodeDetailsDto(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }
}
