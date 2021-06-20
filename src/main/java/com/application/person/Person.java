package com.application.person;

import com.application.Wallet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Person implements Comparable<Person> {

    private int id;
    private String name;
    private Wallet wallet;

    @Override
    public int compareTo(Person other) {
        return Integer.compare(other.getWallet().getNoOfTransactions(), wallet.getNoOfTransactions());
    }
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        return this.name.equals(person.getName());
    }
}
