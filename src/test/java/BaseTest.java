import com.application.person.Person;
import com.application.Wallet;
import org.junit.BeforeClass;

public class BaseTest {

    @BeforeClass
    public static void setup() {

    }

    Person mockedPerson() {
        return mockedPerson(true);
    }

    Person mockedPerson(String name) {
        return mockedPerson(name, true);
    }

    Person mockedPerson(boolean mockWallet) {
        return mockedPerson(ConstantsTest.DEFAULT_PERSON_NAME, mockWallet);
    }

    Person mockedPerson(String name, boolean mockWallet) {
        return Person.builder()
                .name(name)
                .wallet(mockWallet ? mockedWallet() : null)
                .build();
    }

    Wallet mockedWallet() {
        return mockedWallet(ConstantsTest.DEFAULT_WALLET_MONEY, 0);
    }

    Wallet mockedWallet(int money, int noOfTransactions) {
        return Wallet.builder().money(money).noOfTransactions(noOfTransactions).build();
    }
}
