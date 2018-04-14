package com.company;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        if (args.length != 2) { /* Test for correct num. of arguments*/
            System.out.println("Error about the arguments");
            return ;
        }
        int port_num = Integer.parseInt(args[1]);
        Socket socket = new Socket();
        try {
            Socket c_sock = new Socket(args[0], port_num);

            while (true) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(c_sock.getInputStream())
                );
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(c_sock.getOutputStream()),
                        true);
                BufferedReader userEntry = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                System.out.print("Please enter your message: ");
                String input = userEntry.readLine();
                if (input.length() >= 4 && "EXIT".equals(input.substring(0,4))) {
                    out.println(input);
                    break;
                } else {
                    out.println(input);
                    System.out.println("The server is saying: "+in.readLine());
                }
            }
            c_sock.close();
        } catch (IOException ex) { ex.printStackTrace(); }
        System.exit(0);
    }
}
