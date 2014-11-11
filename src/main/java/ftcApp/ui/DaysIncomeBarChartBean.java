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
public class DaysIncomeBarChartBean implements Serializable {
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

        income.set("Salmon", 14850);
        income.set("Tuna", 13333);
        income.set("Mackerel", 8999);
        income.set("Sprat", 5698);
        income.set("Cod", 11321);
        income.set("Hake", 12978);
        income.set("Capelin", 6834);
        income.set("Mullet", 7160);

        model.addSeries(income);

        return model;
    }

    private void createBarModels() {
        createBarModel();
    }

    private void createBarModel() {
        barModel = initBarModel();

        barModel.setTitle("Income per day");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Day of month");

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Income, $");
        yAxis.setMin(5000);
        yAxis.setMax(15500);
    }

}
