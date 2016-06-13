package how.tomcat.works.chapter2;

/**
 * Created by mazhibin on 16/6/13
 */
public class StaticResourceProcessor {
    public void process(Request request,Response response){
        response.sendStaticResource();
    }
}
