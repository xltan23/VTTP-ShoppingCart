package vttp.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private String repoDir;
    private int port;

    public Server(String path, int port) {
        this.repoDir = path;
        this.port = port;
    }

    public void start() {
        ServerSocket server;
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            server = new ServerSocket(port);
            while(true) {
                System.out.println("Waiting for client connection");
                Socket sock = server.accept();
                System.out.println("Starting client connection handler");
                Repository repo = new Repository(repoDir);
                Session sess = new Session(repo, sock);
                threadPool.execute(sess);
            }
        } catch (IOException ex) {
            System.err.println("Server error, exiting");
            ex.printStackTrace();
        }
    }
}
