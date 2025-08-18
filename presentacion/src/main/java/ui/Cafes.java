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
import entidades.Cafe;
import entidades.Producto;

public class Cafes extends JPanel{
    private JPanel pnlFondo;
    private JPanel pnlCafes;
    private JScrollPane scrCafes;
    
    private List<Cafe> cafes = new ArrayList<>();
    private Inicio inicio; 
    
    public Cafes(Inicio inicio) throws SQLException{
        this.inicio = inicio;
        cafes = inicio.obtenerCafes();
        setPreferredSize(new Dimension(590,546));
        setLayout(new GridBagLayout());
        setOpaque(false);
        crearPanelCafes();
        crearTarjetaCafe();
    }

    void crearPanelCafes(){
        pnlFondo = new JPanel();
        pnlFondo.setPreferredSize(new Dimension(580, 536));
        pnlFondo.setLayout(new GridBagLayout());
        pnlFondo.setOpaque(false);
        
        double cantidadElementos = cafes.size();
        double numeroFilas = Math.ceil(cantidadElementos/3);
        double paddingTotal = numeroFilas*10;
        double largoTotal = (240*numeroFilas) + paddingTotal;

        pnlCafes = new JPanel();
        pnlCafes.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //570 de scrCafes menos 16 aprox del tamaño de la barra de scroll
        pnlCafes.setPreferredSize(new Dimension(554, (int) Math.ceil(largoTotal)));
        pnlCafes.setMaximumSize(new Dimension(554, (int) Math.ceil(largoTotal)));
        pnlCafes.setOpaque(false);
        
        scrCafes = new JScrollPane(pnlCafes);
        scrCafes.getVerticalScrollBar().setUI(new BarraDesplazamiento());
        scrCafes.getVerticalScrollBar().setOpaque(false);
        scrCafes.getVerticalScrollBar().setCursor(new Cursor(Cursor.HAND_CURSOR));

        scrCafes.setOpaque(false);
        scrCafes.getViewport().setOpaque(false);
        scrCafes.setPreferredSize(new Dimension(570, 526));
        scrCafes.setBorder(null);
        scrCafes.getVerticalScrollBar().setUnitIncrement(16);
        scrCafes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrCafes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        pnlFondo.add(scrCafes);
        add(pnlFondo);
    }

    void crearTarjetaCafe(){
        for(Cafe cafe : cafes){
            PanelRedondoSombreado cafePanel = new PanelRedondoSombreado(30, Color.WHITE);
            cafePanel.setLayout(new GridBagLayout());
            cafePanel.setPreferredSize(new Dimension(170, 240));
            
            JPanel panelElementos = new JPanel();
            panelElementos.setOpaque(false);
            panelElementos.setLayout(new BoxLayout(panelElementos, BoxLayout.Y_AXIS));
            
            JLabel lblCafeNombre = new JLabel(cafe.getNombre());
            lblCafeNombre.setAlignmentX(CENTER_ALIGNMENT);
            ImageIcon cafeImagen = new ImageIcon(getClass().getResource("/"+cafe.getImagen()));
            JLabel lblCafeImagen = new JLabel(cafeImagen);
            lblCafeImagen.setAlignmentX(CENTER_ALIGNMENT);
            panelElementos.add(lblCafeImagen);
            panelElementos.add(Box.createRigidArea(new Dimension(0, 15)));
            panelElementos.add(lblCafeNombre);
            
            cafePanel.add(panelElementos);

            JPanel panelTamanios = new JPanel();
            panelTamanios.setLayout(new GridBagLayout());
            panelTamanios.setOpaque(false);
            
            panelTamanios.add(configurarBotonesTamanios(cafe));
            panelElementos.add(Box.createRigidArea(new Dimension(0, 15)));
            panelElementos.add(panelTamanios);
            
            pnlCafes.add(cafePanel);
        }
        pnlCafes.revalidate();
        pnlCafes.repaint();
    }

    JPanel configurarBotonesTamanios(Cafe cafe){
        JPanel panelTamaniosContenedor = new JPanel();
        panelTamaniosContenedor.setOpaque(false);
        
        JLabel lblChico = new JLabel("CH");
        lblChico.setFont(lblChico.getFont().deriveFont(14f));
        lblChico.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblChico.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(!inicio.verificarContenido()){
                    inicio.configurarPanelOrden();
                }
                try {
                    for(Component contenedor : inicio.elementosDeCentro()){
                        if(contenedor instanceof JPanel){
                            JPanel existente = (JPanel) contenedor;
                            Producto producto = (Producto) existente.getClientProperty("producto");
                            AtomicInteger cantidad = (AtomicInteger) existente.getClientProperty("cantidad");
                            JLabel label = (JLabel) existente.getClientProperty("label");
                            String tamanio = (String) existente.getClientProperty("tamaño");
                            if(cafe.getId() == producto.getId() && tamanio.equals("CH")){
                                label.setText(String.valueOf(cantidad.incrementAndGet()));
                                return;
                            }
                        }
                    }
                    inicio.agregarProductoEnOrden(cafe, "CH");
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        
        JLabel lblMediano = new JLabel("M");
        lblMediano.setFont(lblMediano.getFont().deriveFont(14f));
        lblMediano.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblMediano.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    if(!inicio.verificarContenido()){
                        inicio.configurarPanelOrden();
                    }
                    for(Component contenedor : inicio.elementosDeCentro()){
                        if(contenedor instanceof JPanel){
                            JPanel existente = (JPanel) contenedor;
                            Producto producto = (Producto) existente.getClientProperty("producto");
                            AtomicInteger cantidad = (AtomicInteger) existente.getClientProperty("cantidad");
                            JLabel label = (JLabel) existente.getClientProperty("label");
                            String tamanio = (String) existente.getClientProperty("tamaño");
                            if(cafe.getId() == producto.getId() && tamanio.equals("M")){
                                label.setText(String.valueOf(cantidad.incrementAndGet()));
                                return;
                            }
                        }
                    }
                    inicio.agregarProductoEnOrden(cafe, "M");
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        
        JLabel lblGrande = new JLabel("G");
        lblGrande.setFont(lblGrande.getFont().deriveFont(14f));
        lblGrande.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblGrande.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    if(!inicio.verificarContenido()){
                        inicio.configurarPanelOrden();
                    }
                    for(Component contenedor : inicio.elementosDeCentro()){
                        if(contenedor instanceof JPanel){
                            JPanel existente = (JPanel) contenedor;
                            Producto producto = (Producto) existente.getClientProperty("producto");
                            AtomicInteger cantidad = (AtomicInteger) existente.getClientProperty("cantidad");
                            JLabel label = (JLabel) existente.getClientProperty("label");
                            String tamanio = (String) existente.getClientProperty("tamaño");
                            if(cafe.getId() == producto.getId() && tamanio.equals("G")){
                                label.setText(String.valueOf(cantidad.incrementAndGet()));
                                return;
                            }
                        }
                    }
                    inicio.agregarProductoEnOrden(cafe, "G");
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        
        panelTamaniosContenedor.add(lblChico);
        panelTamaniosContenedor.add(lblMediano);
        panelTamaniosContenedor.add(lblGrande);
        return panelTamaniosContenedor;
    }
}
