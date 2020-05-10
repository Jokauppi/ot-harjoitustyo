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

/**
 * Constructs a chart to show duration of a given activity on days of the given time frame.
 */
public class ActivityLineChart implements UIElement<LineChart<Number, Number>> {

    private LineChart<Number, Number> activitychart;
    private ActivityListService activityListService;

    public ActivityLineChart(ActivityListService activityListService, String type, ZonedDateTime start, ZonedDateTime end) {

        this.activityListService = activityListService;

        XYChart.Series<Number, Number> data = getData(type, start, end);

        if (data != null) {
            NumberAxis xAxis = getDayAxis(start, end);
            NumberAxis yAxis = getDurationAxis();

            activitychart = new LineChart<>(xAxis, yAxis);
            activitychart.setLegendVisible(false);

            activitychart.getData().add(data);
        }

    }

    private NumberAxis getDayAxis(ZonedDateTime start, ZonedDateTime end) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(start.toEpochSecond());
        xAxis.setUpperBound(end.toEpochSecond());
        xAxis.setTickUnit(60 * 60 * 24);
        xAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number number) {
                return TimeService.formatZoned(TimeService.zonedOfSeconds(number.longValue()), "dd LLL");
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        return xAxis;
    }

    private NumberAxis getDurationAxis() {
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Duration");
        yAxis.setTickLabelFormatter(new StringConverter<>() {
            @Override
            public String toString(Number number) {
                if (number.intValue() < 60 * 60) {
                    return number.intValue() / 60 + " min";
                } else {
                    return number.intValue() / 60 / 60 + " h " + number.intValue() / 60 % 60 + " min";
                }
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        return yAxis;
    }

    private XYChart.Series<Number, Number> getData(String type, ZonedDateTime start, ZonedDateTime end) {

        Map<Long, Integer> durations = activityListService.getDurationsOfTypeOnDaysBetween(type, start, end);

        if (durations.keySet().isEmpty()) {
            return null;
        }

        XYChart.Series<Number, Number> amountSeries = new XYChart.Series<>();
        amountSeries.setName("type");

        for (long date : durations.keySet()) {
            amountSeries.getData().add(new XYChart.Data<>(date, durations.get(date)));
        }

        return amountSeries;
    }

    @Override
    public LineChart<Number, Number> getLayout() {
        return activitychart;
    }
}
