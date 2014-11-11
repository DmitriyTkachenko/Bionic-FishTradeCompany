package ftcApp.ui;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Random;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
public class FishNamesIncomeBarChartBean implements Serializable {
    private BarChartModel barModel;
    Random rand = new Random();

    @PostConstruct
    public void init() {
        createBarModels();
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries income = new ChartSeries();

        for (int i = 1; i <= 31; ++i) {
            income.set("" + i, rand.nextInt(5000 + 1) + 5000);
        }



        model.addSeries(income);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Income per fish name");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Name of fish");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Income, $");
        yAxis.setMin(4500);
        yAxis.setMax(10500);
    }
}
