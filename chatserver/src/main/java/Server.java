import java.io.IOException;
import java.net.ServerSocket;

public class Server extends ServerSocket implements Runnable {
    public Server(int port) throws IOException {
        super(port);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(4001);
        server.run();
    }

    @Override
    public void run() {
        while (true) {
            try {
                new Client(accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
