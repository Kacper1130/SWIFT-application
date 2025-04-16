package org.example.swiftcodesapplication;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SwiftCode {

    @Id
    private String swiftCode;
    private String countryIso2Code;
    private String codeType;
    private String bankName;
    private String address;
    private String townName;
    private String countryName;
    private String timeZone;
    private boolean isHeadquarter;

}
