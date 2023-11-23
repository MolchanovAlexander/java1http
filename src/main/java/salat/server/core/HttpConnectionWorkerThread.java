package salat.server.core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends  Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListnerThread.class);

    private  Socket socket;
    public  HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
             inputStream = socket.getInputStream();
             outputStream = socket.getOutputStream();

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
            LOGGER.info("Connect processing finished.");
        }catch (IOException e){
            throw new RuntimeException(e);
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
