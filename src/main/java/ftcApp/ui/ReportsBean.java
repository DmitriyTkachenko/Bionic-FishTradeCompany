package ftcApp.ui;

import ftcApp.model.Order;
import ftcApp.model.OrderedItem;
import ftcApp.service.OrderService;
import ftcApp.service.TestService;
import org.primefaces.model.chart.*;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ManagedBean
@SessionScoped
public class ReportsBean implements Serializable {
    private BarChartModel fishNamesIncomeBarModel;
    private PieChartModel fishNamesIncomePieModel;
    private BarChartModel daysIncomeBarModel;
    private Date start;
    private Date end;
    private Iterable<Order> orders;
    private Map<Object, Number> nameData;
    private Map<Object, Number> dayData;
    private double minIncomeName;
    private double minIncomeDay;
    private double maxIncomeName;
    private double maxIncomeDay;
    private boolean chartsReady;
    Random rand = new Random();

    @Inject
    private transient OrderService orderService;

    @Inject
    private transient TestService testService;

    @PostConstruct
    public void init() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).
                getAutowireCapableBeanFactory().
                autowireBean(this);

        start = new Date();
        end = new Date();
        chartsReady = false;
        testService.addOrders();
        createFishNamesIncomeBarModel();
        createFishNamesIncomePieModel();
        createDaysIncomeBarModel();
    }

    @PreDestroy
    public void destroy() {
        testService.removeOrders();
    }

    public void loadOrdersForPeriod() {
        orders = orderService.findCompletedOrdersBetweenDates(start, end);
        generateFishNamesIncomeData();
        generateDaysIncomeData();
        createFishNamesIncomeBarModel();
        createFishNamesIncomePieModel();
        createDaysIncomeBarModel();
    }

    private BarChartModel initFishNamesIncomeBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries income = new ChartSeries();

//        for (int i = 1; i <= 31; ++i) {
//            income.set("" + i, rand.nextInt(5000 + 1) + 5000);
//        }

        if (!chartsReady) {
            return model;
        }

        income.setData(nameData);

        model.addSeries(income);

        return model;
    }

    private void createFishNamesIncomeBarModel() {
        fishNamesIncomeBarModel = initFishNamesIncomeBarModel();

        fishNamesIncomeBarModel.setTitle("Income per fish name");

        Axis xAxis = fishNamesIncomeBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Name of fish");

        Axis yAxis = fishNamesIncomeBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Income, $");
        yAxis.setMin(0.9 * minIncomeName);
        yAxis.setMax(1.1 * maxIncomeName);
    }

    private void generateFishNamesIncomeData() {
        Map<Object, Double> data = new HashMap<>();
        minIncomeName = 0;
        maxIncomeName = Double.MAX_VALUE;

        if (orders == null || ((Collection) orders).size() == 0) {
            chartsReady = false;
            return;
        }

        chartsReady = true;

        for (Order order : orders) {
            for (OrderedItem orderedItem : order.getOrderedItems()) {
                double itemIncome = orderedItem.getPrice() * orderedItem.getWeight();
                String itemName = orderedItem.getItem().getNameColdStore();
                Double previous = data.get(itemName);
                if (previous == null) {
                    data.put(itemName, itemIncome);
                } else {
                    data.put(itemName, previous + itemIncome);
                }
            }
        }

        minIncomeName = Collections.min(data.values());
        maxIncomeName = Collections.max(data.values());
        nameData = (Map) data;
    }

    private void generateDaysIncomeData() {
        Map<Object, Double> ordersData = new HashMap<>();
        Map<Object, Double> data = new LinkedHashMap<>();
        minIncomeDay = 0;
        maxIncomeDay = Double.MAX_VALUE;

        if (orders == null || ((Collection) orders).size() == 0) {
            chartsReady = false;
            return;
        }

        chartsReady = true;

        for (Order order : orders) {
            String completed = new SimpleDateFormat("dd.MM.yyyy").format(order.getCompleted());
            double income = order.getTotalPrice();
            Double previous = ordersData.get(completed);
            if (previous == null) {
                ordersData.put(completed, income);
            } else {
                ordersData.put(completed, income + previous);
            }
        }

        LocalDate current = this.start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = this.end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        while (!current.equals(end)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String date = dateTimeFormatter.format(current);
            Double incomeForDate = ordersData.get(date);
            if (incomeForDate == null) {
                data.put(date, 0.0);
            } else {
                data.put(date, incomeForDate);
            }
            current = current.plusDays(1);
        }

        minIncomeDay = Collections.min(data.values());
        maxIncomeDay = Collections.max(data.values());
        dayData = (Map) data;
    }

    private PieChartModel initFishNamesIncomePieModel() {
        PieChartModel model = new PieChartModel();

        if (!chartsReady) {
            return model;
        }

        model.setData((Map) nameData);

        return model;
    }

    private void createFishNamesIncomePieModel() {
        fishNamesIncomePieModel = initFishNamesIncomePieModel();

        fishNamesIncomePieModel.setTitle("Fish name share in total income");
        fishNamesIncomePieModel.setLegendPosition("e");
        fishNamesIncomePieModel.setShowDataLabels(true);
    }

    private BarChartModel initDaysIncomeBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries income = new ChartSeries();

        if (!chartsReady) {
            return model;
        }

        income.setData(dayData);

        model.addSeries(income);

        return model;
    }

    private void createDaysIncomeBarModel() {
        daysIncomeBarModel = initDaysIncomeBarModel();

        daysIncomeBarModel.setTitle("Income per day");

        Axis xAxis = daysIncomeBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Date");

        Axis yAxis = daysIncomeBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Income, $");
        yAxis.setMin(5000);
        yAxis.setMax(15500);
    }

    public BarChartModel getFishNamesIncomeBarModel() {
        return fishNamesIncomeBarModel;
    }

    public PieChartModel getFishNamesIncomePieModel() {
        return fishNamesIncomePieModel;
    }

    public BarChartModel getDaysIncomeBarModel() {
        return daysIncomeBarModel;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public boolean isChartsReady() {
        return chartsReady;
    }

    public void setChartsReady(boolean chartsReady) {
        this.chartsReady = chartsReady;
    }

    public double getMinIncomeName() {
        return minIncomeName;
    }

    public void setMinIncomeName(double minIncomeName) {
        this.minIncomeName = minIncomeName;
    }

    public double getMinIncomeDay() {
        return minIncomeDay;
    }

    public void setMinIncomeDay(double minIncomeDay) {
        this.minIncomeDay = minIncomeDay;
    }

    public double getMaxIncomeName() {
        return maxIncomeName;
    }

    public void setMaxIncomeName(double maxIncomeName) {
        this.maxIncomeName = maxIncomeName;
    }

    public double getMaxIncomeDay() {
        return maxIncomeDay;
    }

    public void setMaxIncomeDay(double maxIncomeDay) {
        this.maxIncomeDay = maxIncomeDay;
    }
}
