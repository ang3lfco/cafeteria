package componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PanelRedondoSombreado extends JPanel{
    private final int radioEsquina;
    private Color colorFondo;

    public PanelRedondoSombreado(int radioEsquina, Color colorFondo){
        this.radioEsquina = radioEsquina;
        this.colorFondo = colorFondo;
        setOpaque(false);
    }

    public void setBackgroundColor(Color backgroundColor){
        this.colorFondo = backgroundColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int desplazamientoSombra = 4;

        Color colorSombra = new Color(0, 0, 0, 60);
        g2.setColor(colorSombra);
        g2.fill(new RoundRectangle2D.Double(
            desplazamientoSombra, desplazamientoSombra,
            getWidth() - desplazamientoSombra,
            getHeight() - desplazamientoSombra,
            radioEsquina, radioEsquina
        ));

        g2.setColor(colorFondo);
        g2.fill(new RoundRectangle2D.Double(
            0, 0,
            getWidth() - desplazamientoSombra,
            getHeight() - desplazamientoSombra,
            radioEsquina, radioEsquina
        ));
        
        g2.dispose();
    }
}
