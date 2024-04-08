package org.example;

import java.io.*;
import java.net.*;

class TCPServer {

    public static void main(String argv[]) throws Exception
    {
        String clientSentence;
        String capitalizedSentence;

        ServerSocket welcomeSocket = new ServerSocket(2525);

        while(true) {

            Socket connectionSocket = welcomeSocket.accept();

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            clientSentence = inFromClient.readLine();
            System.out.println(clientSentence);

            capitalizedSentence = "500 AUTH REQUIRED\n";

            DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            outToClient.writeBytes(capitalizedSentence);
        }
    }
}
