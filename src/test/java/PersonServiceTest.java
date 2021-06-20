import com.application.person.Person;
import com.application.person.PersonDao;
import com.application.person.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static com.application.Constants.DEFAULT_PERSON_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PersonServiceTest extends BaseTest {

    @Captor
    ArgumentCaptor<Person> personArgumentCaptor;
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonDao personDao;

    @Before
    public void beforeAll() {

    }

    @Test
    public void addPersons() {
        String name = "jack";
        doNothing().when(personDao).addPerson(any());
        personService.addPersons(Collections.singletonList(name));
        verify(personDao, times(1)).addPerson(personArgumentCaptor.capture());
        Person actualPerson = personArgumentCaptor.getValue();
        Assert.assertEquals(actualPerson.getName(), name);
    }

    @Test
    public void removePersons() {
        String name = "jack";
        doNothing().when(personDao).deletePerson(any());
        personService.removePersons(Collections.singletonList(name));
        verify(personDao, times(1)).deletePerson(stringArgumentCaptor.capture());
        String actualName = stringArgumentCaptor.getValue();
        Assert.assertEquals(name, actualName);
    }

    @Test
    public void getTopCustomer() {
        Person expectedPerson = mockedPerson(DEFAULT_PERSON_NAME);
        when(personDao.getTopCustomer()).thenReturn(expectedPerson);
        Person actualPerson = personService.getTopCustomer();
        verify(personDao, times(1)).getTopCustomer();
        Assert.assertEquals(actualPerson.getName(), expectedPerson.getName());
    }

}