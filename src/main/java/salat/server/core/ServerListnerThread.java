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
                LOGGER.info(" * Connection accepted" + count++ +socket.getInetAddress());
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                // todo we would read
                String html = "<html><head><title>JAVA server</title></head><body ><div style=\"display:flex;align-items:center;height:300px; background-color:lightgrey;justify-content:center;\"><h1 style=\"color:red\">JAVA GUF RIP</h1></div></body></html>";
                final String CRLF = "\n\r";
                String response =
                        "HTTP/1.1 200 OK" + CRLF +
                                "Content-Length: " + html.getBytes().length + CRLF +
                                CRLF +
                                html +
                                CRLF + CRLF;
                // todo we would writing
                outputStream.write(response.getBytes());

                inputStream.close();
                outputStream.close();
                socket.close();
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
           // serverSocket.close(); // TODO Handle close.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
