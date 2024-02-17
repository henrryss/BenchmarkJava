/*
 * Copyright (C) 2024 henrr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.HenrrySS.benchmarkmaven.controllers;

import com.HenrrySS.benchmarkmaven.Views.FRMBenchmark;
import com.HenrrySS.benchmarkmaven.Views.JDProductoMatrices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author henrr
 */
public class ProductoMatrices {

    private JDProductoMatrices frame;
    private FRMBenchmark bk;
    private Random rmd;
    private int[][] arreglo1, arreglo2;
    public static long tiempoMultiplicacion;

    public ProductoMatrices(FRMBenchmark benchmark, JDProductoMatrices jDProductoMatrices) {
        this.bk = benchmark;
        this.frame = jDProductoMatrices;

        //evento del boton insertar elementos en matriz 20x5
        frame.btnInsertElemTabla1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonMultiplicando(e);
            }
        });

        frame.btnInsertElemTabla2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonMultiplicador(e);
            }
        });

        frame.btnMultiplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonProducto(e);
            }
        });
    }

    private void clickButtonMultiplicando(ActionEvent e) {
        long t1, t2;//variable para calcular el tiempo

        t1 = System.currentTimeMillis();// capturamos el tiempo inicial
        arreglo1 = new int[20][5];
        rmd = new Random();
        for (int i = 0; i < arreglo1.length; i++) {
            for (int j = 0; j < arreglo1[i].length; j++) {
                arreglo1[i][j] = rmd.nextInt(16);// llenamos el arreglo con numeros aleatorios de 0 a 100
            }
        }

        //visualizamos en pantalla el arreglo
        InsertEnJtable(arreglo1, frame.jTable3, frame.jScrollPane1);
        frame.jLabel8.setVisible(true);
        frame.btnInsertElemTabla1.setEnabled(false);
        verificarBotones();
        //finaliza la ejecuion de la multiplicaion de arreglos
        t2 = System.currentTimeMillis();

        // tiempo total
        tiempoMultiplicacion += (t2 - t1);
    }

    private void clickButtonMultiplicador(ActionEvent e) {
        long t1, t2;//variable para calcular el tiempo

        t1 = System.currentTimeMillis();// capturamos el tiempo inicial
        arreglo2 = new int[5][20];
        rmd = new Random();
        for (int i = 0; i < arreglo2.length; i++) {
            for (int j = 0; j < arreglo2[i].length; j++) {
                arreglo2[i][j] = rmd.nextInt(16);// llenamos el arreglo con numeros aleatorios de 0 a 100
            }
        }

        InsertEnJtable(arreglo2, frame.jTable4, frame.jScrollPane2);

        frame.jLabel9.setVisible(true);
        frame.btnInsertElemTabla2.setEnabled(false);
        verificarBotones();
        //finaliza la ejecuion de la multiplicaion de arreglos
        t2 = System.currentTimeMillis();

        // tiempo total
        tiempoMultiplicacion += (t2 - t1);
    }

    private void clickButtonProducto(ActionEvent e) {
        long t1, t2;//variable para calcular el tiempo

        t1 = System.currentTimeMillis();// capturamos el tiempo inicial

        int producto[][] = productoArreglos(arreglo1, arreglo2);

        //visualizar tabla en pantalla
        InsertEnJtable(producto, frame.jTable5, frame.jScrollPane3);

        frame.jLabel10.setVisible(true);

        //finaliza la ejecuion de la multiplicaion de arreglos
        t2 = System.currentTimeMillis();

        // tiempo total
        tiempoMultiplicacion += (t2 - t1);
        frame.txtTiempoProd.setText(Long.toString(tiempoMultiplicacion));
        frame.btnMultiplicar.setEnabled(false);

        bk.btnMultiplicacion.setEnabled(false);
        bk.lblCheckMultiplicacion.setEnabled(true);
    }

    private void InsertEnJtable(int[][] array, JTable jTable, JScrollPane jScrollPane) {

        String[] cabecera = new String[array[0].length];
        boolean[] celdaEditable = new boolean[array[0].length];
        for (int i = 0; i < cabecera.length; i++) {
            cabecera[i] = "";
            celdaEditable[i] = false;
        }
        jTable.setModel(new javax.swing.table.DefaultTableModel(
                convertirMatriz(array),
                cabecera) {
            boolean[] canEdit = celdaEditable;

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(jTable);

        if (jTable.getColumnModel().getColumnCount() > 0) {
            for (int i = 0; i < jTable.getColumnModel().getColumnCount(); i++) {
                jTable.getColumnModel().getColumn(i).setResizable(false);
            }
        }
        jTable.getTableHeader().setVisible(false);

    }

    private Object[][] convertirMatriz(int[][] matrizEnteros) {
        int filas = matrizEnteros.length;
        int columnas = matrizEnteros[0].length;

        // Crear una nueva matriz de objetos con las mismas dimensiones
        Object[][] matrizObjetos = new Object[filas][columnas];

        // Copiar los elementos de la matriz de enteros a la matriz de objetos
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matrizObjetos[i][j] = matrizEnteros[i][j];
            }
        }

        return matrizObjetos;
    }

    private int[][] productoArreglos(int[][] array1, int[][] array2) {
        /*declaramos el arreglo resultante filas=numero de filas del primer arreglo
         columnas = numero columnas segundo arreglo
         */
        int[][] multiplicacion = new int[array1.length][array2[0].length];

        //insertamos en el arreglo los resultados de las operaciones
        for (int x = 0; x < multiplicacion.length; x++) {
            for (int y = 0; y < multiplicacion[x].length; y++) {
                for (int z = 0; z < array1[0].length; z++) {
                    multiplicacion[x][y] += array1[x][z] * array2[z][y];
                }
            }
        }

        return multiplicacion;
    }

    private void verificarBotones() {
        if (frame.btnInsertElemTabla1.isEnabled()==false & frame.btnInsertElemTabla2.isEnabled()==false) {
            frame.btnMultiplicar.setEnabled(true);
        } else {
            frame.btnMultiplicar.setEnabled(false);
        }
    }
}
