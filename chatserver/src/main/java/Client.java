import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client extends Thread {
    private Socket socket;

    private String nickname = null;

    private static final List<Client> clientList = new LinkedList<>();

    public Client(Socket socket) {
        this.socket = socket;
        clientList.add(this);
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
        socket.getOutputStream().write(("Please enter a nickname: ").getBytes());
        Scanner scanner = new Scanner(socket.getInputStream());
        nickname = scanner.nextLine();
        while (true) {
            String readPacket = nickname + ": ";
            try {
                readPacket += scanner.nextLine();
            } catch (NoSuchElementException e) {
                break;
            }

            for (Client client : clientList) {
                if (client.nickname != null) {
                    boolean isSender = (client == this);
                    String sendablePacket = (isSender ? "\n" : "") + readPacket + (isSender ? "" : "\n");
                    client.socket.getOutputStream().write(sendablePacket.getBytes());
                }
            }
        }
        socket.close();
        clientList.remove(this);
    }
}
