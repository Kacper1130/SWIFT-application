package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.CountrySwiftCodesDetailsDto;
import org.example.swiftcodesapplication.dto.ResponseDto;
import org.example.swiftcodesapplication.dto.SwiftCodeDetailsDto;
import org.example.swiftcodesapplication.dto.SwiftCodeRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class SwiftCodeServiceIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @Autowired
    private SwiftCodeService swiftCodeService;

    @BeforeEach
    void setUp() {
        swiftCodeRepository.save(SwiftCodeTestData.HEADQUARTER_SWIFT_CODE);
        swiftCodeRepository.save(SwiftCodeTestData.BRANCH_SWIFT_CODE);
    }

    @AfterEach
    void tearDown() {
        swiftCodeRepository.deleteAll();
    }

    @Test
    void connectionEstablished() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    void getBySwiftCode_WhenHeadquarterSwiftCodeExists_ShouldReturnHeadquarterDetails() {
        SwiftCodeDetailsDto result = swiftCodeService.getBySwiftCode(SwiftCodeTestData.HEADQUARTER_SWIFT_CODE.getSwiftCode());

        assertThat(result).isInstanceOf(SwiftCodeDetailsDto.class);
        assertThat(result.getSwiftCode()).isEqualTo(SwiftCodeTestData.HEADQUARTER_SWIFT_CODE.getSwiftCode());
        assertThat(result.isHeadquarter()).isTrue();
        assertThat(result).hasFieldOrProperty("branches");
    }

    @Test
    void getBySwiftCode_WhenBranchSwiftCodeExists_ShouldReturnBranchDetails() {
        SwiftCodeDetailsDto result = swiftCodeService.getBySwiftCode(SwiftCodeTestData.BRANCH_SWIFT_CODE.getSwiftCode());

        assertThat(result).isInstanceOf(SwiftCodeDetailsDto.class);
        assertThat(result.getSwiftCode()).isEqualTo(SwiftCodeTestData.BRANCH_SWIFT_CODE.getSwiftCode());
        assertThat(result.isHeadquarter()).isFalse();
    }

    @Test
    void getByCountry_WhenCountryExists_ShouldReturnCountryDetails() {
        CountrySwiftCodesDetailsDto result = swiftCodeService.getByCountry("PL");

        assertThat(result.countryISO2()).isEqualTo("PL");
        assertThat(result.countryName()).isEqualTo("POLAND");
        assertThat(result.swiftCodes()).hasSize(2);
    }

    @Test
    void add_WhenSwiftCodeIsValid_ShouldAddNewSwiftCode() {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "123 TEST ST, NEW YORK, NY, 10001",
                "TEST BANK",
                "US",
                "UNITED STATES",
                true,
                "ABCDEFGHXXX"
        );

        ResponseDto response = swiftCodeService.add(request);

        assertThat(response.message()).contains("Successfully added");
        assertThat(swiftCodeRepository.findById("ABCDEFGHXXX")).isPresent();
    }

    @Test
    void delete_WhenSwiftCodeExists_ShouldDeleteSwiftCode() {
        ResponseDto response = swiftCodeService.delete(SwiftCodeTestData.BRANCH_SWIFT_CODE.getSwiftCode());

        assertThat(response.message()).contains("Successfully deleted");
        assertThat(swiftCodeRepository.findById(SwiftCodeTestData.BRANCH_SWIFT_CODE.getSwiftCode())).isEmpty();
    }

}