package how.tomcat.works.chapter2;

import how.tomcat.works.Constants;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by mazhibin on 16/6/13
 */
public class ServletProcessor2 {

    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader classLoader = null;

        try {
            URLStreamHandler streamHandler = null;
            URL[] urls = new URL[1];
            File classPath = new File(Constants.WEB_ROOT);

            // 这里 urls 是一个 java.net.URL 的对象数组,这些对象指向了加载类时候查找的位置。
            // 任何以/结尾的 URL 都假设是一个目录。否则,URL 会 Otherwise, the URL 假定是一个将被下载并在 需要的时候打开的 JAR 文件。
            // 注意:在一个 servlet 容器里边,一个类加载器可以找到 servlet 的地方被称为资源库 (repository)。
            String repository = new URL("file", null, classPath.getCanonicalPath() + File.separator).toString();

            urls[0] = new URL(null, repository, streamHandler);
            classLoader = new URLClassLoader(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class myClass = classLoader.loadClass(servletName);
            Servlet servlet = (Servlet) myClass.newInstance();

            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet.service(requestFacade, responseFacade);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
