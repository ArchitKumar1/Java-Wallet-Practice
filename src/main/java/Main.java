import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        setup();
        run();
    }

    private static void setup() {
        Setup setupJob = new Setup();
        setupJob.run();
    }

    private static void run() throws IOException {
        Iservice service = new Service();
        Scanner sc = new Scanner(System.in);
        String str;

        URL url = Main.class.getResource("input.txt");
        File file = new File(url.getPath());
        BufferedReader br = new BufferedReader(new FileReader(file));

        label:
        while ((str = br.readLine()) != null) {
//        while (true) {
//            str = sc.nextLine();
            System.out.println(str);
            String[] command = str.split(" ");
            List<String> commandList = Arrays.asList(command);
            switch (command[0]) {
                case "setpassword": {
                    String name = commandList.get(1);
                    String password = commandList.get(2);
                    service.addPassword(name,password);
                    break;
                }
                case "getpassword": {
                    String name = commandList.get(1);
                    String password = service.getPassword(name);
                    System.out.println(password);
                    break;
                }

                case "add": {
                    List<String> names = commandList.subList(1, commandList.size());
                    service.addPersons(names);
                    break;
                }

                case "remove": {
                    List<String> names = commandList.subList(1, commandList.size());
                    service.removePersons(names);
                    break;
                }
                case "transact":
                    try {
                        service.transact(command[1], command[2], Integer.parseInt(command[3]));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "status":

                    try {
                        List<String> names = commandList.subList(1, commandList.size());
                        List<Person> persons = service.getPersons(names);
                        for (Person person : persons) {
                            System.out.println(person);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case "top":
                    System.out.println(service.getTopCustomer());
                    break;
                case "exit":
                    break label;
                default:
                    System.out.println("Try again");
                    break;
            }
        }
    }
}
