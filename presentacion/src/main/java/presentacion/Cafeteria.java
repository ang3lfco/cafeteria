package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import componentes.RoundedPanel;
import componentes.ShadowRoundedPanel;
import conexion.ConexionBD;
import daos.OrdenDAO;
import daos.OrdenProductoDAO;
import daos.PostreDAO;
import daos.ProductoDAO;
import daos.TamanioCafeDAO;
import entidades.Orden;
import entidades.OrdenProducto;
import entidades.Producto;
import interfaces.IOrdenDAO;
import interfaces.IOrdenNegocio;
import interfaces.IOrdenProductoDAO;
import interfaces.IOrdenProductoNegocio;
import interfaces.IPostreDAO;
import interfaces.IPostreNegocio;
import interfaces.IProductoDAO;
import interfaces.IProductoNegocio;
import interfaces.ITamanioCafeDAO;
import interfaces.ITamanioCafeNegocio;
import negocio.OrdenNegocio;
import negocio.OrdenProductoNegocio;
import negocio.PostreNegocio;
import negocio.ProductoNegocio;
import negocio.TamanioCafeNegocio;

public class Cafeteria extends JFrame {
    
    //conexion y daos
    private ConexionBD conexion;
    private IProductoDAO productoDAO;
    private IPostreDAO postreDAO;
    private IOrdenProductoDAO ordenProductoDAO;
    private IOrdenDAO ordenDAO;
    private ITamanioCafeDAO tamanioCafeDAO;

    //negocio
    private IProductoNegocio productoNegocio;
    private IPostreNegocio postreNegocio;
    private IOrdenProductoNegocio ordenProductoNegocio;
    private IOrdenNegocio ordenNegocio;
    private ITamanioCafeNegocio tamanioCafeNegocio;

    //listas iniciales
    private List<Producto> productos = new ArrayList<>();
    private List<Producto> cafes = new ArrayList<>();
    private List<Producto> postres = new ArrayList<>();
    
    //componentes
    private JPanel panelOrdenes;
    private double pagoTotal = 0.0;
    private JLabel lblPagoTotal = new JLabel("Total: $ " + pagoTotal);
    private RoundedPanel opcion1;
    private RoundedPanel opcion2;
    private RoundedPanel opcion3;
    private JPanel panelMenu;
    private JPanel panelProductos;
    private JLabel tituloMenu;
    private RoundedPanel panelPrincipal;
    private RoundedPanel ladoDerecho;
    private JPanel ladoIzquierdo;
    private JScrollPane scrollPaneProductos;
    private JScrollPane scrollPaneOrdenes;
    private JPanel panelOrdenar;
    private JPanel panelTituloMenu;
    private RoundedPanel btnOrdenar;
    private JPanel ladoDerechoCompleto;
    private JPanel cuenta;
    private JLabel lblOrdenar;
    
    //dimensiones de los componentes
    private int anchoContenedor = 1024 - 20;
    private int anchoLadoIzquierdo = (int)(anchoContenedor*0.7);
    private int anchoLadoDerecho = (int)(anchoContenedor*0.3);
    private int alturaMenuOpciones = 50;
    private int alturaPanelProductos = 576 - alturaMenuOpciones;
    private int productosPorFila = anchoLadoIzquierdo / 210;
    private int filas = (int) Math.ceil(11 / (double) productosPorFila);
    private int alturaTotal = filas * 280;
    private int opcionMenuActiva = 0;

    private int cantidadProductosNueva = productos.size();
    private int filasNuevas = (int) Math.ceil(cantidadProductosNueva / (double) productosPorFila);
    int alturaTotalNueva = filasNuevas * 280;

    public Cafeteria() throws SQLException {

        //configuracion inicial
        setUndecorated(true);
        setBackground(new Color(0,0,0, 0));
        setTitle("Cafeteria");
        setSize(1024, 576);
        setLocationRelativeTo(null);

        //inicializacion de dependencias
        iniciarConexiones();
        
        //inicializacion de componentes y datos
        crearPantallaPrincipal();
        crearPanelProductos();
        configurarMenu();
        configurarPanelOrdenes();
        configurarContenedorIzquierdo();
        configurarContenedorDerecho();
        actualizarVistaPagoTotal();
        iniciarDatos();

        cantidadProductosNueva = productos.size();
        filasNuevas = (int) Math.ceil(cantidadProductosNueva / (double) productosPorFila);
        alturaTotalNueva = filasNuevas * 280;
    }

    private void iniciarDatos() throws SQLException{
        productos = productoNegocio.obtenerProductos();
        for(Producto p : productos){
            if(p.getIdCafe() != 0){
                cafes.add(p);
            }
            if(p.getIdPostre() != 0){
                postres.add(p);
            }
        }
        for(Producto cafe : cafes){
            panelProductos.add(crearCafe(cafe));
        }
    }

    private void crearPanelProductos(){
        panelProductos = new JPanel();
        panelProductos.setOpaque(false);
        panelProductos.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelProductos.setPreferredSize(new Dimension(anchoLadoIzquierdo, alturaTotal));
        
        scrollPaneProductos = new JScrollPane(panelProductos);
        scrollPaneProductos.setOpaque(false);
        scrollPaneProductos.getViewport().setOpaque(false);
        scrollPaneProductos.setBorder(null);
        scrollPaneProductos.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneProductos.setPreferredSize(new Dimension(anchoLadoIzquierdo, alturaPanelProductos));
        scrollPaneProductos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneProductos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneProductos.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
    }

    private void crearPantallaPrincipal(){
        panelPrincipal = new RoundedPanel(30, new Color(245,245,245));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.X_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(panelPrincipal);
    }

    private void configurarContenedorDerecho(){
        ladoDerecho = new RoundedPanel(30, new Color(109, 74, 49));
        ladoDerecho.setOpaque(false);
        
        ladoDerecho.setPreferredSize(new Dimension(anchoLadoDerecho, 576));
        ladoDerecho.setMaximumSize(new Dimension(anchoLadoDerecho, 567));
        ladoDerecho.setLayout(new BoxLayout(ladoDerecho, BoxLayout.Y_AXIS));

        ladoDerecho.add(Box.createRigidArea(new Dimension(0, 10)));
        ladoDerecho.add(scrollPaneOrdenes);
        ladoDerecho.add(cuenta);
        ladoDerecho.add(panelOrdenar);

        ladoDerechoCompleto = new JPanel();
        ladoDerechoCompleto.setOpaque(false);
        ladoDerechoCompleto.setLayout(new BoxLayout(ladoDerechoCompleto, BoxLayout.Y_AXIS));
        ladoDerechoCompleto.add(ladoDerecho);
        
        panelPrincipal.add(ladoDerechoCompleto);
    }

    private void configurarContenedorIzquierdo(){
        ladoIzquierdo = new JPanel();
        ladoIzquierdo.setLayout(new BoxLayout(ladoIzquierdo, BoxLayout.Y_AXIS));
        ladoIzquierdo.setOpaque(false);
        ladoIzquierdo.setPreferredSize(new Dimension(anchoLadoIzquierdo, 576));
        
        ladoIzquierdo.add(panelMenu);
        ladoIzquierdo.add(panelTituloMenu);
        ladoIzquierdo.add(scrollPaneProductos);

        panelPrincipal.add(ladoIzquierdo);
    }

    private void configurarPanelOrdenes(){
        panelOrdenes = new JPanel();
        panelOrdenes.setLayout(new BoxLayout(panelOrdenes, BoxLayout.Y_AXIS));
        panelOrdenes.setBorder(new EmptyBorder(10,10,10,10));
        scrollPaneOrdenes = new JScrollPane(panelOrdenes);
        scrollPaneOrdenes.setOpaque(false);
        scrollPaneOrdenes.getViewport().setOpaque(false);
        scrollPaneOrdenes.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        scrollPaneOrdenes.setBorder(null);
        scrollPaneOrdenes.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneOrdenes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelOrdenes.setPreferredSize(new Dimension(anchoLadoDerecho, alturaTotal - 100));
        panelOrdenes.setOpaque(false);
        panelOrdenar = new JPanel();
        panelOrdenar.setOpaque(false);
        // panelOrdenar.setBackground(Color.CYAN);
        panelOrdenar.setPreferredSize(new Dimension(anchoLadoDerecho, alturaMenuOpciones));
        panelOrdenar.setMinimumSize(new Dimension(anchoLadoDerecho, alturaMenuOpciones));
        panelOrdenar.setLayout(new BoxLayout(panelOrdenar, BoxLayout.X_AXIS));
        panelOrdenar.setBorder(new EmptyBorder(0,10,10,10));
        
        cuenta = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // cuenta.setBackground(Color.YELLOW);
        cuenta.setBorder(new EmptyBorder(10,10,10,10));
        cuenta.setOpaque(false);
        // cuenta.setBackground(Color.BLACK);
        lblPagoTotal.setForeground(Color.WHITE);
        lblPagoTotal.setFont(new Font(getName(), Font.BOLD, 15));
        cuenta.add(lblPagoTotal);
        btnOrdenar = new RoundedPanel(30, Color.WHITE);
        btnOrdenar.setLayout(new GridBagLayout());
        lblOrdenar = new JLabel("Ordenar");
        lblOrdenar.setFont(new Font(getName(), Font.BOLD, 15));
        lblOrdenar.setForeground(Color.BLACK);
        btnOrdenar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOrdenar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                int idOrden = 0;
                if(panelOrdenes.getComponentCount() != 0){
                    try{
                        idOrden = ordenNegocio.registrarOrden(new Orden(
                            LocalDateTime.now()
                        ));
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null,"Error." + ex.getStackTrace());
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Selecciona productos para realizar un pedido.");
                }
                JOptionPane.showMessageDialog(null, "Orden: " +idOrden);
                if(idOrden > 0){
                    for(Component comp : panelOrdenes.getComponents()){
                        if(comp instanceof JPanel){
                            JPanel ordenIn = (JPanel) comp;
                            Producto producto = (Producto) ordenIn.getClientProperty("producto");
                            AtomicInteger cant = (AtomicInteger) ordenIn.getClientProperty("contador");
                            int cantidad = cant.get();
                            String tamanio = (String) ordenIn.getClientProperty("tamaño");
                            try{
                                ordenProductoNegocio.registrarOrdenProducto(new OrdenProducto(
                                    idOrden,
                                    producto.getId(),
                                    cantidad,
                                    tamanio
                                ));
                            }
                            catch(SQLException ex){
                                JOptionPane.showMessageDialog(null, "Error." + ex.getStackTrace());
                            }
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error.");
                    panelOrdenes.removeAll();
                    panelOrdenes.revalidate();
                    panelOrdenes.repaint();
                }
                pagoTotal = 0.0;
                lblPagoTotal.setText("Total: $ " + pagoTotal);
                panelOrdenes.removeAll();
                panelOrdenes.revalidate();
                panelOrdenes.repaint();
                actualizarVistaPagoTotal();
            }
        });
        btnOrdenar.add(lblOrdenar);
        panelOrdenar.add(btnOrdenar);
    }

    private void configurarMenu(){
        
        opcion1 = new RoundedPanel(30, new Color(109, 74, 49));
        opcion2 = new RoundedPanel(30, new Color(109, 74, 49));
        opcion3 = new RoundedPanel(30, new Color(109, 74, 49));
        
        panelMenu = new JPanel();
        panelMenu.setBorder(new EmptyBorder(0,5,20,10));
        panelMenu.setOpaque(false);
        panelMenu.setPreferredSize(new Dimension(anchoLadoIzquierdo, alturaMenuOpciones));
        panelMenu.setMaximumSize(new Dimension(anchoLadoIzquierdo, alturaMenuOpciones));
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.X_AXIS));

        tituloMenu = new JLabel("Menú de Cafés");
        tituloMenu.setFont(new Font(getName(), Font.BOLD, 16));
        tituloMenu.setBorder(new EmptyBorder(10,10,10,10));
        tituloMenu.setHorizontalAlignment(SwingConstants.LEFT);
        tituloMenu.setVerticalAlignment(SwingConstants.CENTER);
        
        panelTituloMenu = new JPanel(new BorderLayout());
        panelTituloMenu.setOpaque(false);
        panelTituloMenu.setBorder(new EmptyBorder(0,10,0,0));
        panelTituloMenu.add(tituloMenu, BorderLayout.WEST);
        
        opcion1.setLayout(new GridBagLayout());
        JLabel label1 = new JLabel("Cafés");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font(getName(), Font.BOLD, 15));
        opcion1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        opcion1.add(label1);
        opcion1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                tituloMenu.setText("Menú de Cafés");
                panelProductos.removeAll();
                
                int totalProductos = cafes.size();
                int productosPorFila = anchoLadoIzquierdo / 210;
                int filas = (int) Math.ceil(totalProductos / (double) productosPorFila);
                int alturaTotal = filas * 280;
                panelProductos.setPreferredSize(new Dimension(anchoLadoIzquierdo, alturaTotal));

                for(Producto cafe : cafes){
                    panelProductos.add(crearCafe(cafe));
                }

                if(opcionMenuActiva != 1){
                    opcionMenuActiva = 1;
                    opcion1.setBackgroundColor(new Color(173, 114, 57));
                    opcion2.setBackgroundColor(new Color(109, 74, 49));
                    opcion3.setBackgroundColor(new Color(109, 74, 49));
                    panelMenu.revalidate();
                    panelMenu.repaint();
                }
                
                panelProductos.revalidate();
                panelProductos.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                if(opcionMenuActiva != 1){
                    opcion1.setBackgroundColor(new Color(173, 114, 57));
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                if(opcionMenuActiva != 1){
                    opcion1.setBackgroundColor(new Color(109, 74, 49));
                }
            }
        });
        
        
        opcion2.setLayout(new GridBagLayout());
        JLabel label2 = new JLabel("Postres");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font(getName(), Font.BOLD, 15));
        opcion2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        opcion2.add(label2);
        opcion2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tituloMenu.setText("Menú de Postres");
                panelProductos.removeAll();

                int totalProductos = postres.size();
                int productosPorFila = anchoLadoIzquierdo / 210;
                int filas = (int) Math.ceil(totalProductos / (double) productosPorFila);
                int alturaTotal = filas * 280;
                panelProductos.setPreferredSize(new Dimension(anchoLadoIzquierdo, alturaTotal));
                
                for(Producto postre: postres){
                    panelProductos.add(crearPostre(postre));
                }

                if(opcionMenuActiva != 2){
                    opcionMenuActiva = 2;
                    opcion1.setBackgroundColor(new Color(109, 74, 49));
                    opcion2.setBackgroundColor(new Color(173, 114, 57));
                    opcion3.setBackgroundColor(new Color(109, 74, 49));
                    panelMenu.revalidate();
                    panelMenu.repaint();
                }
                
                panelProductos.revalidate();
                panelProductos.repaint();
            }
            @Override
            public void mouseEntered(MouseEvent e){
                if(opcionMenuActiva != 2){
                    opcion2.setBackgroundColor(new Color(173, 114, 57));
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                if(opcionMenuActiva != 2){
                    opcion2.setBackgroundColor(new Color(109, 74, 49));
                }
            }
        });
        
        
        opcion3.setLayout(new GridBagLayout());
        JLabel label3 = new JLabel("Órdenes");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font(getName(), Font.BOLD, 15));
        opcion3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        opcion3.add(label3);
        opcion3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tituloMenu.setText("Ordenes recientes");
                panelProductos.removeAll();

                int ordenProductos = 0;
                try {
                    ordenProductos = ordenProductoNegocio.obtenerOrdenesProductos().size();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                int productosPorFila = anchoLadoIzquierdo / 640;
                int filas = (int) Math.ceil(ordenProductos / (double) productosPorFila);
                int alturaTotal = filas * 280;
                panelProductos.setMaximumSize(new Dimension(anchoLadoIzquierdo, alturaTotal));
                panelProductos.setPreferredSize(new Dimension(anchoLadoIzquierdo, alturaTotal));
                
                try {
                    crearPanelPedidos();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                if(opcionMenuActiva != 3){
                    opcionMenuActiva = 3;
                    opcion1.setBackgroundColor(new Color(109, 74, 49));
                    opcion2.setBackgroundColor(new Color(109, 74, 49));
                    opcion3.setBackgroundColor(new Color(173, 114, 57));
                    panelMenu.revalidate();
                    panelMenu.repaint();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e){
                if(opcionMenuActiva != 3){
                    opcion3.setBackgroundColor(new Color(173, 114, 57));
                }
            }
            @Override
            public void mouseExited(MouseEvent e){
                if(opcionMenuActiva != 3){
                    opcion3.setBackgroundColor(new Color(109, 74, 49));
                }
            }
        });
        
        panelMenu.add(opcion1);
        panelMenu.add(Box.createRigidArea(new Dimension(10, 0)));
        panelMenu.add(opcion2);
        panelMenu.add(Box.createRigidArea(new Dimension(10, 0)));
        panelMenu.add(opcion3);
    }

    public JPanel crearCafe(Producto cafe){
        ShadowRoundedPanel panelProducto = new ShadowRoundedPanel(30, Color.WHITE);
        panelProducto.setPreferredSize(new Dimension(210, 260));
        panelProducto.setBackground(Color.WHITE);
        panelProducto.setLayout(new BoxLayout(panelProducto, BoxLayout.Y_AXIS));
        panelProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon iconoCafe = new ImageIcon(getClass().getResource("/"+cafe.getImagen()));
        JLabel labelIcono = new JLabel(iconoCafe);
        labelIcono.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nombreCafe = new JLabel(cafe.getNombre());
        nombreCafe.setFont(new Font(getName(), Font.BOLD, 18));
        nombreCafe.setAlignmentX(CENTER_ALIGNMENT);

        JPanel tamañosPanel = new JPanel();
        tamañosPanel.setOpaque(false);
        tamañosPanel.setLayout(new BoxLayout(tamañosPanel, BoxLayout.X_AXIS));
        tamañosPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        tamañosPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        RoundedPanel chicoPanel = new RoundedPanel(100, new Color(109, 74, 49));
        chicoPanel.setLayout(new GridBagLayout());
        chicoPanel.setPreferredSize(new Dimension(30,Integer.MAX_VALUE));
        chicoPanel.setMaximumSize(new Dimension(30,Integer.MAX_VALUE));
        chicoPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel chicoLabel = new JLabel("CH");
        chicoLabel.setForeground(Color.WHITE);
        chicoLabel.setFont(new Font(getName(), Font.BOLD, 14));
        chicoPanel.add(chicoLabel);
        chicoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    crearOrden(cafe, "ch");
                    actualizarVistaPagoTotal();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e){
                chicoPanel.setBackgroundColor(new Color(173, 114, 57));
            }
            @Override
            public void mouseExited(MouseEvent e){
                chicoPanel.setBackgroundColor(new Color(109,74,49));
            }
        });
        
        RoundedPanel medianoPanel = new RoundedPanel(100, new Color(109, 74, 49));
        medianoPanel.setLayout(new GridBagLayout());
        medianoPanel.setPreferredSize(new Dimension(30,Integer.MAX_VALUE));
        medianoPanel.setMaximumSize(new Dimension(30,Integer.MAX_VALUE));
        medianoPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel medianoLabel = new JLabel("M");
        medianoLabel.setForeground(Color.WHITE);
        medianoLabel.setFont(new Font(getName(), Font.BOLD, 14));
        medianoPanel.add(medianoLabel);
        medianoPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    crearOrden(cafe, "m");
                    actualizarVistaPagoTotal();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e){
                medianoPanel.setBackgroundColor(new Color(173, 114, 57));
            }
            @Override
            public void mouseExited(MouseEvent e){
                medianoPanel.setBackgroundColor(new Color(109,74,49));
            }
        });
        
        RoundedPanel grandePanel = new RoundedPanel(100, new Color(109, 74, 49));
        grandePanel.setLayout(new GridBagLayout());
        grandePanel.setPreferredSize(new Dimension(30,Integer.MAX_VALUE));
        grandePanel.setMaximumSize(new Dimension(30,Integer.MAX_VALUE));
        grandePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel grandeLabel = new JLabel("G");
        grandeLabel.setForeground(Color.WHITE);
        grandeLabel.setFont(new Font(getName(), Font.BOLD, 14));
        grandePanel.add(grandeLabel);
        grandePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    crearOrden(cafe, "g");
                    actualizarVistaPagoTotal();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e){
                grandePanel.setBackgroundColor(new Color(173, 114, 57));
            }
            @Override
            public void mouseExited(MouseEvent e){
                grandePanel.setBackgroundColor(new Color(109,74,49));
            }
        });
        
        JPanel panelAuxiliar = new JPanel();
        panelAuxiliar.setOpaque(false);
        panelAuxiliar.setLayout(new BoxLayout(panelAuxiliar, BoxLayout.X_AXIS));

        panelAuxiliar.setBorder(new EmptyBorder(5,5,5,5));
        panelAuxiliar.add(chicoPanel);
        panelAuxiliar.add(Box.createRigidArea(new Dimension(5, 0)));
        panelAuxiliar.add(medianoPanel);
        panelAuxiliar.add(Box.createRigidArea(new Dimension(5, 0)));
        panelAuxiliar.add(grandePanel);
        tamañosPanel.add(Box.createHorizontalGlue());
        tamañosPanel.add(panelAuxiliar);
        tamañosPanel.add(Box.createHorizontalGlue());
        
        panelProducto.add(Box.createVerticalGlue());
        panelProducto.add(labelIcono);
        panelProducto.add(Box.createRigidArea(new Dimension(0, 10)));
        panelProducto.add(nombreCafe);
        panelProducto.add(Box.createRigidArea(new Dimension(0, 10)));
        panelProducto.add(tamañosPanel);
        panelProducto.add(Box.createVerticalGlue());
        return panelProducto;
    }

    public JPanel crearPostre(Producto postre){
        ShadowRoundedPanel panelProducto = new ShadowRoundedPanel(30, Color.WHITE);
        panelProducto.setPreferredSize(new Dimension(210, 260));
        panelProducto.setBackground(Color.WHITE);
        panelProducto.setLayout(new BoxLayout(panelProducto, BoxLayout.Y_AXIS));
        panelProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelProducto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try {
                    crearOrden(postre, null);
                    actualizarVistaPagoTotal();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        ImageIcon iconoCafe = new ImageIcon(getClass().getResource("/"+postre.getImagen()));
        JLabel labelIcono = new JLabel(iconoCafe);
        labelIcono.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nombrePostre = new JLabel(postre.getNombre());
        nombrePostre.setFont(new Font(getName(), Font.BOLD, 18));
        nombrePostre.setAlignmentX(CENTER_ALIGNMENT);
        
        panelProducto.add(Box.createVerticalGlue());
        panelProducto.add(labelIcono);
        panelProducto.add(Box.createRigidArea(new Dimension(0, 10)));
        panelProducto.add(nombrePostre);
        panelProducto.add(Box.createRigidArea(new Dimension(0, 10)));
        panelProducto.add(Box.createVerticalGlue());
        
        return panelProducto;
    }

    public void crearOrden(Producto producto, String tamaño) throws SQLException{
        for(Component comp : panelOrdenes.getComponents()){
            if(comp instanceof JPanel){
                JPanel panelExistente = (JPanel) comp;
                Producto productoExistente = (Producto) panelExistente.getClientProperty("producto");
                String tamañoExistente = (String) panelExistente.getClientProperty("tamaño");

                if(productoExistente.getId() == producto.getId() && 
                    ((tamaño == null && tamañoExistente == null) || 
                    (tamaño != null && tamaño.equals(tamañoExistente)))){

                    AtomicInteger contador = (AtomicInteger) panelExistente.getClientProperty("contador");
                    JLabel lblContador = (JLabel) panelExistente.getClientProperty("lblContador");

                    contador.incrementAndGet();
                    lblContador.setText(String.valueOf(contador.get()));

                    double precio = (double) panelExistente.getClientProperty("precio");
                    pagoTotal += precio;
                    lblPagoTotal.setText("Total: $ " + pagoTotal);
                    return;
                }
            }
        }

        AtomicInteger i = new AtomicInteger(1);
        JPanel panelIcono = new JPanel(new GridBagLayout());
        panelIcono.setOpaque(false);
        JPanel panelInformacion = new JPanel();
        panelInformacion.setOpaque(false);
        panelInformacion.setLayout(new BoxLayout(panelInformacion, BoxLayout.Y_AXIS));
   
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        JLabel lblContador = new JLabel(String.valueOf(i));
        lblContador.setFont(new Font(getName(), Font.BOLD, 16));
        lblContador.setForeground(Color.WHITE);
        JPanel contador = new JPanel();
        contador.setOpaque(false);
        contador.setLayout(new GridBagLayout());
        contador.setPreferredSize(new Dimension(50, 20));
        contador.setMaximumSize(new Dimension(50, 20));
        contador.add(lblContador);

        ShadowRoundedPanel ordenIndividual = new ShadowRoundedPanel(30, new Color(109, 74, 49));
        ordenIndividual.setLayout(new BoxLayout(ordenIndividual, BoxLayout.X_AXIS));
        ordenIndividual.setBorder(new EmptyBorder(10,10,10,10));
        ordenIndividual.setOpaque(false);
        ordenIndividual.setPreferredSize(new Dimension(250, 120));
        ordenIndividual.setMaximumSize(new Dimension(250,120));

        JLabel tamañoProducto = new JLabel();
        tamañoProducto.setFont(new Font(getName(), Font.BOLD, 14));
        tamañoProducto.setForeground(Color.WHITE);
        double precio;
        JLabel lblPrecio = new JLabel();
        if(tamaño != null && !tamaño.isEmpty()){
            int tamañoCafe = 0;
            switch(tamaño.toLowerCase()){
                case "ch" : 
                    tamañoProducto.setText("Café chico");
                    tamañoCafe = 1;
                    break;
                case "m" : 
                    tamañoProducto.setText("Café mediano");
                    tamañoCafe = 2;
                    break;
                case "g" : 
                    tamañoProducto.setText("Café grande");
                    tamañoCafe = 3;
                    break;
            }
            precio = tamanioCafeNegocio.obtenerPrecioCafe(producto.getIdCafe(), tamañoCafe);
        }
        else{
            precio = postreNegocio.obtenerPrecio(producto.getIdPostre());
        }

        JLabel nombreProducto = new JLabel("<html><div style='text-align: left; width: 100px;'>" + producto.getNombre() + "</div></html>");
        nombreProducto.setForeground(Color.WHITE);
        nombreProducto.setFont(new Font(getName(), Font.BOLD, 20));
        panelInformacion.add(nombreProducto);

        lblPrecio = new JLabel("$ "+precio);
        lblPrecio.setForeground(Color.WHITE);
        lblPrecio.setFont(new Font(getName(), Font.BOLD, 14));
        pagoTotal += precio;
        lblPagoTotal.setText("Total: $ " + pagoTotal);

        panelInformacion.add(tamañoProducto);
        panelInformacion.add(lblPrecio);

        ImageIcon masImagen = new ImageIcon(getClass().getResource("/mas1.png"));
        JLabel mas = new JLabel(masImagen);
        mas.setAlignmentX(CENTER_ALIGNMENT);
        mas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        mas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                lblContador.setText(String.valueOf(i.incrementAndGet()));
                pagoTotal += precio;
                lblPagoTotal.setText("Total: $ " + pagoTotal);
                actualizarVistaPagoTotal();
            }
        });
        
        ImageIcon menosImagen = new ImageIcon(getClass().getResource("/menos1.png"));
        JLabel menos = new JLabel(menosImagen);
        menos.setAlignmentX(CENTER_ALIGNMENT);
        menos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                if(i.get() > 1){
                    lblContador.setText(String.valueOf(i.decrementAndGet()));
                    pagoTotal -= precio;
                    lblPagoTotal.setText("Total: $ " + pagoTotal);
                }
                else{
                    pagoTotal -= precio;
                    lblPagoTotal.setText("Total: $ " + pagoTotal);
                    panelOrdenes.remove(ordenIndividual);
                    panelOrdenes.revalidate();
                    panelOrdenes.repaint();
                }
                actualizarVistaPagoTotal();
            }
        });
        
        panelBotones.add(mas);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 5)));
        panelBotones.add(contador);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 5)));
        panelBotones.add(menos);

        ImageIcon iconoOrden = new ImageIcon(getClass().getResource("/"+producto.getImagen()));
        Image imagenEscalada = iconoOrden.getImage().getScaledInstance(35, ((35*iconoOrden.getIconHeight())/iconoOrden.getIconWidth()), Image.SCALE_SMOOTH);
        ImageIcon nuevaImagen = new ImageIcon(imagenEscalada);
        JLabel cafeOrdenIcono = new JLabel(nuevaImagen);
        
        panelIcono.add(cafeOrdenIcono);
        ordenIndividual.putClientProperty("producto", producto);
        ordenIndividual.putClientProperty("tamaño", tamaño);
        ordenIndividual.putClientProperty("precio", precio);
        ordenIndividual.putClientProperty("contador", i);
        ordenIndividual.putClientProperty("lblContador", lblContador);
        ordenIndividual.add(panelIcono);
        ordenIndividual.add(Box.createRigidArea(new Dimension(10, 0)));
        ordenIndividual.add(panelInformacion);
        ordenIndividual.add(panelBotones);
        
        panelOrdenes.add(ordenIndividual);
        panelOrdenes.revalidate();
        panelOrdenes.repaint();
    }

    private void actualizarVistaPagoTotal(){
        if(panelOrdenes.getComponentCount() == 0) {
            lblPagoTotal.setVisible(false);
        }
        else{
            lblPagoTotal.setVisible(true);
        }
    }

    private void crearPanelPedidos() throws SQLException{
        ShadowRoundedPanel cabezera = new ShadowRoundedPanel(20, Color.WHITE);
        cabezera.setPreferredSize(new Dimension(650, 40));
        cabezera.setLayout(new GridLayout(1, 5));
        cabezera.setBorder(new EmptyBorder(10,10,10,10));
        panelProductos.add(cabezera);

        JPanel cabezera1 = new JPanel();
        cabezera1.setOpaque(false);
        cabezera1.setLayout(new BoxLayout(cabezera1, BoxLayout.Y_AXIS));
        JPanel cabezera2 = new JPanel();
        cabezera2.setOpaque(false);
        cabezera2.setLayout(new BoxLayout(cabezera2, BoxLayout.Y_AXIS));
        JPanel cabezera3 = new JPanel();
        cabezera3.setOpaque(false);
        cabezera3.setLayout(new BoxLayout(cabezera3, BoxLayout.Y_AXIS));
        JPanel cabezera4 = new JPanel();
        cabezera4.setOpaque(false);
        cabezera4.setLayout(new BoxLayout(cabezera4, BoxLayout.Y_AXIS));
        JPanel cabezera5 = new JPanel();
        cabezera5.setOpaque(false);
        cabezera5.setLayout(new BoxLayout(cabezera5, BoxLayout.Y_AXIS));

        JLabel lblOrden = new JLabel("Orden");
        lblOrden.setAlignmentX(CENTER_ALIGNMENT);
        JLabel lblRegistro = new JLabel("Fecha y hora");
        lblRegistro.setAlignmentX(CENTER_ALIGNMENT);
        JLabel lblEstadoTitulo = new JLabel("Estado");
        lblEstadoTitulo.setAlignmentX(CENTER_ALIGNMENT);
        JLabel lblPedido = new JLabel("Productos");
        lblPedido.setAlignmentX(CENTER_ALIGNMENT);
        
        cabezera1.add(Box.createVerticalGlue());
        cabezera1.add(lblOrden);
        cabezera1.add(Box.createVerticalGlue());

        cabezera2.add(Box.createVerticalGlue());
        cabezera2.add(lblRegistro);
        cabezera2.add(Box.createVerticalGlue());

        cabezera3.add(Box.createVerticalGlue());
        cabezera3.add(lblEstadoTitulo);
        cabezera3.add(Box.createVerticalGlue());
        
        cabezera4.add(Box.createVerticalGlue());
        cabezera4.add(lblPedido);
        cabezera4.add(Box.createVerticalGlue());

        cabezera.add(cabezera1);
        cabezera.add(cabezera2);
        cabezera.add(cabezera3);
        cabezera.add(cabezera4);
        cabezera.add(cabezera5);

        List<Orden> ultimas10Ordenes = ordenNegocio.obtenerOrdenes();
        List<OrdenProducto> ordenesProductos = ordenProductoNegocio.obtenerOrdenesProductos();
        for(Orden orden : ultimas10Ordenes){
            List<OrdenProducto> registrosEncontrados = new ArrayList<>();
            for(OrdenProducto ordenProducto : ordenesProductos){
                if(ordenProducto.getIdOrden() == orden.getId()){
                    registrosEncontrados.add(ordenProducto);
                }
            }
            List<Producto> prods = new ArrayList<>();
            for(OrdenProducto opRegistro : registrosEncontrados){
                for(Producto producto : productos){
                    if(producto.getId() == opRegistro.getIdProducto()){
                        prods.add(producto);
                    }
                }
            }
            ShadowRoundedPanel pedido = new ShadowRoundedPanel(30, Color.WHITE);
            pedido.setPreferredSize(new Dimension(650, 140));
            pedido.setLayout(new GridLayout(1, 5));
            pedido.setBorder(new EmptyBorder(10,10,10,10));

            JPanel panel1 = new JPanel();
            panel1.setBackground(new Color(173, 114, 57));
            // panel1.setBackground(Color.GREEN);
            panel1.setOpaque(false);
            panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
            JPanel panel2 = new JPanel();
            panel2.setOpaque(false);
            // panel2.setBackground(Color.YELLOW);
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            JPanel panel3 = new JPanel();
            panel3.setOpaque(false);
            // panel3.setBackground(Color.RED);
            panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
            JPanel panel4 = new JPanel();
            panel4.setOpaque(false);
            // panel4.setBackground(Color.BLUE);
            panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
            JPanel panel5 = new JPanel();
            panel5.setOpaque(false);
            // panel5.setBackground(Color.BLACK);
            panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
            
            
            JLabel lblNumeroOrden = new JLabel(String.valueOf(orden.getId()));
            lblNumeroOrden.setAlignmentX(CENTER_ALIGNMENT);
            
            
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = orden.getFechaHora().format(formato);
            JLabel lblFechaHora = new JLabel(timestamp);
            lblFechaHora.setAlignmentX(CENTER_ALIGNMENT);

            
            JLabel lblEstado = new JLabel("Completada");
            lblEstado.setAlignmentX(CENTER_ALIGNMENT);

            
            
            JLabel lblProducto1 = new JLabel(String.valueOf(prods.get(0).getNombre()));
            lblProducto1.setAlignmentX(CENTER_ALIGNMENT);
            JLabel lblProducto2 = new JLabel(
                prods.size() > 1 ? prods.get(1).getNombre() : ""
            );
            lblProducto2.setAlignmentX(CENTER_ALIGNMENT);
            
            JLabel lblOrdenCompleta = new JLabel("Ver ticket");
            lblOrdenCompleta.setBorder(new EmptyBorder(5,5,5,5));
            lblOrdenCompleta.setOpaque(true);
            lblOrdenCompleta.setBackground(Color.BLACK);
            lblOrdenCompleta.setForeground(Color.WHITE);
            lblOrdenCompleta.setAlignmentX(CENTER_ALIGNMENT);
            lblOrdenCompleta.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            panel1.add(Box.createVerticalGlue());
            panel1.add(lblNumeroOrden);
            panel1.add(Box.createVerticalGlue());
            
            panel2.add(Box.createVerticalGlue());
            panel2.add(lblFechaHora);
            panel2.add(Box.createVerticalGlue());
            
            panel3.add(Box.createVerticalGlue());
            panel3.add(lblEstado);
            panel3.add(Box.createVerticalGlue());
            
            panel4.add(Box.createVerticalGlue());
            panel4.add(lblProducto1);
            panel4.add(Box.createVerticalGlue());
            panel4.add(lblProducto2);
            panel4.add(Box.createVerticalGlue());
            panel4.add(lblOrdenCompleta);
            panel4.add(Box.createVerticalGlue());

            pedido.add(panel1);
            pedido.add(panel2);
            pedido.add(panel3);
            pedido.add(panel4);
            pedido.add(panel5);
            
            panelProductos.add(pedido);
        }
        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private void iniciarConexiones(){
        conexion = new ConexionBD();
        productoDAO = new ProductoDAO(conexion);
        postreDAO = new PostreDAO(conexion);
        tamanioCafeDAO = new TamanioCafeDAO(conexion);
        ordenProductoDAO = new OrdenProductoDAO(conexion);
        ordenDAO = new OrdenDAO(conexion);
        productoNegocio = new ProductoNegocio(productoDAO);
        postreNegocio = new PostreNegocio(postreDAO);
        tamanioCafeNegocio = new TamanioCafeNegocio(tamanioCafeDAO);
        ordenProductoNegocio = new OrdenProductoNegocio(ordenProductoDAO);
        ordenNegocio = new OrdenNegocio(ordenDAO);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Cafeteria().setVisible(true);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}
