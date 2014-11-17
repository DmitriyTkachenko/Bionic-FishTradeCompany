package ftcApp.ui;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/itemsChanged")
public class ItemsResource {

    @OnMessage(encoders = {JSONEncoder.class})
    public boolean onMessage(boolean flag) {
        return flag;
    }
}
