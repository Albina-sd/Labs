package filtered_signal;

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

    public ScatterPlot(String title, double [] x, double [] y1, double [] y2, double [] y3) {
        super(title);

        // Create dataset
        XYDataset dataset = createDataset(x, y1, y2, y3);

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Signals",
                "x", "f(x)", dataset);


        //Changes background color
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));


        // Create Panel
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset(double [] x, double [] y1, double [] y2, double [] y3) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Original");
        for (int i = 0; i < x.length; i++){
            series1.add(x[i],y1[i]);
        }
        dataset.addSeries(series1);

        XYSeries series2 = new XYSeries("Noise");
        for (int i = 0; i < x.length; i++){
            series2.add(x[i],y2[i]);
        }
        dataset.addSeries(series2);

        XYSeries series3 = new XYSeries("Filtered");
        for (int i = 0; i < x.length; i++){
            series3.add(x[i],y3[i]);
        }
        dataset.addSeries(series3);

        return dataset;
    }
}
