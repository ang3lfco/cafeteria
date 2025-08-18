package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notificacion extends JPanel {
    private final JLayeredPane layeredPane;
    private final int ancho = 240;
    private final int altura = 70;

    public Notificacion(JLayeredPane layeredPane, String mensajePrincipal, String mensajeSecundario) {
        super(new GridBagLayout());
        this.layeredPane = layeredPane;

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBounds(getStartX(), -altura, ancho, altura);

        JPanel popupContenido = new JPanel();
        popupContenido.setBackground(new Color(80, 50, 30, 240));
        popupContenido.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        popupContenido.setLayout(new BoxLayout(popupContenido, BoxLayout.Y_AXIS));
        popupContenido.setOpaque(true);
        popupContenido.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTexto1 = new JLabel(mensajePrincipal);
        lblTexto1.setForeground(Color.WHITE);
        lblTexto1.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblTexto1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTexto2 = new JLabel(mensajeSecundario);
        lblTexto2.setForeground(new Color(255, 245, 210));
        lblTexto2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblTexto2.setAlignmentX(Component.CENTER_ALIGNMENT);

        popupContenido.add(lblTexto1);
        popupContenido.add(Box.createVerticalStrut(5));
        popupContenido.add(lblTexto2);

        add(popupContenido);
    }

    private int getStartX(){
        return (layeredPane.getWidth() - ancho) / 2;
    }

    public void mostrar(){
        layeredPane.add(this, JLayeredPane.POPUP_LAYER);
        layeredPane.repaint();

        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener(){
            int y = -altura;

            @Override
            public void actionPerformed(ActionEvent e){
                if(y < 20){
                    y += 2;
                    setLocation(getStartX(), y);
                }
                else{
                    setLocation(getStartX(), 20);
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void cerrar(){
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener(){
            int y = getY();

            @Override
            public void actionPerformed(ActionEvent e){
                if (y > -altura){
                    y -= 2;
                    setLocation(getStartX(), y);
                }
                else {
                    setLocation(getStartX(), -altura);
                    layeredPane.remove(Notificacion.this);
                    layeredPane.repaint();
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}
