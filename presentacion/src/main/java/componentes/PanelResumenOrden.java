package componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import entidades.Cafe;
import entidades.OrdenProducto;
import interfaces.IOrdenNegocio;
import interfaces.IOrdenProductoNegocio;
import ui.Inicio;

public class PanelResumenOrden extends JDialog{
    private List<ItemOrden> productosParaOrdenar = new ArrayList<>();
    private IOrdenNegocio ordenNegocio;
    private IOrdenProductoNegocio ordenProductoNegocio;
    private Inicio inicio;

    public PanelResumenOrden(Inicio inicio, List<ItemOrden> productosParaOrdenar, IOrdenNegocio ordenNegocio, IOrdenProductoNegocio ordenProductoNegocio){
        super(inicio, "Confirmar Orden", true);
        this.inicio = inicio;
        this.productosParaOrdenar = productosParaOrdenar;
        this.ordenNegocio = ordenNegocio;
        this.ordenProductoNegocio = ordenProductoNegocio;
        setUndecorated(true);
        getContentPane().add(generarPanel());
        pack();
        setLocationRelativeTo(inicio);
        // setVisible(true);
    }

    public JPanel generarPanel(){
        JPanel fondo = new JPanel();
        fondo.setBackground(new Color(109, 74, 49));
        // fondo.setBackground(new Color(173, 114, 57));
        fondo.setLayout(new BoxLayout(fondo, BoxLayout.Y_AXIS));
        fondo.setPreferredSize(new Dimension(480, 350));
        JPanel contenedor1 = new JPanel();
        contenedor1.setOpaque(false);
        contenedor1.setPreferredSize(new Dimension(480, 250));
        JPanel contenedor2 = new JPanel();
        // contenedor2.setBackground(Color.PINK);
        contenedor2.setOpaque(false);
        contenedor2.setPreferredSize(new Dimension(480, 100));
        contenedor2.setLayout(new GridBagLayout());
        
        JPanel panelResumenOrden = new JPanel();
        panelResumenOrden.setOpaque(false);
        panelResumenOrden.setLayout(new BoxLayout(panelResumenOrden, BoxLayout.Y_AXIS));
        JScrollPane scrResumenOrden = new JScrollPane(panelResumenOrden);
        scrResumenOrden.getVerticalScrollBar().setUI(new BarraDesplazamiento());
        scrResumenOrden.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrResumenOrden.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrResumenOrden.setOpaque(false);
        scrResumenOrden.getViewport().setOpaque(false);
        scrResumenOrden.setPreferredSize(new Dimension(480,250));
        scrResumenOrden.setBorder(null);
        JLabel lblTitulo = new JLabel("Resumen de Orden");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font(getName(), Font.BOLD, 16));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        panelResumenOrden.add(lblTitulo);
        panelResumenOrden.add(Box.createRigidArea(new Dimension(0, 10)));
        for(ItemOrden item : productosParaOrdenar){
            JPanel pnlOrdenIndividual = new JPanel();
            pnlOrdenIndividual.setBorder(new EmptyBorder(20,20,20,20));
            pnlOrdenIndividual.setLayout(new BoxLayout(pnlOrdenIndividual, BoxLayout.X_AXIS));
            pnlOrdenIndividual.setPreferredSize(new Dimension(463, 50));
            pnlOrdenIndividual.setMaximumSize(new Dimension(463, 50));
            pnlOrdenIndividual.setMinimumSize(new Dimension(463, 50));
            // pnlOrdenIndividual.setBackground(Color.CYAN);
            pnlOrdenIndividual.setOpaque(false);
            pnlOrdenIndividual.add(new JLabel(item.getProducto().getNombre())).setForeground(Color.WHITE);
            pnlOrdenIndividual.add(Box.createHorizontalGlue());
            // pnlOrdenIndividual.add(Box.createRigidArea(new Dimension()));
            if(item.getProducto() instanceof Cafe){
                switch (item.getTamanio()) {
                    case "CH":
                        pnlOrdenIndividual.add(new JLabel("Café chico")).setForeground(Color.WHITE);
                        pnlOrdenIndividual.add(Box.createHorizontalGlue());
                        break;
                
                    case "M":
                        pnlOrdenIndividual.add(new JLabel("Café mediano")).setForeground(Color.WHITE);
                        pnlOrdenIndividual.add(Box.createHorizontalGlue());
                        break;
                        
                    case "G":
                        pnlOrdenIndividual.add(new JLabel("Café grande")).setForeground(Color.WHITE);
                        pnlOrdenIndividual.add(Box.createHorizontalGlue());
                        break;
                }
            }
            pnlOrdenIndividual.add(new JLabel(String.valueOf(item.getCantidad()))).setForeground(Color.WHITE);
            panelResumenOrden.add(pnlOrdenIndividual);
        }
        contenedor1.add(scrResumenOrden);
        
        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setFocusable(false);
        btnConfirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConfirmar.setBackground(new Color(102, 187, 106));
        btnConfirmar.setForeground(Color.WHITE);
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusable(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setBackground(new Color(229, 115, 115));
        btnCancelar.setForeground(Color.WHITE);
        btnConfirmar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    int idOrden = ordenNegocio.registrarOrden(new entidades.Orden(LocalDateTime.now()));
                    for(ItemOrden it : productosParaOrdenar){
                        OrdenProducto op = new OrdenProducto(idOrden, it.getProducto().getId(), it.getCantidad(), it.getTamanio());
                        ordenProductoNegocio.registrarOrdenProducto(op);
                    }
                    JOptionPane.showMessageDialog(null, "Orden Registrada.");
                    inicio.cerrarPopupOrden();
                    inicio.eliminarProductosDeOrden();
                    inicio.limpiarCarrito();
                    Window window = SwingUtilities.getWindowAncestor(btnConfirmar);
                    if(window != null){
                        window.dispose();
                    }
                }
                catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                SwingUtilities.getWindowAncestor(btnCancelar).dispose();
            }
        });
        
        contenedor2.add(btnCancelar);
        contenedor2.add(Box.createRigidArea(new Dimension(10, 0)));
        contenedor2.add(btnConfirmar);
        fondo.add(contenedor1);
        fondo.add(contenedor2);
        return fondo;
    }
}