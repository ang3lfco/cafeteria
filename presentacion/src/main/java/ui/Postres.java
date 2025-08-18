package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import componentes.BarraDesplazamiento;
import componentes.PanelRedondoSombreado;
import entidades.Postre;
import entidades.Producto;

public class Postres extends JPanel{
    private JPanel pnlFondo;
    private JPanel pnlPostres;
    private JScrollPane scrPostres;
    private Inicio inicio;

    private List<Postre> postres = new ArrayList<>();

    public Postres(Inicio inicio) throws SQLException{
        this.inicio = inicio;
        postres = inicio.obtenerPostres();
        setPreferredSize(new Dimension(590,546));
        setLayout(new GridBagLayout());
        setOpaque(false);
        crearPanelPostres();
        crearTarjetaPostre();
    }

    void crearPanelPostres(){
        pnlFondo = new JPanel();
        pnlFondo.setPreferredSize(new Dimension(580, 536));
        pnlFondo.setLayout(new GridBagLayout());
        pnlFondo.setOpaque(false);
        
        double cantidadElementos = postres.size();
        double numeroFilas = Math.ceil(cantidadElementos/3);
        double paddingTotal = numeroFilas*10;
        double largoTotal = (240*numeroFilas) + paddingTotal;

        pnlPostres = new JPanel();
        pnlPostres.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //570 de scrCafes menos 16 aprox del tamaño de la barra de scroll
        pnlPostres.setPreferredSize(new Dimension(554, (int) Math.ceil(largoTotal)));
        pnlPostres.setMaximumSize(new Dimension(554, (int) Math.ceil(largoTotal)));
        pnlPostres.setOpaque(false);
        
        scrPostres = new JScrollPane(pnlPostres);
        scrPostres.getVerticalScrollBar().setUI(new BarraDesplazamiento());
        scrPostres.getVerticalScrollBar().setOpaque(false);
        scrPostres.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));

        scrPostres.setOpaque(false);
        scrPostres.getViewport().setOpaque(false);
        scrPostres.setPreferredSize(new Dimension(570, 526));
        scrPostres.setBorder(null);
        scrPostres.getVerticalScrollBar().setUnitIncrement(16);
        scrPostres.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrPostres.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        pnlFondo.add(scrPostres);
        add(pnlFondo);
    }

    void crearTarjetaPostre(){
        for(Postre postre : postres){
            PanelRedondoSombreado postrePanel = new PanelRedondoSombreado(30, Color.WHITE);
            JPanel panelElementos = new JPanel();
            panelElementos.setOpaque(false);
            panelElementos.setLayout(new BoxLayout(panelElementos, BoxLayout.Y_AXIS));
            postrePanel.setLayout(new GridBagLayout());
            postrePanel.setPreferredSize(new Dimension(170, 240));
            JLabel lblPostreNombre = new JLabel(postre.getNombre());
            lblPostreNombre.setAlignmentX(CENTER_ALIGNMENT);
            
            ImageIcon postreImagen = new ImageIcon(getClass().getResource("/"+postre.getImagen()));
            JLabel lblPostreImagen = new JLabel(postreImagen);
            lblPostreImagen.setAlignmentX(CENTER_ALIGNMENT);
            
            panelElementos.add(lblPostreImagen);
            panelElementos.add(Box.createRigidArea(new Dimension(0, 15)));
            panelElementos.add(lblPostreNombre);
            postrePanel.add(panelElementos);
            postrePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e){
                    if(!inicio.verificarContenido()){
                        inicio.configurarPanelOrden();
                    }
                    try{
                        for(Component contenedor : inicio.elementosDeCentro()){
                            if(contenedor instanceof JPanel){
                                JPanel existente = (JPanel) contenedor;
                                Producto producto = (Producto) existente.getClientProperty("producto");
                                AtomicInteger cantidad = (AtomicInteger) existente.getClientProperty("cantidad");
                                JLabel label = (JLabel) existente.getClientProperty("label");
                                if(postre.getId() == producto.getId()){
                                    label.setText(String.valueOf(cantidad.incrementAndGet()));
                                    return;
                                }
                            }
                        }
                        inicio.agregarProductoEnOrden(postre, null);
                    }
                    catch(SQLException ex){
                        ex.printStackTrace();
                    }
                }
            });
            pnlPostres.add(postrePanel);
        }
        pnlPostres.revalidate();
        pnlPostres.repaint();
    }
}
