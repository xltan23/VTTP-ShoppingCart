package vttp.project;


public class CartMain {
    public static void main( String[] args ) {
        Repository repo = new Repository(args[0]);
        Session session = new Session(repo);
        session.start();
    }
}
