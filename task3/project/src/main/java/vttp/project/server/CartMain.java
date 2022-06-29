package vttp.project.server;

public class CartMain {
    public static void main(String[] args) {
        String repo = args[0];
        int port = Integer.parseInt(args[1]);
        Server server = new Server(repo, port);
        server.start();
    }
}
