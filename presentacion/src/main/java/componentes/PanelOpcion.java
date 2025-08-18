package componentes;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PanelOpcion extends JPanel{
    private final Color barraColor = new Color(173, 114, 57);
    private final Color gradientInicio = new Color(173, 114, 57, 100);
    private final Color gradientFin = new Color(173, 114, 57, 0);

    public PanelOpcion(){
        setOpaque(false); 
        setPreferredSize(new Dimension(188, 30));
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        int ancho = getWidth();
        int altura = getHeight();

        int barraAncho = 10;
        g2.setColor(barraColor);
        g2.fillRect(0, 0, barraAncho, altura);

        GradientPaint gradient = new GradientPaint(barraAncho, 0, gradientInicio, ancho / 2, 0, gradientFin);
        
        g2.setPaint(gradient);
        g2.fillRect(barraAncho, 0, ancho - barraAncho, altura);
        g2.dispose();
        super.paintComponent(g);
    }
}
