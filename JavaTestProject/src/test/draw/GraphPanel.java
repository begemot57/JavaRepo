package test.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphPanel extends JPanel {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private List<Color> lineColors = new ArrayList<Color>();
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<List<Double>> scores;

    public GraphPanel(List<List<Double>> scores, List<Color> lineColors) {
        this.scores = scores;
        if(lineColors != null)
        	this.lineColors = lineColors;
        else{
        	this.lineColors.add(new Color(44, 102, 230, 180));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.get(0).size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        List<List<Point>> graphPoints = new ArrayList<List<Point>>();
        List<Point> lineGraphPoints;
        for (int i = 0; i < scores.size(); i++) {
        	lineGraphPoints = new ArrayList<Point>();
	        for (int j = 0; j < scores.get(i).size(); j++) {
	            int x1 = (int) (j * xScale + padding + labelPadding);
	            int y1 = (int) ((getMaxScore() - scores.get(i).get(j)) * yScale + padding);
	            lineGraphPoints.add(new Point(x1, y1));
	        }
	        graphPoints.add(lineGraphPoints);
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (scores.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < scores.get(0).size(); i++) {
            if (scores.get(0).size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.get(0).size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((scores.get(0).size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldStroke = g2.getStroke();
        if(this.lineColors.size() == 1)
        	g2.setColor(this.lineColors.get(0));
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size(); i++) {
        	if(this.lineColors.size() > 1)
        		g2.setColor(this.lineColors.get(i));
        	lineGraphPoints = graphPoints.get(i);
	        for (int j = 0; j < lineGraphPoints.size() - 1; j++) {
	            int x1 = lineGraphPoints.get(j).x;
	            int y1 = lineGraphPoints.get(j).y;
	            int x2 = lineGraphPoints.get(j + 1).x;
	            int y2 = lineGraphPoints.get(j + 1).y;
	            g2.drawLine(x1, y1, x2, y2);
	        }
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
        	lineGraphPoints = graphPoints.get(i); 
	        for (int j = 0; j < lineGraphPoints.size(); j++) {
	            int x = lineGraphPoints.get(j).x - pointWidth / 2;
	            int y = lineGraphPoints.get(j).y - pointWidth / 2;
	            int ovalW = pointWidth;
	            int ovalH = pointWidth;
	            g2.fillOval(x, y, ovalW, ovalH);
	        }
        }
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }
    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (List<Double> score : scores) {
        	for (Double lineScore : score) {
        		minScore = Math.min(minScore, lineScore);
        	}
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (List<Double> score : scores) {
        	for (Double lineScore : score) {
        		maxScore = Math.max(maxScore, lineScore);
        	}
        }
        return maxScore;
    }

    public void setScores(List<List<Double>> scores) {
        this.scores = scores;
        invalidate();
        this.repaint();
    }

    public List<List<Double>> getScores() {
        return scores;
    }

    private static void createAndShowGui() {
        List<List<Double>> scores = new ArrayList<List<Double>>();
        Random random = new Random();
        int noLines = 3;
        int maxDataPoints = 100;
        int maxScore = 100;
        List<Double> line;
        for (int i = 0; i < noLines; i++) {
        	line = new ArrayList<Double>();
	        for (int j = 0; j < maxDataPoints; j++) {
	        	line.add((double) random.nextDouble() * maxScore);
	//            scores.add((double) i);
	        }
	        scores.add(line);
        }
        
        List<Color> lineColors = new ArrayList<Color>();
        lineColors.add(new Color(230, 0, 0, 180));
        lineColors.add(new Color(0, 230, 0, 180));
        lineColors.add(new Color(0, 0, 230, 180));
        
        GraphPanel mainPanel = new GraphPanel(scores, lineColors);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void drawLines(final List<List<Double>> lines, final List<Color> lineColors){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GraphPanel mainPanel = new GraphPanel(lines, lineColors);
                mainPanel.setPreferredSize(new Dimension(800, 600));
                JFrame frame = new JFrame("DrawGraph");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}
