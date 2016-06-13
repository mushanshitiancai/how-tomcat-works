import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by mazhibin on 16/6/13
 */
public class PrimitiveServlet implements Servlet {
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("begin service");
        PrintWriter writer = servletResponse.getWriter();
        writer.println("Hello word!");
        writer.println("Hello tomcat!");
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
