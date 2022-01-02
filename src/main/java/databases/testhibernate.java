package databases;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class testhibernate {

//    public static void main(String[] args) {
//        Person person = Person.builder().id(23).name("archit").build();
//        Session session = HibernateUtils.getSessionFactory().openSession();
//        session.beginTransaction();
//        session.save(person);
//        session.getTransaction().commit();
//        session.close();
//        System.out.println("Done");
//
//        session.get(person)
//    }

    public static void main(String[] args) {

        String hql = "FROM Person where id = 4 and name = '4444'";
        Session session = HibernateUtils.getSessionFactory().openSession();

        Query<Person> query = session.createQuery(hql,Person.class);
        List<Person> results = query.list();

        for (Person person : results) {
            System.out.println(person);
        }
    }

}
