package client;

public class Main
{
    static String ip = "10.234.107.98";
    static int port = 2525;
    public static void main(String[] args)
    {
        new TCPClient(ip,port);
    }
}