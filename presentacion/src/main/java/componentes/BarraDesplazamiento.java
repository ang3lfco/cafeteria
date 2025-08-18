package componentes;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class BarraDesplazamiento extends BasicScrollBarUI{
    private final Color customThumbColor = new Color(121, 85, 72);
    private final Color customTrackColor = new Color(245, 245, 245);

    @Override
    protected JButton createDecreaseButton(int orientation){
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation){
        return createZeroButton();
    }

    private JButton createZeroButton(){
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        button.setOpaque(false);
        button.setFocusable(false);
        button.setBorder(null);
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds){
        if (!c.isEnabled()) return;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(customThumbColor);
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(customTrackColor);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        g2.dispose();
    }
}
