package rbf;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ScatterPlot extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;

    public ScatterPlot(String title,double x[], double [] y1, double[] y2, String key1, String key2) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(y1, y2, x, key1, key2);

        // Create chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                "function",
                "t", "X(t)", dataset);

        //Changes background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(225, 220, 250));


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(double [] y1, double [] y2, double [] x, String key1, String key2) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries(key1);
        for (int i = 0; i < y1.length; i++)
            series1.add(x[i],y1[i]);

        dataset.addSeries(series1);

        XYSeries series2 = new XYSeries(key2);
        for (int i = 0; i < y2.length; i++)
            series2.add(x[i],y2[i]);

        dataset.addSeries(series2);

        return dataset;
    }
}
