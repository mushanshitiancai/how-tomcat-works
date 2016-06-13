package how.tomcat.works.chapter1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mazhibin on 16/6/13
 *
 * 最简单的静态资源服务器
 *
 * Request类解析HTTP头部,获取URI
 * Response类读取相对应的文件,把文件的*原始数据*发送给浏览器
 */
public class HttpServer {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    private static final String SHUTDOWN_COMMAND = "/shutdown";

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    private boolean shutdown = false;

    public void await(){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(HttpServer.PORT,1, InetAddress.getByName(HttpServer.HOST));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while(!shutdown){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                Request request = new Request(in);
                Response response = new Response(out);
                response.setRequest(request);

                request.parse();
                response.sendStaticResource();

                System.out.println(request.getUri());
                shutdown = request.getUri().startsWith(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }
}
