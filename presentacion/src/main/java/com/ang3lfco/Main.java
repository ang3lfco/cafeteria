package com.ang3lfco;

import java.sql.SQLException;

import conexion.ConexionBD;
import daos.CafeDAO;
import daos.OrdenDAO;
import daos.OrdenProductoDAO;
import daos.PostreDAO;
import daos.TamanioCafeDAO;
import interfaces.ICafeDAO;
import interfaces.ICafeNegocio;
import interfaces.IOrdenDAO;
import interfaces.IOrdenNegocio;
import interfaces.IOrdenProductoDAO;
import interfaces.IOrdenProductoNegocio;
import interfaces.IPostreDAO;
import interfaces.IPostreNegocio;
import interfaces.ITamanioCafeDAO;
import interfaces.ITamanioCafeNegocio;
import negocio.CafeNegocio;
import negocio.OrdenNegocio;
import negocio.OrdenProductoNegocio;
import negocio.PostreNegocio;
import negocio.TamanioCafeNegocio;
import ui.Inicio;

public class Main {
    public static void main(String[] args) throws SQLException {
        ConexionBD conexionBD = new ConexionBD();
        
        ICafeDAO cafeDAO = new CafeDAO(conexionBD);
        IPostreDAO postreDAO = new PostreDAO(conexionBD);
        IOrdenDAO ordenDAO = new OrdenDAO(conexionBD);
        IOrdenProductoDAO ordenProductoDAO = new OrdenProductoDAO(conexionBD);
        ITamanioCafeDAO tamanioCafeDAO = new TamanioCafeDAO(conexionBD);
        
        ICafeNegocio cafeNegocio = new CafeNegocio(cafeDAO);
        IPostreNegocio postreNegocio = new PostreNegocio(postreDAO);
        ITamanioCafeNegocio tamanioCafeNegocio = new TamanioCafeNegocio(tamanioCafeDAO);
        IOrdenNegocio ordenNegocio = new OrdenNegocio(ordenDAO);
        IOrdenProductoNegocio ordenProductoNegocio = new OrdenProductoNegocio(ordenProductoDAO);

        Inicio inicio = new Inicio(
            cafeNegocio, 
            postreNegocio, 
            tamanioCafeDAO, 
            tamanioCafeNegocio, 
            ordenNegocio, 
            ordenProductoNegocio
        );
        inicio.setVisible(true);
    }
}