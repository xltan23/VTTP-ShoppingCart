package vttp.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class Cart {

    private List<String> contents = new LinkedList<>();
    private String username;
    
    // Default constructor
    public Cart(String name) {
        this.username = name;
    }

    // Implement getters
    public List<String> getContents() {
        return contents;
    }

    public String getUsername() {
        return username;
    }

    // Methods: Adding (Perform addition of item into cart)
    public void add(String item) {
        for (String inCart : contents) {
            if (inCart.equals(item)) {
                return;
            }
            contents.add(item);
        }
    }

    // Methods: Delete (Perform removal of the item from cart)
    public String delete(int index) {
        if (index < contents.size()) {
            contents.remove(index);
        }
        return "nothing";
    }

    // Methods: Load (Username of cart as inputstream)
    public void load(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String item;
        while ((item=br.readLine())!=null) {
            // Fill up contents with items in the user's cart
            contents.add(item);
        } 
        br.close();
        isr.close();
    }
    
    // Methods: Save (Username of cart as outputstream)
    public void save(OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        for (String item : contents) {
            // Transfer items in user's cart (contents) to repository
            bw.write(item+"\n");
        osw.flush();
        bw.flush();
        osw.close();
        bw.close();
        }
    }
}
