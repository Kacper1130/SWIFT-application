package org.example.swiftcodesapplication;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.swiftcodesapplication.dto.CsvSwiftCode;
import org.example.swiftcodesapplication.exception.ReadCsvException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    private static final String FILE_PATH = "data.csv";

    public List<CsvSwiftCode> readCsvFile() {
        List<CsvSwiftCode> swiftCodes = new ArrayList<>();

        try (
                FileReader fileReader = new FileReader(FILE_PATH);
                CSVReader csvReader = new CSVReader(fileReader)
        ) {
            String[] line;
            csvReader.readNext();
            while ((line = csvReader.readNext()) != null) {
                swiftCodes.add(
                        new CsvSwiftCode(
                                line[0],
                                line[1],
                                line[2],
                                line[3],
                                line[4],
                                line[5],
                                line[6],
                                line[7]
                        )
                );
            }
        } catch (CsvValidationException e) {
            throw new ReadCsvException("CSV validation error");
        } catch (IOException e) {
            throw new ReadCsvException("I/O error while reading CSV");
        }

        return swiftCodes;
    }
}
