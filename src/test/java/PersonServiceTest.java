import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest {

    private PersonService personService;

    @Before
    public void beforeAll() {
        this.personService = new PersonService(new PersonDao());
    }

    @Test
    public void test() {

    }
}