package com.application;

import com.application.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Wallet {
    private int money;
    private int noOfTransactions;

    public void incrementTransactions() {
        this.noOfTransactions += 1;
    }

    public void updateMoney(int amount, PersonType personType, Person person) {
        if (personType == PersonType.SENDER) {
            money -= amount;
        }
        if (personType == PersonType.RECEIVER) {
            money += amount;
        }
        PrometheusUtils.getMoneyGuage().labels(person.getName()).set(money);
        PrometheusUtils.getTransactionCounter().labels(person.getName()).inc();
        incrementTransactions();
    }
}
