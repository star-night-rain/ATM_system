package org.example;

public class Main
{
    static String ip = "127.0.0.1";
    static int port = 2525;
    public static void main(String[] args)
    {
        new TCPClient(ip,port);
    }
}