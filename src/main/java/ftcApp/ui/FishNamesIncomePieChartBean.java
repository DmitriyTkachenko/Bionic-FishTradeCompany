package ftcApp.ui;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class FishNamesIncomePieChartBean implements Serializable {
    private PieChartModel pieModel;

    @PostConstruct
    public void init() {
        createPieModels();
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    private void createPieModels() {
        createPieModel();
    }

    private void createPieModel() {
        pieModel = new PieChartModel();

        pieModel.set("Salmon", 14850);
        pieModel.set("Tuna", 13333);
        pieModel.set("Mackerel", 8999);
        pieModel.set("Sprat", 5698);
        pieModel.set("Cod", 11321);
        pieModel.set("Hake", 12978);
        pieModel.set("Capelin", 6834);
        pieModel.set("Mullet", 7160);

        pieModel.setTitle("Fish name share in total income");
        pieModel.setLegendPosition("e");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
    }

}
