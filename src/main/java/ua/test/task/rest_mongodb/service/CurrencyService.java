package ua.test.task.rest_mongodb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.test.task.rest_mongodb.model.Currency;

public interface CurrencyService {

    Currency getMax(String crypto);

    Currency getMin(String crypto);

    Page<Currency> getCurrencies(String crypto, Pageable paging);
}
