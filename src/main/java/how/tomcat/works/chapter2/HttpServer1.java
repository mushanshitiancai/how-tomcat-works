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
 * HttpServer建立Socket连接,获取输入输出流,建立Request和Response,然后转发给ServletProcessor或者StaticResourceProcessor
 *
 * 第二章的这个例子已经可以运行Servlet了.运行的逻辑是通过ServletProcessor中的ClassLoader来读取对应的Servlet类,然后调用service方法.
 */
public class HttpServer1 {
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
                    ServletProcessor1 processor = new ServletProcessor1();
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
        HttpServer1 server1 = new HttpServer1();
        server1.await();
    }
}
