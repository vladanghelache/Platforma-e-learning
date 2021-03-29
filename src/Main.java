
import services.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        E_learningPlatform sesiune = E_learningPlatform.getInstance();
        boolean end = false;
        Scanner scanner = new Scanner(System.in);

        exit:
        while(!end) {
            while (sesiune.getCurrentUser() == null) {
                System.out.println("Pentru a va crea un cont nou apasati tasta '1' ");
                System.out.println("Pentru a va loga apasati tasta '2' ");
                System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                String option = scanner.next();

                switch (option) {
                    case "1":
                        sesiune.register();
                        break;
                    case "2":
                        sesiune.login();
                        break;
                    case "3":
                        end = true;
                        break exit;
                    default:
                        System.out.println("Comanda necunoscuta");
                }

            }
            while(sesiune.getCurrentUser() != null){

                System.out.println("Pentru a afisa informatiile dumneavoastra apasati tasta '1' ");
                System.out.println("Pentru a va deloga apasati tasta '2' ");
                System.out.println("Pentru a inchide sesiunea apasati tasta '3'");
                String option = scanner.next();

                switch (option) {
                    case "1":
                        System.out.println(sesiune.getCurrentUser());
                        break ;
                    case "2":
                        sesiune.logout();
                        break;
                    case "3":
                        end = true;
                        break exit;
                    default:
                        System.out.println("Comanda necunoscuta");
                }

            }
        }

    }
}
