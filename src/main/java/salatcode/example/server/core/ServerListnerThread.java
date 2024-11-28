package salatcode.example.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListnerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListnerThread.class);
    private int port;
    private String webRoot;
    private ServerSocket serverSocket;

    public ServerListnerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {

        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info(" * Connection accepted - " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
            // serverSocket.close(); // TODO Handle close.
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket.", e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
