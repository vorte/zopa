package com.petrov.dimitar.zopa;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarketParser {

    public List<Offer> parseFile(String filePath) {
        File csvData = new File(filePath);
        CSVParser parser;
        try {
            // assuming charset and format as not specified in spec
            parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.EXCEL.withFirstRecordAsHeader());
        } catch (IOException ex) {
            return new ArrayList<>();
        }

        List<Offer> offers = new ArrayList<>();
        for (CSVRecord csvRecord : parser) {
            Double rate = new Double(csvRecord.get(1));
            Integer available = new Integer(csvRecord.get(2));

            offers.add(new Offer(available, rate));
        }

        return offers;
    }

}
