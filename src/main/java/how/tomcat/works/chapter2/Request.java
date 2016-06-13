package how.tomcat.works.chapter2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mazhibin on 16/6/13
 */
public class Request implements ServletRequest{

    private final InputStream in;
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

    public String getUri() {
        return uri;
    }

    public Object getAttribute(String s) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String s) {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String s) {
        return new String[0];
    }

    public Map getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String s, Object o) {

    }

    public void removeAttribute(String s) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    public String getRealPath(String s) {
        return null;
    }
}
