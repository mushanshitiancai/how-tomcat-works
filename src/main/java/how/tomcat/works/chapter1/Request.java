package how.tomcat.works.chapter1;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mazhibin on 16/6/13
 */
public class Request {
    private InputStream in;
    private String uri;

    Request(InputStream in){
        this.in = in;
    }

    public void parse(){
        StringBuffer sb = new StringBuffer();
        byte[] buffer = new byte[1024];

        int size = 0;
        try {
            size = in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < size; i++) {
            sb.append((char)buffer[i]);
        }

        System.out.println(sb.toString());

        uri = parseUri(sb.toString());
    }

    public String parseUri(String body){
        int index1 = body.indexOf(" ");
        if(index1 != -1){
            int index2 = body.indexOf(" ",index1+1);
            if(index2 != -1){
                return body.substring(index1+1,index2);
            }
        }
        return null;
    }

    public String getUri(){
        return uri;
    }
}
