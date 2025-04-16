package org.example.swiftcodesapplication;

import org.example.swiftcodesapplication.dto.CountrySwiftCodesDetailsDto;
import org.example.swiftcodesapplication.dto.ResponseDto;
import org.example.swiftcodesapplication.dto.SwiftCodeDetailsDto;
import org.example.swiftcodesapplication.dto.SwiftCodeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private final SwiftCodeService swiftCodeService;

    public SwiftCodeController(SwiftCodeService swiftCodeService) {
        this.swiftCodeService = swiftCodeService;
    }

    @GetMapping("/{swift-code}")
    public ResponseEntity<SwiftCodeDetailsDto> getBySwiftCode(@PathVariable("swift-code") String swiftCode) {
        return ResponseEntity.ok(swiftCodeService.getBySwiftCode(swiftCode));
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<CountrySwiftCodesDetailsDto> getByCountry(@PathVariable String countryISO2code) {
        return ResponseEntity.ok(swiftCodeService.getByCountry(countryISO2code));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> add(@RequestBody SwiftCodeRequest swiftCodeRequest) {
        return ResponseEntity.ok(swiftCodeService.add(swiftCodeRequest));
    }

    @DeleteMapping("/{swift-code}")
    public ResponseEntity<ResponseDto> delete(@PathVariable("swift-code") String swiftCode) {
        return ResponseEntity.ok(swiftCodeService.delete(swiftCode));
    }

    @PostMapping("/load-data")
    public ResponseEntity<ResponseDto> loadData() {
        return ResponseEntity.ok(swiftCodeService.parseDataFromCsv());
    }

}
