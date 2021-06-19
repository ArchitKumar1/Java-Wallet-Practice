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

    public void updateMoney(int amount, PersonType personType) {
        if (personType == PersonType.SENDER) {
            money -= amount;
        }
        if (personType == PersonType.RECEIVER) {
            money += amount;
        }
        incrementTransactions();
    }
}
