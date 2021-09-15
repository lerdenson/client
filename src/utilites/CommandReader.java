package utilites;


import commands.*;
import general.Coordinates;
import general.Location;
import general.Route;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * The Command Reader class. It is meant to scan inputs and parse them into commands
 */
public class CommandReader {
    /**
     * Constructor for instantiating a new Command reader to use the System.in as input. Creates a new Scanner and uses a method chose
     *
     */
    public CommandReader(DatagramSocket socket, SocketAddress address, String filename) {
        Scanner scn = new Scanner(System.in);
        chose(scn,0, 10, socket, address, filename);
    }

    /**
     * Constructor for instantiating a new Command reader to use the script-file as input. Creates a new Scanner and uses a method chose with anonymous program.commands.Commands inheritor with muted commands
     *
     * @param script     the script-file where we want to read the commands from
     * @param marker     the marker that helps us to know if we are reading from script-file (marker>=1) or from System.in (marker=0).
     */
    public CommandReader(String script, int marker, long maxit, DatagramSocket socket, SocketAddress address, String filename) {
        try {
            Scanner scn = new Scanner(new File(script));
            Scanner scanner = new Scanner(System.in);
            if (marker == 2) {
                out.println("Seems like you are going deeper - how deep do you agree to go? Enter the number of iterations");
                while (true) {
                    try {
                        maxit = Long.parseLong(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        out.println("Use the number as a parameter");
                    }
                }
            }
            scanner.close();
            chose(scn, marker, maxit, socket, address, filename);
        } catch (FileNotFoundException e) {
            out.println("No such a file found");
        } catch (StackOverflowError error) {
            out.println("Could not continue because of danger of StackOverflow. " + marker + " iterations happened");
        }
    }

    /**
     * Method for reading the input and parsing the Strings into commands
     *
     * @param scn        the Scanner tuned with constructor to read from System.in or from script-file
     * @param marker     the marker that helps us to know if we are reading from script-file (marker>=1) or from System.in (marker=0).
     */
    public void chose(Scanner scn, int marker, long maxit, DatagramSocket socket, SocketAddress address, String filename) {
        String[] words;
        String line;
        while (scn.hasNextLine()) {
            line = scn.nextLine();
            words = line.split(" ");
            switch (words[0]) {
                case "help":
                    send(new HelpCommand(filename), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "info":
                    send(new InfoCommand(filename), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "show":
                    send(new ShowCommand(filename), socket, address);
                    //new SaveCommand("BACKUP");
                    receive(socket);
                    break;
                case "add":
                    try {
                        if (words[1].equals("")) {
                            out.println("Name must exist! Try to fix inputs");
                            break;
                        }
                        send(new AddCommand(filename, words[1], collectCoordinates(scn), LocalDate.now(), collectLocation(scn), collectLocation(scn), Float.parseFloat(words[2])), socket, address);
                        receive(socket);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Not enough arguments");
                    } catch (NumberFormatException e) {
                        out.println("Health must be Double");
                    }
                    //new SaveCommand("BACKUP");
                    break;
                case "update":
                    try {
                        if (words[2].equals("")) {
                            out.println("Name must exist! Try to fix inputs");
                            break;
                        }
                        send(new UpdateCommand(filename, Integer.parseInt(words[1]), words[2], collectCoordinates(scn), LocalDate.now(), collectLocation(scn), collectLocation(scn), Float.parseFloat(words[3])), socket, address);
                        receive(socket);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Not enough arguments");
                    } catch (NumberFormatException e) {
                        out.println("Distance must be Float, id must be Integer");
                    }
                    //new SaveCommand("BACKUP");
                    break;
                case "remove_by_id":
                    try {
                        send(new RemoveByIdCommand(filename, Integer.parseInt(words[1])), socket, address);
                        receive(socket);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Not enough arguments");
                    } catch (NumberFormatException e) {
                        out.println("Id must be Long");
                    }
                    //new SaveCommand("BACKUP");
                    break;
                case "clear":
                    send(new ClearCommand(filename), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "exit":
                    exit(scn, filename, socket, address);
                    break;
                case "remove_greater":
                    send(new RemoveGreaterCommand(filename, new Route(words[1], collectCoordinates(scn), LocalDate.now(), collectLocation(scn), collectLocation(scn), Float.parseFloat(words[2]))), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "remove_lower":
                    send(new RemoveLowerCommand(filename, new Route(words[1], collectCoordinates(scn), LocalDate.now(), collectLocation(scn), collectLocation(scn), Float.parseFloat(words[2]))), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "add_if_max":
                    send(new AddIfMaxCommand(filename, words[1], new Coordinates(Float.parseFloat(words[2]), Long.parseLong(words[3])), LocalDate.now(), new Location(Float.parseFloat(words[4]), Integer.parseInt(words[5]), words[6]), new Location(Float.parseFloat(words[7]), Integer.parseInt(words[8]), words[9]), Float.parseFloat(words[10])), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "count_greater_than_distance":
                    try {
                        send(new CountGreaterThanDistanceCommand(filename, Float.parseFloat(words[1])), socket, address);
                        receive(socket);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Not enough arguments");
                    } catch (IllegalArgumentException e) {
                        out.println("Weapon must be one of the given list (CHAIN_SWORD, MANREAPER, LIGHTING_CLAW, POWER_FIST)");
                    }
                    //new SaveCommand("BACKUP");
                    break;
                case "filter_less_than_distance":
                    try {
                        send(new FilterLessThanDistance(filename, Float.parseFloat(words[1])), socket, address);
                        receive(socket);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Can't find the parameter");
                    } catch (IllegalArgumentException e) {
                        out.println("Category must be one of the given list (INCEPTOR, SUPPRESSOR, TACTICAL) or should not exist");
                    }
                    //new SaveCommand("BACKUP");
                    break;
                case "remove_all_by_distance":
                    send(new RemoveAllByDistanceCommand(filename, Float.parseFloat(words[1])), socket, address);
                    receive(socket);
                    //new SaveCommand("BACKUP");
                    break;
                case "execute_script":
                    File script = new File(words[1]);
                    if (!script.canRead()) {
                        out.println("Script file is unable to read");
                        continue;
                    }
                    if (marker >= maxit) {
                        //new SaveCommand("BACKUP");
                        break;
                    }
                    try {
                        new CommandReader(words[1], marker + 1, maxit, socket, address, filename);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        out.println("Not enough arguments");
                    }
                    //new SaveCommand("BACKUP");
                    break;
                default:
                    if (marker == 0) {
                        out.println("Wrong command, try again");
                    }
                    break;
            }
            if (marker == 0) {
                out.println("Enter the command:");
            }
        }
    }


    private void exit(Scanner scn, String fileName, DatagramSocket socket, SocketAddress address) {
        if (new File("BACKUP").exists()) {
            out.println("R u sure 'bout that? (Yeah/Nah)");
            String answer = scn.nextLine();
            if ((answer.equals("Nah")) || (answer.equalsIgnoreCase("N"))) {
                out.println("Exiting stopped, you can continue");
            } else if ((answer.equals("Yeah")) || (answer.equalsIgnoreCase("Y"))) {
                out.println("Thanks for using our airline! See ya soon. All processes are stopped...");
                try {
                    Files.delete(Paths.get("BACKUP"));
                } catch (IOException e) {
                    out.println(e.getMessage());
                }
                System.exit(0);
            } else {
                out.println("For your safe we managed to stop exiting");
            }
        } else {
            out.println("Thanks for using our airline! See ya soon. All processes are stopped...");
            System.exit(0);
        }
    }

    private void send(AbstractCommand command, DatagramSocket socket, SocketAddress address) {
        byte[] sending;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(command);
            out.flush();
            sending = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sending, sending.length, address);
            socket.send(packet);
        } catch (PortUnreachableException e) {
            out.println("Port is unreachable - try again later");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void receive(DatagramSocket socket) {
        byte[] message = new byte[1024];
        try {
            DatagramPacket packet = new DatagramPacket(message, message.length);
            socket.setSoTimeout(10000);
            socket.receive(packet);
            ByteArrayInputStream bis = new ByteArrayInputStream(message);
            ObjectInput in = new ObjectInputStream(bis);
            String received_message = (String) in.readObject();
            System.out.println(received_message);
        } catch (SocketTimeoutException e) {
            out.println("Timeout exceeded");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Location collectLocation(Scanner scn) {
        String locationName;
        Float x;
        Integer y;
        while (true) {
                out.println("Enter the location name:");
                locationName = scn.nextLine();
                break;
        }

        while (true) {
            try {
                out.println("enter x-coordinate:");
                x = Float.parseFloat(scn.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("x should be the number. Please try again");
            }
        }
        while (true) {
            try {
                out.println("Enter y-coordinate:");
                y = Integer.parseInt(scn.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("y should be the Integer. Please try again");
            }
        }
        return new Location(x, y, locationName);
    }

    private Coordinates collectCoordinates(Scanner scn) {
        Float x;
        Long y;
        while (true) {
            try {
                out.println("enter x-coordinate:");
                x = Float.parseFloat(scn.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("x should be the number. Please try again");
            }
        }

        while (true) {
            try {
                out.println("Enter y-coordinate:");
                y = Long.parseLong(scn.nextLine());
                break;
            } catch (NumberFormatException e) {
                out.println("x should be Long. Please try again");
            }
        }
        return new Coordinates(x, y);
    }
}
