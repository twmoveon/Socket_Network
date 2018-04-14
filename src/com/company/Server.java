package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
         if (args.length != 1) {  // Test for correct number of arguments
            System.out.println( "Error about the arguments");
            return ; }
        int port_num = Integer.parseInt(args[0]); /* First arg: server port num. */
        ServerSocket rv_sock = new ServerSocket(port_num);
        while(true){
            System.out.println( "\nWaiting for client to connect...\n");
            Socket s_sock = rv_sock.accept();
            while (true) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(s_sock.getInputStream())
                );
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(s_sock.getOutputStream()),
                        true);
                String input = in.readLine();
                if (input == null){
                    System.out.println("The command can't be empty");
                    out.println("The command can't be empty");
                }else{
                    if (input.equals("EXIT")){
                        System.out.println("Ending connection");
                        out.println("Ending connection");
                        break;
                    }
                    if(input.length()>3&&input.substring(0,3).equals("GET")){
                        String fileName = input.substring(3, input.length());
                        File file = new File(fileName);
                        if (file.exists()){
                            out.println("Reading file: " + fileName);
                            Scanner scanner = new Scanner(file);
                            while(scanner.hasNextLine()) {
                                System.out.println(scanner.nextLine());
                            }
                        }else{
                            System.out.println("ERROR: no such file");
                            out.println("ERROR: no such file!");
                        }
                    }else if(input.length()>6&&input.substring(0,6).equals("BOUNCE")){
                        System.out.println("The client is saying: "+input.substring(6,input.length()));
                        out.println((input.substring(6,input.length())));
                    }else if (input.length()>4&&input.substring(0,4).equals("EXIT")){
                        System.out.println("Ending connection: " + input.substring(4, input.length()));
                        out.println("Ending connection: " + input.substring(4, input.length()));
                        break;
                    }else{
                        System.out.println("Error: " + "No such command");
                        out.println("No such command!");
                    }
                }
            }
            s_sock.close();
            break;
        }
    }
}
