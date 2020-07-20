package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Client {
    private String adresa = "localhost";
    private int port = 500;
    ObjectInputStream input;
    ObjectOutputStream output;
    Socket socket;
    public boolean reset = false;

    public Client(Socket socket) {
        this.socket = socket;
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            Thread.sleep(100);
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            System.out.println("cant contact server");
        }
        Thread t = new Thread(() -> recieve());
        t.setName("Conection Handler");
        t.start();
    }

    //furt posloucha
    public ArrayList<String> list = new ArrayList<String>();

    public void recieve(){
        System.out.println("CLIENT INIT");
        while(true){
            try {
                //list.addAll((List<String>) input.readObject());
                    var x = (ArrayList<String>) input.readObject();
                    if(x.size() <= 0 )
                        continue;
                for (var el: x) {
                        if(!list.contains(el))
                            list.add(el);
                }
                //list.addAll(x);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public ArrayList<String> update(ArrayList<String> send){
        try {
            output.writeObject(send);
            //output.writeUnshared(send);
            //output.flush();
            output.reset();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}