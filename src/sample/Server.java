package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Server {

    List<List<String>> listOfObjects = new ArrayList<>();
    List<Connection> clients = new ArrayList<>();

    private String adresa = "localhost";
    private int port = 500;
    private ServerSocket servr;
    private Socket s;

    public Server() throws IOException {
        servr = new ServerSocket(port);
        //servr.setSoTimeout(10000);
        //run v novem vlakne
        Thread t = new Thread(() -> {
            try {
                run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.start();
        //Thread t2 = new Thread(() -> run2());
        //t2.start();
    }


    public void run() throws IOException {
        int index = 0;
        while (true) {
            s = servr.accept();

            List<String> xx = new ArrayList<>();
            var helpme = new ObjectInputStream(s.getInputStream());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            var c = new Connection(
                    helpme,
                    new ObjectOutputStream(s.getOutputStream()),
                    s,
                    clients,
                    xx);
            index++;
            //kazdy klient ma sve vlakno
            Thread t = new Thread(() -> c.run());
            clients.add(c);
            //listOfObjects.add(xx);
            t.start();
        }
    }

    /*public void run2(){
        System.out.println("PISU");
        while (true) {
            //odeslu
            try {

                if(listOfObjects.size() > 0)
                    for (var cl: clients) {
                        for (var el: listOfObjects) {
                            cl.output.writeObject(el);
                        }
                        cl.output.flush();
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for(int i=0;i< listOfObjects.size();i++)
                listOfObjects.get(i).clear();
        }
    }
}*/


    class Connection {
        ObjectInputStream input;
        ObjectOutputStream output;
        Socket socket;
        List<Connection> clients;
        List<String> listOfObjects;

        public Connection(ObjectInputStream input, ObjectOutputStream output, Socket socket, List<Connection> clients, List<String> listOfObjects) {
            this.input = input;
            this.socket = socket;
            this.output = output;
            this.clients = clients;
            this.listOfObjects = listOfObjects;
        }

        public void run() {
            System.out.println("SERVER INIT");
            while (true) {
                try {
                        //cti (klient)
                        var get = input.readObject();

                        //zapis (broadcast)
                        for (var cl : clients) {
                            synchronized (clients) {
                                if(cl == this)
                                    continue;
                            cl.output.writeObject(get);
                            //cl.output.writeUnshared(get);
                            //cl.output.reset();
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
