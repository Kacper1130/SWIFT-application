package org.example.swiftcodesapplication;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.swiftcodesapplication.dto.CsvSwiftCode;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

    private static final String FILE_PATH = "src/main/resources/data.csv";

    public static List<CsvSwiftCode> read() {
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
            throw new RuntimeException("CSV validation error", e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while reading CSV", e);
        }

        return swiftCodes;
    }
}
