package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Network{
    private Thread serverThread,t;
    private ServerSocket servr;

    private String adresa = "localhost";
    private int port = 500;

    public Client client;
    private Server server;
    private Socket socket;

    public Network() {
        try {
            socket = new Socket(adresa, port);
        } catch (IOException e) {
            System.out.println("server neni -> vytvorit");
        }
        if (socket == null) {
            try {
                server = new Server();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if(socket == null)
                socket = new Socket(adresa, port);
            client = new Client(socket);
        } catch (Exception e) {
            System.out.println("server neni -> idk ??");
        }
    }

    public ArrayList<GameObject> update(ArrayList<GameObject> send){
        var xyz = client.update(send).clone();
        client.list.clear();

        return (ArrayList<GameObject>) xyz;
    }
}
