package how.tomcat.works.chapter1;

import java.io.*;

/**
 * Created by mazhibin on 16/6/13
 */
public class Response {
    private static final int BUFFER_SIZE = 1024;
    private OutputStream out;
    private Request request;

    Response(OutputStream out){
        this.out = out;
    }

    public void setRequest(Request request){
        this.request = request;
    }

    public void sendStaticResource(){
        FileInputStream fis = null;
        byte[] buf = new byte[BUFFER_SIZE];

        try {
            File file = new File(HttpServer.WEB_ROOT,request.getUri());

            if(file.exists()){
                fis = new FileInputStream(file);

                int ch = fis.read(buf,0,BUFFER_SIZE);
                while(ch != -1){
                    out.write(buf,0,ch);
                    ch = fis.read(buf,0,BUFFER_SIZE);
                }
            }else{
                String err = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                out.write(err.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
