package salat.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListnerThread extends Thread{
     private final static Logger LOGGER = LoggerFactory.getLogger(ServerListnerThread.class);
    private  int port;
    private  String webRoot;
    private ServerSocket serverSocket;
    public  ServerListnerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(this.port);
    }
    @Override
    public void run(){
        byte count = 0;
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info(" * Connection accepted - " + count++ +" " + socket.getInetAddress());
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
           // serverSocket.close(); // TODO Handle close.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
