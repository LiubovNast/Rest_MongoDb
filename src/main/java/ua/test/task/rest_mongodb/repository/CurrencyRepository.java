package ua.test.task.rest_mongodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ua.test.task.rest_mongodb.model.Currency;

import java.util.List;

public interface CurrencyRepository extends MongoRepository<Currency, String> {

    List<Currency> findAllByCryptoNameOrderByPriceDesc(String cryptoName);

    List<Currency> findAllByCryptoNameOrderByPriceAsc(String cryptoName);

    Page<Currency> findAllByCryptoNameOrderByPriceAsc(String cryptoName, Pageable pageable);

    boolean existsByCryptoName(String crypto);
}
