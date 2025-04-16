package org.example.swiftcodesapplication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SwiftCodeRepository extends JpaRepository<SwiftCode, String> {

    List<SwiftCode> findAllBySwiftCodeStartingWith(String pattern);
    List<SwiftCode> findAllByCountryIso2Code(String countryISO2Code);

}
