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
import com.HenrrySS.benchmarkmaven.Views.JDQuickSort;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author henrr
 */
public class QuickSort {

    private int[] elementos;
    private JDQuickSort frame;
    private FRMBenchmark bk;
    public static long tiempoQuicksort;

    public QuickSort(FRMBenchmark benchamrk, JDQuickSort frmQuickSort) {

        bk = benchamrk;
        this.frame = frmQuickSort;

        frame.InsertarElemento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonInsert(e);
            }
        });

        frame.ordenarQuicksort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonOrdenar(e);
            }
        });
    }

    private void clickButtonInsert(ActionEvent e) {
        long t1, t2; // inicializmos las variables que permitiran calcular el tiemp de ejecucion

        t1 = System.currentTimeMillis();// iniciamos el tiempo
        //Cuando se hace click en el boton insertar elementos
        //insertar los elementos
        int cantidadElementos = 1000000;
        elementos = new int[cantidadElementos];
        Random rdn = new Random(); //valores aleatorios
        for (int i = 0; i < elementos.length; i++) {
            elementos[i] = rdn.nextInt(cantidadElementos * 2);
        }
        //llenar la jtable
        InsertDataInJtable(elementos, frame.jTable1, frame.jScrollPane5);
        //visualizar los elementos
        frame.jLabel2.setText("* " + elementos.length + " elementos");
        frame.jLabel2.setVisible(true);
        frame.InsertarElemento.setEnabled(false);
        frame.ordenarQuicksort.setEnabled(true);
        // fin de la ejecucion
        t2 = System.currentTimeMillis();
        // tiempo total
        tiempoQuicksort += (t2 - t1);
    }

    private void clickButtonOrdenar(ActionEvent e) {
        long t1, t2; // inicializmos las variables que permitiran calcular el tiemp de ejecucion

        t1 = System.currentTimeMillis();// iniciamos el tiempo
        // capturamos los limites de tiempo
        int limiteInferior = 0;
        int limiteSuperior = elementos.length - 1;

        //llamamos al metodo quicksort con 3 parametros
        int[] dataOrdenada = OrdenamientoQuickSort(elementos, limiteInferior, limiteSuperior);

        // visualizamos en jtable el arreglo ordenado
        InsertDataInJtable(dataOrdenada, frame.jTable2, frame.jScrollPane6);

        frame.jLabel3.setText("*" + elementos.length + " Elementos ordenados");
        frame.jLabel3.setVisible(true);
        frame.ordenarQuicksort.setEnabled(false);

        // fin de la ejecucion
        t2 = System.currentTimeMillis();
        // tiempo total
        tiempoQuicksort = (t2 - t1);
        frame.txtiempoQuicksort.setText(Long.toString(tiempoQuicksort));

        bk.lblCheckQuickSort.setEnabled(true);
        bk.btnQuickSort.setEnabled(false);
    }

    private void InsertDataInJtable(int[] arreglo, JTable jTable, JScrollPane jScrollPane) {

        Object[][] data = new Object[arreglo.length][2];
        for (int i = 0; i < data.length; i++) {
            data[i][0] = i + 1;
            data[i][1] = arreglo[i];
        }

        jTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                    "Id.", "Elemento"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setResizable(false);
            jTable.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTable.getColumnModel().getColumn(1).setResizable(false);
            jTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        }
    }

    private int[] OrdenamientoQuickSort(int[] arreglo, int inferior, int superior) {
        if (arreglo == null || arreglo.length == 0) {
            return null;
        }

        if (inferior >= superior) {
            return null;
        }

        // obtener el pivote
        int medio = inferior + (superior - inferior) / 2;
        int pivote = arreglo[medio];

        // make left < pivot and right > pivot
        int i = inferior, j = superior;
        while (i <= j) {
            while (arreglo[i] < pivote) {
                i++;
            }

            while (arreglo[j] > pivote) {
                j--;
            }

            if (i <= j) {
                // asignamos temp como variable temporal
                int temp = arreglo[i];
                arreglo[i] = arreglo[j];
                arreglo[j] = temp;
                i++;
                j--;
            }
        }

        // ordenamos recursivamente las dos partea
        if (inferior < j) {
            OrdenamientoQuickSort(arreglo, inferior, j);
        }

        if (superior > i) {
            OrdenamientoQuickSort(arreglo, i, superior);
        }

        return arreglo;
    }
}
