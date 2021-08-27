package com.application;


import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class PrometheusUtils {

    private static final String SUCCESS_STRING_LABEL = "SUCCESS";
    private static final String SUCCESS_BOOLEAN_LABEL = "1";
    private static final String FAILURE_BOOLEAN_LABEL = "0";


    private static final Counter transactionCounter;

    private static final Gauge moneyGuage;


    static {
        transactionCounter = Counter
                .build("transaction_count", "Transaction Counts")
                .labelNames("person")
                .register();
    }

    static {
        moneyGuage = Gauge
                .build("money_count", "Live Money")
                .labelNames("person")
                .register();
    }

    public static Counter getTransactionCounter() {
        return transactionCounter;
    }

    public static Gauge getMoneyGuage() {
        return moneyGuage;
    }
}
