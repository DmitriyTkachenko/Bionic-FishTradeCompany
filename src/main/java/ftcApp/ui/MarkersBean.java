package ftcApp.ui;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
public class MarkersBean implements Serializable {

    private MapModel simpleModel;

    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
        LatLng coord = new LatLng(50.465107, 30.521178);
        simpleModel.addOverlay(new Marker(coord, "Head Office"));
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

}
