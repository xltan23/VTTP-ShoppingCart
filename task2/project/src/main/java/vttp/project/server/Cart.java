package vttp.project.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Cart {

    private String username;
    private List<String> contents;

    public Cart(String name) {
        this.username = name;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getContents() {
        return contents;
    }

    public void add(String item) {
        for (String inCart : contents) {
            if (inCart.equals(item)) {
                return;
            }
        }
        contents.add(item);
    }

    public String delete(int index) {
        if (index < contents.size()) {
            contents.remove(index);
        }
        return "nothing";
    }

    public void load(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String item;
        while ((item = br.readLine()) != null ) {
            contents.add(item);
        }
        br.close();
        isr.close();
    }

    public void save(OutputStream os) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        for (String item : contents) {
            bw.write(item + "\n");
        }
        osw.flush();
        bw.flush();
        osw.close();
        bw.close();
    }

}
