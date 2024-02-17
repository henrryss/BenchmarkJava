/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HenrrySS.benchmarkmaven.controllers;

import com.HenrrySS.benchmarkmaven.Views.FRMBenchmark;
import com.HenrrySS.benchmarkmaven.Views.JDPermutaciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author henrry_s
 */
public class Permutacion {

    private JDPermutaciones frame;
    private Object[] elementos = new Object[7];
    public static long tiempoPermutacion;
    private ArrayList<Object> permut = new ArrayList();

    public Permutacion(FRMBenchmark framePrincipal, JDPermutaciones frameVista) {
        FRMBenchmark bk = framePrincipal;
        this.frame = frameVista;
        InicializarValores();
        this.frame.btnpermutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                long t1, t2;// variables para calcular el tiempo
                t1 = System.currentTimeMillis(); // inicio del tiempo

                int n = 8; //tipos para escoger
                int r = elementos.length; // numero de elementos

                // invocamos al metodo permutar
                // pasando como parametro los elemntos, un espacio en blanco, los tipos a escoger y numero de elementos
                // lisamos todas las permutaciones para mostrar en la aplicacion
                Permutar(elementos, "", n, r);

                // visualizamos en la tabla de permutaciones
                Object permutaciones[] = permut.toArray();
                VisualizarJTable(permutaciones, frame.jTabPermutaciones, frame.jScrollPane7);

                //finaliza el tiempo de ejecion
                t2 = System.currentTimeMillis();
                // calculamos la diferencia
                tiempoPermutacion = (t2 - t1);

                frame.txtcantper.setText(Integer.toString(permut.size()));
                frame.txtiempoPermut.setText(Float.toString(tiempoPermutacion));

                //reporte();
                frame.btnpermutar.setEnabled(false);
                bk.btnPermutaciones.setEnabled(false);
                bk.lblCheckPermutaciones.setEnabled(true);

            }

        });

    }

    private void Permutar(Object[] elementos, String act, int n, int r) {

        // funcion recursiva
        // n= numero de elementos en cada permutacion
        //r= numero de elementos para permutar
        if (n == 0) {
            permut.add(act); // listamos las permutaciones
        } else {
            //  hacemos las combinaciones de n en n
            for (int i = 0; i < r; i++) {
                Permutar(elementos, act + elementos[i], n - 1, r);

            }
        }
    }

    private void InicializarValores() {

        elementos = new Object[]{"A", "B", "C", "D", "E", "F", "G","H"};

        VisualizarJTable(elementos, this.frame.jTabElementos, this.frame.jScrollPane4);
    }

    private void VisualizarJTable(Object[] arreglo, JTable jTable, JScrollPane jScrollPane) {

        Object[][] data = new Object[arreglo.length][2];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = i + 1;
            data[i][1] = arreglo[i];
        }
        jTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                    "#", "Elemento"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setResizable(false);
            jTable.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTable.getColumnModel().getColumn(1).setResizable(false);
            jTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }
    }
}
