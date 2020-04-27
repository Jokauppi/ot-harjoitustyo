package productivetime.ui.activitystatsview.chart;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;
import productivetime.domain.ActivityListService;
import productivetime.domain.TimeService;
import productivetime.ui.UIElement;

import java.time.ZonedDateTime;
import java.util.Map;

public class ActivityLineChart implements UIElement<LineChart<Number, Number>> {

    private LineChart<Number, Number> activitychart;
    private ActivityListService activityListService;

    public ActivityLineChart(ActivityListService activityListService, String type, ZonedDateTime start, ZonedDateTime end) {

        this.activityListService = activityListService;

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                return TimeService.formatZoned(TimeService.zonedOfSeconds( number.longValue()), "dd LLL");
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount of activity");

        activitychart = new LineChart<>(xAxis, yAxis);

        activitychart.getData().addAll(getData(type, start, end));
    }

    public XYChart.Series getData(String type, ZonedDateTime start, ZonedDateTime end) {

        Map<Long, Integer> amounts = activityListService.getAmountsOfTypeOnDaysBetween(type, start, end);

        XYChart.Series<Long, Integer> amountSeries = new XYChart.Series<>();
        amountSeries.setName("type");

        for (long date : amounts.keySet()) {
            amountSeries.getData().add(new XYChart.Data<>(date, amounts.get(date)));
        }

        return amountSeries;
    }

    @Override
    public LineChart<Number, Number> getLayout() {
        return activitychart;
    }
}
