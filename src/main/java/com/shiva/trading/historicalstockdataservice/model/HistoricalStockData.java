package com.shiva.trading.historicalstockdataservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "historical_data")
public class HistoricalStockData implements Serializable {

    @Id
    private String id;

    private String ticker;
    private String interval; // daily, weekly, monthly
    private Date date;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
    private double closePrice;
    private long volume;

    public HistoricalStockData(String ticker, String interval, Date date, double openPrice, double closePrice, double highPrice, double lowPrice, long volume) {
        this.ticker = ticker;
        this.interval = interval;
        this.date = date;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
    }

}
