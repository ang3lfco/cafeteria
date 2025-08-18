package componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PanelRedondo extends JPanel{
    private final int radioEsquina;
    private Color colorFondo;
    
    public PanelRedondo(int radio, Color colorFondo){
        this.radioEsquina = radio;
        this.colorFondo = colorFondo;
        setOpaque(false);
    }

    public void setBackgroundColor(Color colorFondo){
        this.colorFondo = colorFondo;
        repaint();
    }

    public Color getBackgroundColor(){
        return colorFondo;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colorFondo);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radioEsquina, radioEsquina));
        g2.dispose();
    }
}