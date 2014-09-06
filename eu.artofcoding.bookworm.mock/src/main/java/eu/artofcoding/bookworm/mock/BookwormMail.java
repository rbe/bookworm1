package eu.artofcoding.bookworm.mock;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class BookwormMail {

    private GreenMail greenMail;

    public static void main(String[] args) {
        BookwormMail bookwormMail = new BookwormMail();
        bookwormMail.start();
    }

    public void start() {
        ServerSetup serverSetup = new ServerSetup(3025, "127.0.0.1", "smtp");
        greenMail = new GreenMail(serverSetup);
        greenMail.start();
    }

    private void stop() {
        greenMail.stop();
    }

}
