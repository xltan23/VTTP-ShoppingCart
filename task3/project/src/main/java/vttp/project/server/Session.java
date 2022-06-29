package vttp.project.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class Session implements Runnable {
    public static final String CARTS = "carts"
    public static final String LIST = "list";
    public static final String ADD = "add";
    public static final String DELETE = "delete";
    public static final String LOAD = "load";
    public static final String SAVE = "save";
    public static final String EXIT = "exit";

    private Repository repository;
    private Cart cart;
    private Socket sock;
    private InputStream is;
    private ObjectInputStream ois;
    private OutputStream os;
    private ObjectOutputStream oos;

    public Session(Repository repo, Socket sock) {
        this.repository = repo;
        this.sock = sock;
    }

    public void run() {
        try {
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void start() throws IOException {
        boolean stop = false;
        initializeStreams(sock);
        cart = new Cart("Ralph");

        while(!stop) {
            String input = read();
            System.out.printf(">> %s\n", input);

            String[] terms = input.split(" ");
            StringBuilder builder = new StringBuilder();
            String msg = "";

            switch(terms[0]) {
                case CARTS:
                    builder.append("List of shopping carts\n");
                    printList(repository.getShoppingCarts(), builder);
                    msg = builder.toString();
                    break;

                case LIST:
                    msg = "Items in %s's shopping cart\n".formatted(cart.getUsername());
                    builder.append(msg);
                    printList(cart.getContents(), builder);
                    msg = builder.toString();
                    break;

                case ADD: 
                    int before = cart.getContents().size();
                    for (int i = 1; i < terms.length; i++) {
                        cart.add(terms[i]);
                    } 
                    int addCount = cart.getContents().size() - before;
                    msg = "Added %d item(s) into %s's cart\n".formatted(addCount,cart.getUsername());
                    break;

                case DELETE: 
                    int index = Integer.parseInt(terms[1]);
                    String item = cart.delete(index-1);
                    msg = "Removed %s from %s's cart\n".formatted(item, cart.getUsername());
                    break;

                case LOAD:
                    cart = repository.load(terms[1]);
                    msg = "Loaded %s shopping cart. There are %s item(s)\n".formatted(terms[1],cart.getContents().size());
                    break;

                case SAVE: 
                    repository.save(cart);
                    msg = "%s's cart has been saved\n".formatted(cart.getUsername());
                    break;

                case EXIT: 
                    stop = true;
                    continue;
                
                default: 
                    msg = "Unknown input: %s\n".formatted(terms[0]);
            }
            write(msg);
        }
        close();
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

    private void printList(List<String> list, StringBuilder builder) {
        if (list.size() <= 0) {
            builder.append("There are no contents\n");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            String msg = "%d. %s\n".formatted((i+1), list.get(i));
            builder.append(msg);
        }
    }
    
}
