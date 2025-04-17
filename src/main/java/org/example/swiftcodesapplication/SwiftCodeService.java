package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.*;
import org.example.swiftcodesapplication.exception.CountryISO2CodeDoesNotExistException;
import org.example.swiftcodesapplication.exception.InvalidSwiftCodeException;
import org.example.swiftcodesapplication.exception.SwiftCodeAlreadyExistsException;
import org.example.swiftcodesapplication.exception.SwiftCodeDoesNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwiftCodeService {

    private final SwiftCodeRepository swiftCodeRepository;
    private final SwiftCodeMapper swiftCodeMapper;

    public SwiftCodeService(SwiftCodeRepository swiftCodeRepository, SwiftCodeMapper swiftCodeMapper) {
        this.swiftCodeRepository = swiftCodeRepository;
        this.swiftCodeMapper = swiftCodeMapper;
    }

    public SwiftCodeDetailsDto getBySwiftCode(String code) {
        SwiftCode swiftCode = swiftCodeRepository.findById(code)
                .orElseThrow(() -> new SwiftCodeDoesNotExistException(code));

        if (swiftCode.isHeadquarter()) {
            return getHeadquarterDetails(swiftCode);
        } else {
            return swiftCodeMapper.toBranchSwiftCodeDetailsDto(swiftCode);
        }
    }

    private SwiftCodeDetailsDto getHeadquarterDetails(SwiftCode swiftCode) {
        List<SwiftCode> branches = swiftCodeRepository.findAllBySwiftCodeStartingWith(swiftCode.getSwiftCode().substring(0, 8))
                .stream()
                .filter(branch -> !branch.getSwiftCode().equals(swiftCode.getSwiftCode()))
                .toList();

        return swiftCodeMapper.toHeadquarterSwiftCodeDetailsDto(swiftCode, branches);
    }

    public CountrySwiftCodesDetailsDto getByCountry(String countryISO2code) {
        List<SwiftCode> swiftCodes = swiftCodeRepository.findAllByCountryIso2Code(countryISO2code);

        if (swiftCodes.isEmpty()) {
            throw new CountryISO2CodeDoesNotExistException(countryISO2code);
        }

        return swiftCodeMapper.toCountrySwiftCodeDetailsDto(swiftCodes);
    }

    public ResponseDto add(SwiftCodeRequest swiftCodeRequest) {
        if (swiftCodeRepository.existsById(swiftCodeRequest.swiftCode())) {
            throw new SwiftCodeAlreadyExistsException(swiftCodeRequest.swiftCode());
        }

        validateSwiftCodeRequest(swiftCodeRequest);

        SwiftCode swiftCode = swiftCodeMapper.toEntity(swiftCodeRequest);
        swiftCodeRepository.save(swiftCode);
        return new ResponseDto("Successfully added SWIFT code to database");
    }

    private void validateSwiftCodeRequest(SwiftCodeRequest swiftCodeRequest) {
        if (swiftCodeRequest.isHeadquarter() && !swiftCodeRequest.swiftCode().endsWith("XXX")) {
            throw new InvalidSwiftCodeException("Headquarter SWIFT code must end with 'XXX'");
        }
        if (!swiftCodeRequest.isHeadquarter() && swiftCodeRequest.swiftCode().endsWith("XXX")) {
            throw new InvalidSwiftCodeException("Branch SWIFT code must not end with 'XXX'");
        }
    }

    public ResponseDto delete(String swiftCode) {
        if (!swiftCodeRepository.existsById(swiftCode)) {
            throw new SwiftCodeDoesNotExistException(swiftCode);
        }

        swiftCodeRepository.deleteById(swiftCode);

        return new ResponseDto("Successfully deleted SWIFT code");
    }

    public ResponseDto parseDataFromCsv() {
        List<CsvSwiftCode> csvSwiftCodes = CsvService.read();
        System.out.println("Zczytany rozmiar : " + csvSwiftCodes.size());
        List<SwiftCode> swiftCodes = csvSwiftCodes.stream().map(csvSwiftCode -> {
            SwiftCode swiftCode = new SwiftCode();
            swiftCode.setSwiftCode(csvSwiftCode.swiftCode());
            swiftCode.setCountryIso2Code(csvSwiftCode.countryIso2Code());
            swiftCode.setCodeType(csvSwiftCode.codeType());
            swiftCode.setBankName(csvSwiftCode.bankName());
            swiftCode.setAddress(csvSwiftCode.address());
            swiftCode.setTownName(csvSwiftCode.townName());
            swiftCode.setCountryName(csvSwiftCode.countryName());
            swiftCode.setTimeZone(csvSwiftCode.timeZone());

            boolean isHeadquarter = csvSwiftCode.swiftCode().endsWith("XXX");
            swiftCode.setHeadquarter(isHeadquarter);

            System.out.println("Processed SwiftCode: " + swiftCode.getSwiftCode());
            return swiftCode;
        }).toList();

        swiftCodeRepository.saveAll(swiftCodes);
        return new ResponseDto(String.format("Successfully parsed and saved %d records", swiftCodes.size()));
    }

}
