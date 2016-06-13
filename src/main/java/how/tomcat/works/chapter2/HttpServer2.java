package how.tomcat.works.chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mazhibin on 16/6/13
 *
 * 和HttpServer1的区别是使用ServletProcessor2替换ServletProcessor1
 * ServletProcessor2和ServletProcessor1的区别是:前者使用了Facade概念,使用户无法直接使用Request和Response类
 */
public class HttpServer2 {
    public static final String SHUTDOWN_COMMAND = "/shutdown";

    private boolean shutdown = false;

    public void await(){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(8080,1,InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(serverSocket!=null && !shutdown){
            try {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                Request request = new Request(in);
                request.parse();
                Response response = new Response(out,request);

                if(request.getUri().startsWith("/servlet/")){
                    ServletProcessor2 processor = new ServletProcessor2();
                    processor.process(request,response);
                }else{
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request,response);
                }

                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer2 server = new HttpServer2();
        server.await();
    }
}
