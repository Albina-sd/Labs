package oneLayer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ScatterPlot extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;

    public ScatterPlot(String title, int [] y, String key) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(y, key);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Count of Errors",
                "k", "E(k)", dataset);

        //Changes background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(225, 220, 250));


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(int [] y, String key) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries(key);
        for (int i = 0; i < y.length; i++){
            series1.add((i+1),y[i]);
        }
        dataset.addSeries(series1);

        return dataset;
    }
}
