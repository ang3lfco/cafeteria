package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import componentes.ItemOrden;
import componentes.Notificacion;
import componentes.PanelRedondo;
import componentes.PanelResumenOrden;
import entidades.Cafe;
import entidades.Postre;
import entidades.Producto;
import interfaces.ICafeNegocio;
import interfaces.IOrdenNegocio;
import interfaces.IOrdenProductoNegocio;
import interfaces.IPostreNegocio;
import interfaces.ITamanioCafeDAO;
import interfaces.ITamanioCafeNegocio;

public class Inicio extends JFrame{
    private final PanelRedondo roundedPanel;
    private final Contenedor contenedor;
    private final Menu menu;
    private final Orden orden;
    private Notificacion notificacion;

    private final ICafeNegocio cafeNegocio;
    private final IPostreNegocio postreNegocio;
    private final ITamanioCafeNegocio tamanioCafeNegocio;
    private final IOrdenNegocio ordenNegocio;
    private final IOrdenProductoNegocio ordenProductoNegocio;

    public Inicio(ICafeNegocio cafeNegocio, IPostreNegocio postreNegocio, ITamanioCafeDAO tamanioCafeDAO, ITamanioCafeNegocio tamanioCafeNegocio, IOrdenNegocio ordenNegocio, IOrdenProductoNegocio ordenProductoNegocio) throws SQLException{
        this.cafeNegocio = cafeNegocio;
        this.postreNegocio = postreNegocio;
        this.tamanioCafeNegocio = tamanioCafeNegocio;
        this.ordenNegocio = ordenNegocio;
        this.ordenProductoNegocio = ordenProductoNegocio;

        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setSize(1024, 576);
        setLocationRelativeTo(null);

        roundedPanel = new PanelRedondo(30, Color.WHITE);
        roundedPanel.setLayout(new GridBagLayout());
        contenedor = new Contenedor(new Cafes(this));
        menu = new Menu(this);
        orden = new Orden(this);
        roundedPanel.add(menu);
        roundedPanel.add(contenedor);
        roundedPanel.add(orden);
        setContentPane(roundedPanel);
    }

    public void agregarProductoEnOrden(Producto producto, String tamanio) throws SQLException{
        this.orden.agregarProducto(producto, tamanio);
    }

    public void actualizarVista(JPanel nuevaVista) throws SQLException{
        this.contenedor.cambiarVista(nuevaVista);
    }

    public List<Cafe> obtenerCafes() throws SQLException{
        return this.cafeNegocio.obtenerCafes();
    }

    public List<Postre> obtenerPostres() throws SQLException{
        return this.postreNegocio.obtenerPostres();
    }

    public double obtenerPrecioCafe(int idCafe, int idTamanio) throws SQLException{
        return this.tamanioCafeNegocio.obtenerPrecioCafe(idCafe, idTamanio);
    }

    public double obtenerPrecioPostre(int idPostre) throws SQLException{
        return this.postreNegocio.obtenerPrecio(idPostre);
    }

    public void configurarPanelOrden(){
        orden.configurarOrdenPanel();
    }

    public boolean verificarContenido(){
        return orden.verificarContenido();
    }

    public Component[] elementosDeCentro(){
        return orden.elementosDeCentro();
    }

    public void mostrarPopupOrdenEnProgreso(){
        notificacion = new Notificacion(
            getLayeredPane(),
            "Órden en progreso...",
            "Haz clic para finalizar"
        );
        notificacion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                PanelResumenOrden pro = new PanelResumenOrden(Inicio.this, obtenerProductosParaOrdenar(), ordenNegocio, ordenProductoNegocio);
                pro.setVisible(true);
            }
        });
        notificacion.mostrar();
    }

    public List<ItemOrden> obtenerProductosParaOrdenar(){
        Component[] productosEnOrden = orden.elementosDeCentro();
        List<ItemOrden> productosParaOrdenar = new ArrayList<>();
        for(Component comp : productosEnOrden){
            JPanel panelExistente = (JPanel) comp;
            Producto producto = (Producto) panelExistente.getClientProperty("producto");
            AtomicInteger cantidad = (AtomicInteger) panelExistente.getClientProperty("cantidad");
            int cantidadTotal = cantidad.get();
            String tamanio = (String) panelExistente.getClientProperty("tamaño");
            
            ItemOrden item = new ItemOrden(producto, cantidadTotal, tamanio);
            productosParaOrdenar.add(item);
        }
        return productosParaOrdenar;
    }

    public void cerrarPopupOrden(){
        if (notificacion != null){
            notificacion.cerrar();
            notificacion = null;
        }
    }

    public void eliminarProductosDeOrden(){
        orden.eliminarProductosDeOrden();
    }

    public void limpiarCarrito(){
        orden.carritoVacio();
    }
}