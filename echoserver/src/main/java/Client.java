import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Thread {
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handlePackets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePackets() throws IOException {
        Scanner scanner = new Scanner(socket.getInputStream());
        while (true) {
            String readPacket = null;
            try {
                readPacket = scanner.nextLine();
            } catch (NoSuchElementException e) {
                socket.close();
            }
            byte[] sendableBytes = readPacket.getBytes();
            socket.getOutputStream().write(sendableBytes);
        }
    }
}
