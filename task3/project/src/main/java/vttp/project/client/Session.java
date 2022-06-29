package vttp.project.client;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Session {
    public static final String EXIT = "exit";

    private String user;
    private Socket sock;
    private InputStream is;
    private OutputStream os;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Session(String user, Socket sock) {
        this.user = user;
        this.sock = sock;
    }

    public void start() throws IOException {
        initializeStreams(sock);
        write("load %s".formatted(user));
        String response = read();
        System.out.printf("%s\n", response);
        Console cons = System.console();
        while(true) {
            String input = cons.readLine("> ");
            String[] terms = input.split(" ");
            write(input);
            if (terms[0].equals(EXIT)) {
                break;
            }
            response = read();
            System.out.printf("%s\n", response);
        }
        System.out.println("\nThank you for shopping with us");
        try {
            close();
        } catch (IOException ex) {}
    }

    private String read() throws IOException {
        return ois.readUTF();
    }

    private void write(String out) throws IOException {
        oos.writeUTF(out);
        oos.flush();
    }

    private void initializeStreams(Socket sock) throws IOException {
        is = sock.getInputStream();
        ois = new ObjectInputStream(is);
        os = sock.getOutputStream();
        oos = new ObjectOutputStream(os);
    }

    private void close() throws IOException {
        is.close();
        os.close();
    }
}
