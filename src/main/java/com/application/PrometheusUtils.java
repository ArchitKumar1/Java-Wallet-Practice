package com.application;


import io.prometheus.client.Counter;

public class PrometheusUtils {

    private static final String SUCCESS_STRING_LABEL = "SUCCESS";
    private static final String SUCCESS_BOOLEAN_LABEL = "1";
    private static final String FAILURE_BOOLEAN_LABEL = "0";


    private static final Counter transactionCounter;


    static {
        transactionCounter = Counter
                .build("transaction_count", "Transaction Counts")
                .labelNames("metric")
                .register();

    }

    public static Counter getTransactionCounter(){
        return transactionCounter;
    }
}
