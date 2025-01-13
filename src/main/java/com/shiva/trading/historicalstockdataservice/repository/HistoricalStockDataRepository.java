package com.shiva.trading.historicalstockdataservice.repository;


import com.shiva.trading.historicalstockdataservice.model.HistoricalStockData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalStockDataRepository extends MongoRepository<HistoricalStockData, String> {
}
