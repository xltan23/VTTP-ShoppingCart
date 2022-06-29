package vttp.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class Repository {
    private File repository; 

    // Constructor that set up a file (Takes in String argument as File)
    public Repository(String repo) {
        repository = new File(repo);
    }

    public List<String> getShoppingCarts() {
        List<String> carts = new LinkedList<>();
        // List of Names.cart in repository 
        for (String n : repository.list()) {
            carts.add(n.replace(".cart", ""));  
        }
        // Return list of carts (Only the names)
        return carts;
    }

    public void save(Cart cart) {
        // Obtaining username from cart => Ralph.cart
        String cartName = cart.getUsername() + ".cart";
        // Create path for particular cart
        String saveLocation = repository.getPath() + File.separator + cartName; 
        // Proper file created for cart
        File saveFile = new File(saveLocation);
        try {
            if (!saveFile.exists()) 
                saveFile.createNewFile();
            OutputStream os = new FileOutputStream(saveLocation);
            cart.save(os);
            os.flush();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Cart load(String username) {
        String cartName = username + ".cart";
        Cart cart = new Cart(username);
        // Cart files are Names.cart files in repository
        for (File cartFile : repository.listFiles()) {
            if (cartFile.getName().equals(cartName)) {
                try {
                    InputStream is = new FileInputStream(cartFile);
                    cart.load(is);
                    is.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return cart;
    }
}
