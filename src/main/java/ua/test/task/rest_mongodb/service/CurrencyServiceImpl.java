package ua.test.task.rest_mongodb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.test.task.rest_mongodb.exception.AppException;
import ua.test.task.rest_mongodb.model.Currency;
import ua.test.task.rest_mongodb.repository.CurrencyRepository;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency getMin(String crypto) {
        List<Currency> currencies = currencyRepository.findAllByCryptoNameOrderByPriceAsc(crypto);
        if(currencies.isEmpty()) {
            throw AppException.currencyNotFound(crypto);
        }
        return currencies.get(0);
    }

    @Override
    public Currency getMax(String crypto) {
        List<Currency> currencies = currencyRepository.findAllByCryptoNameOrderByPriceDesc(crypto);
        if(currencies.isEmpty()) {
            throw AppException.currencyNotFound(crypto);
        }
        return currencies.get(0);
    }

    @Override
    public Page<Currency> getCurrencies(String crypto, Pageable paging) {
        if(!currencyRepository.existsByCryptoName(crypto)) {
            throw AppException.currencyNotFound(crypto);
        }
        return currencyRepository.findAllByCryptoNameOrderByPriceAsc(crypto, paging);
    }
}
