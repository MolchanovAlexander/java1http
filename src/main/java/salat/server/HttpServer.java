package salat.server;

import salat.server.config.Configuration;
import salat.server.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 *  Driver Class for Http Server
 */
public class HttpServer {
    public static void main (String[] args){
        String path = "src/main/resources/http.json";
        System.out.println(" Server starting... ");
        ConfigurationManager.getInstance().loadConfigurationFile(path);

        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());
        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // todo we would read
            String html = "<html><head><title>JAVA server</title></head><body><h1>JAVA GUF RIP</h1></body></html>";
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
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
