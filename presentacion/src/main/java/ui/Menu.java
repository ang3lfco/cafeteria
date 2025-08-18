package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import componentes.PanelRedondo;

public class Menu extends JPanel{
    private PanelRedondo roundedPanel;
    private Inicio inicio;
    private PanelRedondo panelActivo;

    public Menu(Inicio inicio){
        this.inicio = inicio;
        configurarMenu();
    }

    void configurarMenu(){
        setPreferredSize(new Dimension(202, 556));
        setLayout(null);
        setOpaque(false);
        setBounds(0, 0, 202, 556);
        roundedPanel = new PanelRedondo(30, new Color(109, 74, 49));
        roundedPanel.setBounds(0, 0, 202, 556);
        roundedPanel.setLayout(new BoxLayout(roundedPanel, BoxLayout.Y_AXIS));
        configurarLogoNegocio();
        agregarOpcionesMenu();
        add(roundedPanel);
    }

    void configurarLogoNegocio(){
        ImageIcon logoNegocio = new ImageIcon(getClass().getResource("/cafeteria4.png"));
        JLabel lblLogo = new JLabel(logoNegocio);
        lblLogo.setAlignmentX(CENTER_ALIGNMENT);
        lblLogo.setPreferredSize(new Dimension(0,180));
        roundedPanel.add(lblLogo);
    }

    void agregarOpcionesMenu(){
        JPanel fondo = new JPanel();
        fondo.setOpaque(false);
        JPanel contenedorOpciones = new JPanel();
        contenedorOpciones.setOpaque(false);
        contenedorOpciones.setLayout(new BoxLayout(contenedorOpciones, BoxLayout.Y_AXIS));
        PanelRedondo[] opciones = {
            new PanelRedondo(15, new Color(109, 74, 49)), //Cafés
            new PanelRedondo(15, new Color(109, 74, 49)), //Postres
            new PanelRedondo(15, new Color(109, 74, 49)), //Órdenes
            new PanelRedondo(15, new Color(109, 74, 49)), //Administrar
        };
        JLabel[] textoOpciones = {
            new JLabel("Cafés"),
            new JLabel("Postres"),
            new JLabel("Órdenes"),
            new JLabel("Administrar")
        };
        for(int i = 0 ; i < opciones.length ; i++){
            final int index = i;

            opciones[i].setPreferredSize(new Dimension(180,40));
            opciones[i].setOpaque(false);
            opciones[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            textoOpciones[i].setForeground(Color.WHITE);
            opciones[i].setLayout(new GridBagLayout());
            opciones[i].add(textoOpciones[i]);

            if(i == 0){
                actualizarEstadoActivo(opciones[0]);
            }

            switch (textoOpciones[i].getText()) {
                case "Cafés":
                    opciones[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            try {
                                actualizarEstadoActivo(opciones[index]);
                                inicio.actualizarVista(new Cafes(inicio));
                            } catch (SQLException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    });
                    break;
                case "Postres":
                    opciones[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            try {
                                actualizarEstadoActivo(opciones[index]);
                                inicio.actualizarVista(new Postres(inicio));
                            } catch (SQLException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    });
                    break;
                case "Órdenes":
                    opciones[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            actualizarEstadoActivo(opciones[index]);
                            JOptionPane.showMessageDialog(null, "Funcionalidad no disponible.");
                        }
                    });
                    break;
                case "Administrar":
                    opciones[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e){
                            actualizarEstadoActivo(opciones[index]);
                            JOptionPane.showMessageDialog(null, "Funcionalidad no disponible.");
                        }
                    });
                    break;
            
                default:
                    break;
            }
        }
        for(JPanel p : opciones){
            contenedorOpciones.add(p);
            contenedorOpciones.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        fondo.add(contenedorOpciones);
        roundedPanel.add(fondo);
    }

    public void actualizarEstadoActivo(PanelRedondo nuevoPanel){
        if(panelActivo != null){
            panelActivo.setBackgroundColor(new Color(109, 74, 49));
            ((JLabel)panelActivo.getComponent(0)).setForeground(Color.WHITE);
        }
        panelActivo = nuevoPanel;
        panelActivo.setBackgroundColor(Color.WHITE);
        ((JLabel)panelActivo.getComponent(0)).setForeground(Color.BLACK);
    }
}