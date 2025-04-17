package org.example.swiftcodesapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.swiftcodesapplication.dto.ResponseDto;
import org.example.swiftcodesapplication.dto.SwiftCodeRequest;
import org.example.swiftcodesapplication.exception.CountryISO2CodeDoesNotExistException;
import org.example.swiftcodesapplication.exception.ReadCsvException;
import org.example.swiftcodesapplication.exception.SwiftCodeAlreadyExistsException;
import org.example.swiftcodesapplication.exception.SwiftCodeDoesNotExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.swiftcodesapplication.SwiftCodeTestData.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SwiftCodeController.class)
class SwiftCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SwiftCodeService swiftCodeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBySwiftCode_WhenBranchSwiftCodeExists_ShouldReturnBranchDetails() throws Exception {
        when(swiftCodeService.getBySwiftCode("PLBPKOPLPWWAA")).thenReturn(BRANCH_DETAILS_DTO);

        mockMvc.perform(get("/v1/swift-codes/PLBPKOPLPWWAA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("PLBPKOPLPWWAA"));
    }

    @Test
    void getBySwiftCode_WhenHeadquarterSwiftCodeExists_ShouldReturnHeadquarterDetails() throws Exception {
        when(swiftCodeService.getBySwiftCode("PLBPKOPLPWXXX")).thenReturn(HEADQUARTER_DETAILS_DTO);

        mockMvc.perform(get("/v1/swift-codes/PLBPKOPLPWXXX"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("PLBPKOPLPWXXX"));
    }

    @Test
    void getBySwiftCode_WhenSwiftCodeDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(swiftCodeService.getBySwiftCode("INVALID"))
                .thenThrow(new SwiftCodeDoesNotExistException("INVALID"));

        mockMvc.perform(get("/v1/swift-codes/INVALID"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("SWIFT code 'INVALID' does not exist"));
    }

    @Test
    void getByCountry_WhenCountryExists_ShouldReturnCountrySwiftCodes() throws Exception {
        when(swiftCodeService.getByCountry("PL")).thenReturn(COUNTRY_DETAILS_DTO);

        mockMvc.perform(get("/v1/swift-codes/country/PL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryISO2").value("PL"))
                .andExpect(jsonPath("$.countryName").value("POLAND"))
                .andExpect(jsonPath("$.swiftCodes[0].swiftCode").value("PLBPKOPLPWXXX"))
                .andExpect(jsonPath("$.swiftCodes[1].swiftCode").value("PLBPKOPLPWWAA"));
    }

    @Test
    void getByCountry_WhenCountryDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(swiftCodeService.getByCountry("XX"))
                .thenThrow(new CountryISO2CodeDoesNotExistException("XX"));

        mockMvc.perform(get("/v1/swift-codes/country/XX"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Country with ISO2 code 'XX' does not exist"));
    }

    @Test
    void add_WhenValidRequest_ShouldReturnSuccessResponse() throws Exception {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                true,
                "PLBPKOPLPWXXX"
        );
        ResponseDto response = new ResponseDto("Swift code added");

        when(swiftCodeService.add(any())).thenReturn(response);

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Swift code added"));
    }

    @Test
    void add_WhenSwiftCodeAlreadyExists_ShouldReturnConflict() throws Exception {
        SwiftCodeRequest request = new SwiftCodeRequest(
                "UL. PULAWSKA 15 WARSZAWA, MAZOWIECKIE, 02-515",
                "PKO BANK POLSKI S.A.",
                "PL",
                "POLAND",
                true,
                "EXISTING"
        );
        when(swiftCodeService.add(any()))
                .thenThrow(new SwiftCodeAlreadyExistsException("EXISTING"));

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("SWIFT code 'EXISTING' already exists"));
    }

    @Test
    void delete_WhenSwiftCodeExists_ShouldReturnSuccessResponse() throws Exception {
        ResponseDto response = new ResponseDto("Deleted successfully");

        when(swiftCodeService.delete("DEUTDEFF")).thenReturn(response);

        mockMvc.perform(delete("/v1/swift-codes/DEUTDEFF"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Deleted successfully"));
    }

    @Test
    void delete_WhenSwiftCodeDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(swiftCodeService.delete("UNKNOWN"))
                .thenThrow(new SwiftCodeDoesNotExistException("UNKNOWN"));

        mockMvc.perform(delete("/v1/swift-codes/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("SWIFT code 'UNKNOWN' does not exist"));
    }

    @Test
    void loadData_WhenCalled_ShouldReturnSuccessMessage() throws Exception {
        ResponseDto response = new ResponseDto("Data loaded");

        when(swiftCodeService.parseDataFromCsv()).thenReturn(response);

        mockMvc.perform(post("/v1/swift-codes/load-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Data loaded"));
    }

    @Test
    void loadData_WhenCsvReadFails_ShouldReturnBadRequest() throws Exception {
        when(swiftCodeService.parseDataFromCsv())
                .thenThrow(new ReadCsvException("Error reading CSV file"));

        mockMvc.perform(post("/v1/swift-codes/load-data"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error reading CSV file"));
    }
}