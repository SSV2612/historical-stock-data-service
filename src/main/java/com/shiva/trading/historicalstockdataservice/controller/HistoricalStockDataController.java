package com.shiva.trading.historicalstockdataservice.controller;


import com.shiva.trading.historicalstockdataservice.model.HistoricalStockData;
import com.shiva.trading.historicalstockdataservice.service.HistoricalStockDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/historicStocks")
public class HistoricalStockDataController {

    @Autowired
    private HistoricalStockDataService historicalStockDataService;

    @GetMapping("/price/{ticker}")
    public List<HistoricalStockData> getHistoricalData(@PathVariable String ticker, @RequestParam(defaultValue = "DAILY") String interval) {
        if (!interval.equalsIgnoreCase("DAILY") && !interval.equalsIgnoreCase("WEEKLY") && !interval.equalsIgnoreCase("MONTHLY")) {
            throw new IllegalArgumentException("Invalid Interval argument. Valid values are DAILY, WEEKLY and MONTHLY");
        }

        return historicalStockDataService.getHistoricStockPrice(ticker, interval.toUpperCase());
    }
}
