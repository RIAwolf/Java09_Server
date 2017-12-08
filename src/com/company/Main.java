package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final int PORT = 8080;


    public static void main(String[] args) {
        try {
            ServerSocket serveris = new ServerSocket(PORT);
            boolean testi = true;
            Socket kanalas = null;
            ObjectInputStream ivedimas = null;
            ObjectOutputStream isvedimas = null;
            kanalas = serveris.accept(); // cia kodas sustos ir lauks prisijungimo
            System.out.println("Prisijunge: " + kanalas.getInetAddress());
            isvedimas = new ObjectOutputStream(kanalas.getOutputStream());// output stream visada kurti pirma
            ivedimas = new ObjectInputStream(kanalas.getInputStream());
            while (testi) {
                String veiksmas = "";
                try {
                    if (ivedimas.available() > 0) {
                        veiksmas = ivedimas.readUTF();
                    }
                    if (veiksmas == null) {
                        kanalas.close();
                        veiksmas = "";
                    }
                } catch (Exception skaitymoKlaida) {
                    skaitymoKlaida.printStackTrace();
                }
                switch (veiksmas) {
                    case "":
                        break;
                    case "pabaiga":
                        System.out.println("Gautas nurodymas baigti darba");
                        testi = false;

                        isvedimas.writeUTF("pabaiga");
                        isvedimas.flush();

                        kanalas.close();
                        serveris.close();
                        break;
                    default:
                        System.out.println(veiksmas);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
