package componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class ShadowRoundedPanel extends JPanel {
    private final int cornerRadius;
    private Color backgroundColor;

    public ShadowRoundedPanel(int cornerRadius, Color backgroundColor) {
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        setOpaque(false);
    }

    public void setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int shadowOffset = 4;

        Color shadowColor = new Color(0, 0, 0, 60);
        g2.setColor(shadowColor);
        g2.fill(new RoundRectangle2D.Double(
            shadowOffset, shadowOffset,
            getWidth() - shadowOffset,
            getHeight() - shadowOffset,
            cornerRadius, cornerRadius
        ));

        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Double(
            0, 0,
            getWidth() - shadowOffset,
            getHeight() - shadowOffset,
            cornerRadius, cornerRadius
        ));

        g2.dispose();
    }
}
