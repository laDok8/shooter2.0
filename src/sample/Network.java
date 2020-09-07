package sample;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Network{

    private static int port = 500;

    public static Client client;
    private static Server server;
    private static String pAdresa;

    public static String getpAdresa() {
        return pAdresa;
    }

    public static void makeClient(String adresa){
        pAdresa = adresa;
        try {
            Socket socket = new Socket(adresa, port);
            client = new Client(socket);
        } catch (IOException e) {
            System.out.println("server nenalezen");
        }
    }
    public static void makeServer(String adresa){
        pAdresa = adresa;
        server = new Server(port);
    }


    public static ArrayList<GameObject> update(ArrayList<GameObject> send){
        var xyz = client.update(send).clone();
        client.list.clear();

        return (ArrayList<GameObject>) xyz;
    }
}
