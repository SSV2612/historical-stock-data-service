package com.shiva.trading.historicalstockdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HistoricalStockDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoricalStockDataServiceApplication.class, args);
    }

}
