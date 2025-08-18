package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import componentes.BarraDesplazamiento;
import componentes.PanelRedondo;
import componentes.PanelRedondoSombreado;
import entidades.Cafe;
import entidades.Postre;
import entidades.Producto;

public class Orden extends JPanel{
    private PanelRedondo roundedPanel;
    private JPanel centro;
    private JScrollPane scrCentro;
    private JLabel lblArriba;
    private JLabel lblAbajo;
    private Inicio inicio;

    public Orden(Inicio inicio){
        this.inicio = inicio;
        crearOrdenPanel();
        configurarOrdenPanel();
        carritoVacio();
        add(roundedPanel);
    }

    void crearOrdenPanel(){
        setPreferredSize(new Dimension(202, 556));
        setLayout(null);
        setBounds(0, 0, 202, 556);
        setOpaque(false);
        roundedPanel = new PanelRedondo(30, new Color(109, 74, 49));
        roundedPanel.setBounds(0, 0, 202, 556);
    }

    void carritoVacio(){
        roundedPanel.removeAll();
        roundedPanel.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ImageIcon logoCarrito = new ImageIcon(getClass().getResource("/carrito.png"));
        JLabel lblLogoCarrito = new JLabel(logoCarrito);
        lblLogoCarrito.setAlignmentX(CENTER_ALIGNMENT);
        JLabel lblCarrito = new JLabel("No hay órden en progreso.");
        lblCarrito.setAlignmentX(CENTER_ALIGNMENT);
        lblCarrito.setForeground(Color.WHITE);
        panel.add(lblLogoCarrito);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblCarrito);
        roundedPanel.add(panel);
        roundedPanel.revalidate();
        roundedPanel.repaint();
    }

    public void agregarProducto(Producto producto, String tamanio) throws SQLException{
        AtomicInteger cantidad = new AtomicInteger(1);
        PanelRedondoSombreado productoPanel = new PanelRedondoSombreado(30, new Color(109, 74, 49));
        productoPanel.setAlignmentX(CENTER_ALIGNMENT);
        productoPanel.setLayout(new BoxLayout(productoPanel, BoxLayout.Y_AXIS));
        productoPanel.setPreferredSize(new Dimension(170,170));
        productoPanel.setMinimumSize(new Dimension(170,170));
        productoPanel.setMaximumSize(new Dimension(170,170));
        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);
        lblNombre.setForeground(Color.WHITE);
        productoPanel.add(lblNombre);
        
        JLabel lblPrecio = new JLabel();
        if(producto instanceof Cafe){
            if(tamanio.equals("CH")){
                JLabel lblChico = new JLabel("Café chico");
                lblChico.setForeground(Color.WHITE);
                lblChico.setAlignmentX(CENTER_ALIGNMENT);
                productoPanel.add(lblChico);
                double precio = inicio.obtenerPrecioCafe(producto.getId(), 1);
                lblPrecio = new JLabel("$ "+String.valueOf(precio));
                lblPrecio.setAlignmentX(CENTER_ALIGNMENT);
                lblPrecio.setForeground(Color.WHITE);
            }
            else if(tamanio.equals("M")){
                JLabel lblMediano = new JLabel("Café mediano");
                lblMediano.setForeground(Color.WHITE);
                lblMediano.setAlignmentX(CENTER_ALIGNMENT);
                productoPanel.add(lblMediano);
                double precio = inicio.obtenerPrecioCafe(producto.getId(), 2);
                lblPrecio = new JLabel("$ "+String.valueOf(precio));
                lblPrecio.setAlignmentX(CENTER_ALIGNMENT);
                lblPrecio.setForeground(Color.WHITE);
            }
            else if(tamanio.equals("G")){
                JLabel lblGrande = new JLabel("Café grande");
                lblGrande.setForeground(Color.WHITE);
                lblGrande.setAlignmentX(CENTER_ALIGNMENT);
                productoPanel.add(lblGrande);
                double precio = inicio.obtenerPrecioCafe(producto.getId(), 3);
                lblPrecio = new JLabel("$ "+String.valueOf(precio));
                lblPrecio.setAlignmentX(CENTER_ALIGNMENT);
                lblPrecio.setForeground(Color.WHITE);
            }
        }

        if(producto instanceof Postre){
            double precio = inicio.obtenerPrecioPostre(producto.getId());
            lblPrecio = new JLabel("$ " +String.valueOf(precio));
            lblPrecio.setAlignmentX(CENTER_ALIGNMENT);
            lblPrecio.setForeground(Color.WHITE);
        }
        
        ImageIcon imgProducto = new ImageIcon(getClass().getResource("/"+producto.getImagen()));
        Image imagenEscalada = imgProducto.getImage().getScaledInstance(44, 44, Image.SCALE_SMOOTH);
        ImageIcon imgProductoFinal = new ImageIcon(imagenEscalada);
        JLabel lblProductoImagen = new JLabel(imgProductoFinal);
        lblProductoImagen.setAlignmentX(CENTER_ALIGNMENT);
        productoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        productoPanel.add(lblProductoImagen);
        productoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        productoPanel.add(lblPrecio);
        productoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(0, 0, 10, 0));
        contenedor.setPreferredSize(new Dimension(170,170));
        contenedor.setMinimumSize(new Dimension(170,170));
        contenedor.setMaximumSize(new Dimension(170,170));
        contenedor.putClientProperty("producto", producto);
        contenedor.putClientProperty("cantidad", cantidad);
        JPanel panelDeCantidades = configurarElementosCantidades(contenedor, cantidad);
        productoPanel.add(panelDeCantidades);
        contenedor.putClientProperty("label", panelDeCantidades.getClientProperty("label"));
        if(producto instanceof Cafe){
            contenedor.putClientProperty("tamaño", tamanio);
        }
        contenedor.add(productoPanel, BorderLayout.CENTER);
        centro.add(contenedor);
        centro.revalidate();
        centro.repaint();
    }

    void configurarOrdenPanel(){
        roundedPanel.removeAll();
        roundedPanel.setLayout(new BoxLayout(roundedPanel, BoxLayout.Y_AXIS));
        roundedPanel.setBorder(new EmptyBorder(5,5,5,5));
        ImageIcon imgArriba = new ImageIcon(getClass().getResource("/arriba.png"));
        lblArriba = new JLabel(imgArriba);
        lblArriba.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblArriba.setAlignmentX(CENTER_ALIGNMENT);
        JPanel arriba = new JPanel();
        arriba.setLayout(new GridBagLayout());
        arriba.setPreferredSize(new Dimension(202, 64));
        arriba.setOpaque(false);
        arriba.add(lblArriba);

        centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setOpaque(false);
        centro.addContainerListener(new ContainerListener() {
            @Override
            public void componentAdded(ContainerEvent e){
                if(centro.getComponentCount() == 1){
                    inicio.mostrarPopupOrdenEnProgreso();
                }
            }
            @Override
            public void componentRemoved(ContainerEvent e){
                if(!verificarContenido()){
                    inicio.cerrarPopupOrden();
                    carritoVacio();
                }
            }
        });
        scrCentro = new JScrollPane(centro);
        scrCentro.getVerticalScrollBar().setUnitIncrement(16);
        scrCentro.getVerticalScrollBar().setUI(new BarraDesplazamiento());
        scrCentro.setOpaque(false);
        scrCentro.getViewport().setOpaque(false);
        scrCentro.setPreferredSize(new Dimension(202, 428));
        scrCentro.setBorder(null);
        scrCentro.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrCentro.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        ImageIcon imgAbajo = new ImageIcon(getClass().getResource("/abajo.png"));
        lblAbajo = new JLabel(imgAbajo);
        lblAbajo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblAbajo.setAlignmentX(CENTER_ALIGNMENT);
        JPanel abajo = new JPanel();
        abajo.setLayout(new GridBagLayout());
        abajo.setPreferredSize(new Dimension(202, 64));
        abajo.setOpaque(false);
        abajo.add(lblAbajo);
        roundedPanel.add(arriba);
        roundedPanel.add(scrCentro);
        roundedPanel.add(abajo);
        configurarScrollMovimiento();
        roundedPanel.revalidate();
        roundedPanel.repaint();
    }

    boolean verificarContenido(){
        for(Component comp : centro.getComponents()){
            if(comp instanceof JPanel){
                return true;
            }
        }
        return false;
    }

    public void configurarScrollMovimiento(){
        lblArriba.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JScrollBar barra = scrCentro.getVerticalScrollBar();
                int paso = 60;
                barra.setValue(barra.getValue() - paso);
            }
        });

        lblAbajo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JScrollBar barra = scrCentro.getVerticalScrollBar();
                int paso = 60;
                barra.setValue(barra.getValue() + paso);
            }
        });
    }

    public JPanel configurarElementosCantidades(JPanel contenedor, AtomicInteger cantidad){
        JLabel lblCantidad = new JLabel(String.valueOf(cantidad));
        lblCantidad.setForeground(Color.WHITE);

        ImageIcon imgMas = new ImageIcon(getClass().getResource("/mas1.png"));
        JLabel lblMas = new JLabel(imgMas);
        lblMas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblMas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                lblCantidad.setText(String.valueOf(cantidad.incrementAndGet()));
            }
        });
        
        ImageIcon imgMenos = new ImageIcon(getClass().getResource("/menos1.png"));
        JLabel lblMenos = new JLabel(imgMenos);
        lblMenos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblMenos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(cantidad.get() > 1){
                    lblCantidad.setText(String.valueOf(cantidad.decrementAndGet()));
                }
                else{
                    centro.remove(contenedor);
                    centro.revalidate();
                    centro.repaint();
                }
            }
        });
        
        JPanel pnlCantidad = new JPanel();
        pnlCantidad.setOpaque(false);
        pnlCantidad.add(lblMenos);
        pnlCantidad.add(Box.createRigidArea(new Dimension(3,0)));
        pnlCantidad.add(lblCantidad);
        pnlCantidad.add(Box.createRigidArea(new Dimension(3,0)));
        pnlCantidad.add(lblMas);
        pnlCantidad.putClientProperty("label", lblCantidad);
        return pnlCantidad;
    }

    public Component[] elementosDeCentro(){
        return centro.getComponents();
    }

    public void eliminarProductosDeOrden(){
        centro.removeAll();
        centro.revalidate();
        centro.repaint();
    }
}
