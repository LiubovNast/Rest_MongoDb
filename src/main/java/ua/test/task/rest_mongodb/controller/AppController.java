package ua.test.task.rest_mongodb.controller;

import au.com.bytecode.opencsv.CSVWriter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.test.task.rest_mongodb.exception.AppException;
import ua.test.task.rest_mongodb.model.Currency;
import ua.test.task.rest_mongodb.service.CurrencyService;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cryptocurrencies")
public class AppController {

    private final CurrencyService currencyService;

    public AppController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/minprice")
    public Currency getMinPrice(@RequestParam("name") String name) {
        return currencyService.getMin(name);
    }

    @GetMapping("/maxprice")
    public Currency getMaxPrice(@RequestParam("name") String name) {
        return currencyService.getMax(name);
    }

    @GetMapping
    public Page<Currency> getCurrency(@RequestParam("name") String name,
                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable paging = PageRequest.of(page, size);
        return currencyService.getCurrencies(name, paging);
    }

    @GetMapping("/csv")
    public HttpStatus generateCsv() {
        List<String[]> csv = new ArrayList<>();
        String[] header = new String[3];
        header[0] = "Cryptocurrency Name";
        header[1] = "Min Price";
        header[2] = "Max Price";
        csv.add(header);

        csv.add(getCsvRow("BTC"));
        csv.add(getCsvRow("ETH"));
        csv.add(getCsvRow("XRP"));

        String fileName = "report_" + new Date() + ".csv";
        String path = "./reports";

        String allPath = path + "\\" + fileName.replaceAll(":", "_");
        try (CSVWriter writer = new CSVWriter(new FileWriter(allPath))) {
            if (Files.notExists(Path.of(path))) {
                Files.createDirectories(Path.of(path));
            }
            if (Files.notExists(Path.of(allPath))) {
                Files.createFile(Path.of(allPath));
            }
            writer.writeAll(csv);
        } catch (IOException e) {
            throw AppException.errorIO(e.getMessage());
        }
        return HttpStatus.OK;
    }

    private String[] getCsvRow(String cryptoName) {
        String[] row = new String[3];
        row[0] = cryptoName;
        row[1] = currencyService.getMin(cryptoName).getPrice().toString();
        row[2] = currencyService.getMax(cryptoName).getPrice().toString();
        return row;
    }
}
