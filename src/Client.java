
import utilites.CommandReader;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;


/**
 * The program.Main class of the program. Contains method main() where the main logic of the program is
 */
public class Client {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 0;
        Scanner scn = new Scanner(System.in);
        String input;
        try {
            System.out.println("Enter the address: ");
            address = scn.nextLine();
            System.out.println("Enter the port: ");
            input = scn.nextLine();
            try {
                port = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Wrong port");
                System.exit(1);
            }
            SocketAddress a = new InetSocketAddress(InetAddress.getByName(address), port);
            DatagramSocket s = new DatagramSocket();


            String filePath = "Data.xml";
            try {
                filePath = System.getenv("TEMP");
            } catch (Exception e) {
                System.out.println("Something wrong with your Environment Variable 'TEMP' - it must be equal to the name of your database");
                System.exit(1);
            }


            System.out.println("Enter the command: ");
            new CommandReader(s, a, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

