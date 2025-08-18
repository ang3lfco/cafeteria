package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;

import javax.swing.JPanel;

import componentes.PanelRedondo;

public class Contenedor extends JPanel{
    private PanelRedondo roundedPanel;

    public Contenedor(JPanel ventana) throws SQLException{
        setPreferredSize(new Dimension(600, 556));
        setLayout(null);
        setOpaque(false);

        roundedPanel = new PanelRedondo(30, Color.WHITE);
        roundedPanel.setBounds(0, 0, 600, 556);
        roundedPanel.add(ventana);
        add(roundedPanel);
    }

    public void cambiarVista(JPanel nuevaVista){
        roundedPanel.removeAll();
        nuevaVista.setBounds(0, 0, 600, 556);
        roundedPanel.add(nuevaVista);
        roundedPanel.revalidate();
        roundedPanel.repaint();
    }
}
