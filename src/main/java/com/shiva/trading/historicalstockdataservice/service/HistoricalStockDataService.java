package com.shiva.trading.historicalstockdataservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiva.trading.historicalstockdataservice.model.HistoricalStockData;
import com.shiva.trading.historicalstockdataservice.repository.HistoricalStockDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
@RequiredArgsConstructor
public class HistoricalStockDataService {

    private static final Logger log = LoggerFactory.getLogger(HistoricalStockDataService.class);

    @Value("${alpha.vantage.base_url}")
    private String BASE_URL;

    @Value("${alpha.vantage.api.key}")
    private String API_Key;

    @Autowired
    private HistoricalStockDataRepository historicstockRepository;

    @Cacheable(value = "historicalStocks", key = "#ticker + ':' + #interval")
    public List<HistoricalStockData> getHistoricStockPrice(String ticker, String interval) {

        String timeSeriesKey = "";

        if ("DAILY".equalsIgnoreCase(interval)) {
            timeSeriesKey = "Time Series (Daily)";
        } else if ("WEEKLY".equalsIgnoreCase(interval)) {
            timeSeriesKey = "Weekly Time Series";
        } else if ("MONTHLY".equalsIgnoreCase(interval)) {
            timeSeriesKey = "Monthly Time Series";
        } else {
            throw new IllegalArgumentException("Invalid interval specified. Supported intervals: DAILY, WEEKLY, MONTHLY.");
        }

        log.debug("Fetching data for Ticker: {} with Interval: {}", ticker, interval);
        String url = String.format("%s?function=TIME_SERIES_%s&symbol=%s&apikey=%s", BASE_URL, interval, ticker, API_Key);
        log.debug("Constructed API URL: {}", url);
        RestTemplate restTemplate = new RestTemplate();
        List<HistoricalStockData> stockHistoryList = new ArrayList<>();

        try {
            String response = restTemplate.getForObject(url, String.class);
            log.debug("Received Response from Alpha Vantage API: {}", response);
            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(response).path(timeSeriesKey);
            log.debug("Parsed Time Series Node: {}", rootNode);

            rootNode.fields().forEachRemaining(entry -> {
                try {
                    JsonNode values = entry.getValue();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(entry.getKey()); // Parse the date string to Date object
                    log.debug("Parsed Date: {}", parsedDate);

                    HistoricalStockData history = new HistoricalStockData(
                            ticker,
                            interval,
                            parsedDate,
                            values.path("1. open").asDouble(),
                            values.path("4. close").asDouble(),
                            values.path("2. high").asDouble(),
                            values.path("3. low").asDouble(),
                            values.path("5. volume").asLong()
                    );
                    log.debug("Constructed HistoricalStockData: {}", history);
                    stockHistoryList.add(history);
                } catch (Exception ex) {
                    ex.printStackTrace(); // Log any specific errors for debugging
                    System.err.println("Skipping entry due to parsing error: " + entry.getKey());
                    log.error("Error parsing data entry: {}", ex.getMessage());
                }
            });

            System.out.println("Saving to Mongo:" + stockHistoryList);
            historicstockRepository.saveAll(stockHistoryList);
            log.debug("Saved data to MongoDB: {}", stockHistoryList);
            return stockHistoryList;

        } catch (Exception e) {
            log.error("Error fetching data for ticker {}: {}", ticker, e.getMessage());
            throw new IllegalArgumentException("Failed to fetch data for ticker: " + ticker);
        }
    }
}

