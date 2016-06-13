package how.tomcat.works.chapter2;

import how.tomcat.works.chapter1.HttpServer;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * Created by mazhibin on 16/6/13
 */
public class Response implements ServletResponse{
    private static final int BUFFER_SIZE = 1024;

    private final OutputStream out;
    private final Request request;

    Response(OutputStream out,Request request){
        this.out = out;
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

    public String getCharacterEncoding() {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(out,true);
    }

    public void setContentLength(int i) {

    }

    public void setContentType(String s) {

    }

    public void setBufferSize(int i) {

    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {

    }

    public void resetBuffer() {

    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {

    }

    public void setLocale(Locale locale) {

    }

    public Locale getLocale() {
        return null;
    }
}
