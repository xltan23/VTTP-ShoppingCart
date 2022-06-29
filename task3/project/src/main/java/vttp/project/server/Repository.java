package vttp.project.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

public class Repository {
    private File repository;

    public Repository(String repo) {
        repository = new File(repo);
    }

    public List<String> getShoppingCarts() {
        List<String> carts = new LinkedList<>();
        for (String n : repository.list()) {
            carts.add(n.replace(".carts",""));
        }
        return carts;
    }

    public void save(Cart cart) {
        String cartName = cart.getUsername() + ".cart"
        String saveLocation = repository.getPath() + File.separator + cartName;
        File saveFile = new File(saveLocation);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
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
        for (File cartFile : repository.listFiles()) {
            if (cartFile.getName().equals(cartName)){
                try {
                    InputStream is = new FileInputStream(cartFile);
                    cart.load(is);
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return cart;
    }
}