package vttp.project;

import java.io.Console;
import java.util.List;

public class Session {
    public static final String LIST = "list";
    public static final String CARTS = "carts";
    public static final String ADD = "add";
    public static final String DELETE = "delete";
    public static final String LOAD = "load";
    public static final String SAVE = "save";
    public static final String EXIT = "exit";

    private Repository repository;
    private Cart cart;

    public Session(Repository repo) {
        this.repository = repo;
    }

    public void start() {
        Console cons = System.console();
        boolean stop = false;
        cart = new Cart("Ralph");

        while (!stop) {
            String input = cons.readLine("> ");
            String[] terms = input.split(" ");
            switch (terms[0]) {
                case CARTS: 
                    System.out.println("List of shopping carts");
                    // Print list of carts by the names
                    printList(repository.getShoppingCarts());
                    break;
                
                case LIST:
                    System.out.printf("Items in %s's shopping cart\n", cart.getUsername());
                    // Print items in cart
                    printList(cart.getContents());
                    break;
                
                case ADD: 
                    int before = cart.getContents().size();
                    for (int i = 1; i < terms.length; i++) {
                        // Perform operation to add using method add from Cart class
                        cart.add(terms[i]);
                    int addCount = cart.getContents().size() - before;
                    // Account for number of items added to cart
                    System.out.printf("Added %d item(s) into %s's cart\n", addCount, cart.getUsername());
                    }

                case DELETE:
                    int index = Integer.parseInt(terms[1]);
                    // Perform operation to remove item from cart using method delete
                    String item = cart.delete(index-1);
                    System.out.printf("Removed %s from %s's cart\n", item, cart.getUsername());

                case LOAD:
                    cart = repository.load(terms[1]);
                    System.out.printf("Loaded %s's shopping cart. There are %s item(s)\n", terms[1], cart.getContents().size());
                    break;

                case SAVE:
                    repository.save(cart);
                    System.out.printf("%s's cart has been saved\n", cart.getUsername());
                    break;

                case EXIT:
                    stop = true;
                    break;

                default:
                    System.err.printf("Unknown input: %s\n", terms[0]);
            } 
            System.out.println();
        }
        System.out.println("Thank you for shopping with us");
    }

    public void printList(List<String> list) {
        if (list.size() <= 0) {
            System.out.println("There are no contents.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", (i+1), list.get(i));
        }
    }
    
}
