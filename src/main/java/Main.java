import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        Iservice service = new Service();
        Scanner sc = new Scanner(System.in);
        String str;

        URL url = Main.class.getResource("input.txt");
        File file = new File(url.getPath());
        BufferedReader br = new BufferedReader(new FileReader(file));

        while ((str = br.readLine()) != null) {
//        while (true) {
//            str = sc.nextLine();
            System.out.println(str);
            String[] command = str.split(" ");
            List<String> commandList = Arrays.asList(command);
            if (command[0].equals("add")) {
                List<String> names = commandList.subList(1, commandList.size());
                service.addPersons(names);
            } else if (command[0].equals("remove")) {
                List<String> names = commandList.subList(1, commandList.size());
                service.removePersons(names);
            } else if (command[0].equals("transact")) {
                try {
                    service.transact(command[1], command[2], Integer.parseInt(command[3]));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (command[0].equals("status")) {

                try {
                    List<String> names = commandList.subList(1, commandList.size());
                    List<Person> persons = service.getPersons(names);
                    for (Person person : persons) {
                        System.out.println(person);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (command[0].equals("top")) {
                System.out.println(service.getTopCustomer());
            } else if (command[0].equals("exit")) {
                break;
            } else {
                System.out.println("Try again");
            }
        }
    }
}
