package com.ang3lfco;

import java.sql.SQLException;
import java.util.List;

import conexion.ConexionBD;
import daos.CafeDAO;
import entidades.Cafe;
import interfaces.ICafeDAO;
import interfaces.ICafeNegocio;
import negocio.CafeNegocio;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");

        ConexionBD conexion = new ConexionBD();
        ICafeDAO cafeDAO = new CafeDAO(conexion);
        ICafeNegocio cafeNegocio = new CafeNegocio(cafeDAO);
        List<Cafe> cafes = cafeNegocio.obtenerCafes();

        for(Cafe cafe : cafes){
            System.out.println(cafe.getNombre());
        }
    }
}