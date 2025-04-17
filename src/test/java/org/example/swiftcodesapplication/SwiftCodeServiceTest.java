package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.*;
import org.example.swiftcodesapplication.exception.CountryISO2CodeDoesNotExistException;
import org.example.swiftcodesapplication.exception.InvalidSwiftCodeException;
import org.example.swiftcodesapplication.exception.SwiftCodeAlreadyExistsException;
import org.example.swiftcodesapplication.exception.SwiftCodeDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.example.swiftcodesapplication.SwiftCodeTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SwiftCodeServiceTest {

    @Mock
    SwiftCodeRepository swiftCodeRepository;

    @Mock
    SwiftCodeMapper swiftCodeMapper;

    @Mock
    CsvService csvService;

    @InjectMocks
    SwiftCodeService swiftCodeService;


    @Test
    void getBySwiftCode_WhenHeadquarterSwiftCodeExists_ShouldReturnHeadquarterDetails() {
        when(swiftCodeRepository.findById("PLBPKOPLPWXXX")).thenReturn(Optional.of(HEADQUARTER_SWIFT_CODE));
        when(swiftCodeRepository.findAllBySwiftCodeStartingWith("PLBPKOPL")).thenReturn(List.of(HEADQUARTER_SWIFT_CODE, BRANCH_SWIFT_CODE));
        when(swiftCodeMapper.toHeadquarterSwiftCodeDetailsDto(eq(HEADQUARTER_SWIFT_CODE), anyList())).thenReturn(HEADQUARTER_DETAILS_DTO);

        SwiftCodeDetailsDto result = swiftCodeService.getBySwiftCode("PLBPKOPLPWXXX");

        assertNotNull(result);
        assertEquals(HEADQUARTER_DETAILS_DTO, result);
        verify(swiftCodeRepository).findById("PLBPKOPLPWXXX");
        verify(swiftCodeRepository).findAllBySwiftCodeStartingWith("PLBPKOPL");
        verify(swiftCodeMapper).toHeadquarterSwiftCodeDetailsDto(eq(HEADQUARTER_SWIFT_CODE), anyList());
    }

    @Test
    void getBySwiftCode_WhenBranchSwiftCodeExists_ShouldReturnBranchDetails() {
        when(swiftCodeRepository.findById("PLBPKOPLPWWAA")).thenReturn(Optional.of(BRANCH_SWIFT_CODE));
        when(swiftCodeMapper.toBranchSwiftCodeDetailsDto(BRANCH_SWIFT_CODE)).thenReturn(BRANCH_DETAILS_DTO);

        SwiftCodeDetailsDto result = swiftCodeService.getBySwiftCode("PLBPKOPLPWWAA");

        assertNotNull(result);
        assertEquals(BRANCH_DETAILS_DTO, result);
        verify(swiftCodeRepository).findById("PLBPKOPLPWWAA");
        verify(swiftCodeMapper).toBranchSwiftCodeDetailsDto(BRANCH_SWIFT_CODE);
    }

    @Test
    void getBySwiftCode_WhenSwiftCodeDoesNotExist_ShouldThrowException() {
        when(swiftCodeRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());

        SwiftCodeDoesNotExistException exception = assertThrows(
                SwiftCodeDoesNotExistException.class,
                () -> swiftCodeService.getBySwiftCode("NONEXISTENT")
        );
        assertEquals("SWIFT code 'NONEXISTENT' does not exist", exception.getMessage());
        verify(swiftCodeRepository).findById("NONEXISTENT");
    }

    @Test
    void getByCountry_WhenCountryExists_ShouldReturnCountryDetails() {
        List<SwiftCode> countrySwiftCodes = List.of(HEADQUARTER_SWIFT_CODE, BRANCH_SWIFT_CODE);
        when(swiftCodeRepository.findAllByCountryIso2Code("PL")).thenReturn(countrySwiftCodes);
        when(swiftCodeMapper.toCountrySwiftCodeDetailsDto(countrySwiftCodes)).thenReturn(COUNTRY_DETAILS_DTO);

        CountrySwiftCodesDetailsDto result = swiftCodeService.getByCountry("PL");

        assertNotNull(result);
        assertEquals(COUNTRY_DETAILS_DTO, result);
        verify(swiftCodeRepository).findAllByCountryIso2Code("PL");
        verify(swiftCodeMapper).toCountrySwiftCodeDetailsDto(countrySwiftCodes);
    }

    @Test
    void getByCountry_WhenCountryDoesNotExist_ShouldThrowException() {
        when(swiftCodeRepository.findAllByCountryIso2Code("XX")).thenReturn(Collections.emptyList());

        CountryISO2CodeDoesNotExistException exception = assertThrows(
                CountryISO2CodeDoesNotExistException.class,
                () -> swiftCodeService.getByCountry("XX")
        );
        assertEquals("Country with ISO2 code 'XX' does not exist", exception.getMessage());
        verify(swiftCodeRepository).findAllByCountryIso2Code("XX");
    }

    @Test
    void add_WhenSwiftCodeDoesNotExist_ShouldAddSwiftCode() {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                true,
                "PLBPKOPLPWXXX"
        );

        when(swiftCodeRepository.existsById("PLBPKOPLPWXXX")).thenReturn(false);
        when(swiftCodeMapper.toEntity(request)).thenReturn(HEADQUARTER_SWIFT_CODE);

        ResponseDto response = swiftCodeService.add(request);

        assertNotNull(response);
        assertEquals("Successfully added SWIFT code to database", response.message());
        verify(swiftCodeRepository).existsById("PLBPKOPLPWXXX");
        verify(swiftCodeMapper).toEntity(request);
        verify(swiftCodeRepository).save(HEADQUARTER_SWIFT_CODE);
    }

    @Test
    void add_WhenSwiftCodeAlreadyExists_ShouldThrowException() {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                true,
                "PLBPKOPLPWXXX"
        );

        when(swiftCodeRepository.existsById("PLBPKOPLPWXXX")).thenReturn(true);

        SwiftCodeAlreadyExistsException exception = assertThrows(
                SwiftCodeAlreadyExistsException.class,
                () -> swiftCodeService.add(request)
        );
        assertEquals("SWIFT code 'PLBPKOPLPWXXX' already exists", exception.getMessage());
        verify(swiftCodeRepository).existsById("PLBPKOPLPWXXX");
        verify(swiftCodeRepository, never()).save(any());
    }

    @Test
    void add_WhenHeadquarterDoesNotEndWithXXX_ShouldThrowException() {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "SIENKIEWICZA 12/14 WARSZAWA, MAZOWIECKIE, 00-944",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                true,
                "PLBPKOPLPWWAA"
        );

        when(swiftCodeRepository.existsById("PLBPKOPLPWWAA")).thenReturn(false);

        InvalidSwiftCodeException exception = assertThrows(
                InvalidSwiftCodeException.class,
                () -> swiftCodeService.add(request)
        );
        assertEquals("Headquarter SWIFT code must end with 'XXX'", exception.getMessage());
        verify(swiftCodeRepository).existsById("PLBPKOPLPWWAA");
        verify(swiftCodeRepository, never()).save(any());
    }

    @Test
    void add_WhenBranchEndsWithXXX_ShouldThrowException() {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                false,
                "PLBPKOPLPWXXX"
        );

        when(swiftCodeRepository.existsById("PLBPKOPLPWXXX")).thenReturn(false);

        InvalidSwiftCodeException exception = assertThrows(
                InvalidSwiftCodeException.class,
                () -> swiftCodeService.add(request)
        );
        assertEquals("Branch SWIFT code must not end with 'XXX'", exception.getMessage());
        verify(swiftCodeRepository).existsById("PLBPKOPLPWXXX");
        verify(swiftCodeRepository, never()).save(any());
    }

    @Test
    void delete_WhenSwiftCodeExists_ShouldDeleteSwiftCode() {
        when(swiftCodeRepository.existsById("PLBPKOPLPWXXX")).thenReturn(true);

        ResponseDto response = swiftCodeService.delete("PLBPKOPLPWXXX");

        assertNotNull(response);
        assertEquals("Successfully deleted SWIFT code", response.message());
        verify(swiftCodeRepository).existsById("PLBPKOPLPWXXX");
        verify(swiftCodeRepository).deleteById("PLBPKOPLPWXXX");
    }

    @Test
    void delete_WhenSwiftCodeDoesNotExist_ShouldThrowException() {
        when(swiftCodeRepository.existsById("NONEXISTENT")).thenReturn(false);

        SwiftCodeDoesNotExistException exception = assertThrows(
                SwiftCodeDoesNotExistException.class,
                () -> swiftCodeService.delete("NONEXISTENT")
        );
        assertEquals("SWIFT code 'NONEXISTENT' does not exist", exception.getMessage());
        verify(swiftCodeRepository).existsById("NONEXISTENT");
        verify(swiftCodeRepository, never()).deleteById(anyString());
    }

    @Test
    void parseDataFromCsv_ShouldSaveAllSwiftCodes() {
        CsvSwiftCode csvSwiftCode1 = new CsvSwiftCode(
                "PLBPKOPLPWXXX",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw"
        );

        CsvSwiftCode csvSwiftCode2 = new CsvSwiftCode(
                "PLBPKOPLPWWAA",
                "PL",
                "BIC11",
                "PKO BANK POLSKI S.A.",
                "SIENKIEWICZA 12/14 WARSZAWA, MAZOWIECKIE, 00-944",
                "WARSZAWA",
                "POLAND",
                "Europe/Warsaw"
        );

        List<CsvSwiftCode> csvSwiftCodes = List.of(csvSwiftCode1, csvSwiftCode2);
        when(csvService.readCsvFile()).thenReturn(csvSwiftCodes);

        ResponseDto response = swiftCodeService.parseDataFromCsv();

        assertNotNull(response);
        assertEquals("Successfully parsed and saved 2 records", response.message());
        verify(csvService).readCsvFile();
        verify(swiftCodeRepository).saveAll(anyList());
    }

}
