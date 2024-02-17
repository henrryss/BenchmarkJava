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
import com.HenrrySS.benchmarkmaven.Views.JDCribaEratostenes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author henrr
 */
public class CribaEratostenes {

    private JDCribaEratostenes frame;
    private FRMBenchmark bk;
    private Integer dataCriba[][] = new Integer[625][16];
    public static long tiempoCriba;

    public CribaEratostenes(FRMBenchmark framePrincipal, JDCribaEratostenes frameVista) {
        bk = framePrincipal;
        this.frame = frameVista;
        InicializarElementosCriba();
        this.frame.btnejcutarcriba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonEjecutar(e);

            }
        });
    }

    private void InicializarElementosCriba() {
        int contador = 0;
        for (int i = 0; i < dataCriba.length; i++) {
            for (int j = 0; j < dataCriba[i].length; j++) {
                if (i == 0 & j == 0) {
                    dataCriba[i][j] = 0;
                } else {
                    dataCriba[i][j] = contador + 1;
                }
                contador++;
            }
        }

        VisualizarJTable((Object[][]) dataCriba, frame.jTable9, frame.jScrollPane10);

    }

    private void clickButtonEjecutar(ActionEvent e) {
        long t1, t2;// variables para calcular el tiempo
        t1 = System.currentTimeMillis();// capturamos el tiempo al inicio de la ejecucion
        ArrayList<Object> listaPrimos = new ArrayList();//para obtner lista de primos

        //para anular los elementos, asignamos los multiplos a un valor 0
        for (int i = 0; i < dataCriba.length; i++) { // Bucle para buscar elemento desde la primero fila
            for (int j = 0; j < dataCriba[i].length; j++) {// Bucle para buscar elemento desde la primero columna

                if (Math.pow(dataCriba[i][j], 2) > 10000) {// si el numero elevedao al cuadrado es mayor que 10000
                    if (dataCriba[i][j] != 0) {
                        listaPrimos.add(dataCriba[i][j]); // listamos todos los numeros restantes
                    }
                } else {
                    if (dataCriba[i][j] != 0) {
                        listaPrimos.add(dataCriba[i][j]);// listamos los numeros que se encuentras sin anular
                        for (int k = i; k < dataCriba.length; k++) {
                            for (int l = 0; l < dataCriba[i].length; l++) {
                                if (dataCriba[k][l] > dataCriba[i][j]) {
                                    if (dataCriba[k][l] % dataCriba[i][j] == 0) {
                                        dataCriba[k][l] = 0;// recorremos nuevamente el array para hallar los multiplos de los numeros
                                    }

                                }
                            }
                        }
                    }
                }

            }
            // visualizacion en pantalla de las tablas
            cargarTablaCriba();
            cargarListaPrimos(listaPrimos);
        }
        // capturamos el final de la ejecucion
        t2 = System.currentTimeMillis();
        tiempoCriba = t2 - t1; // total del tiempo sera la diferencia entr fin e inicio
        frame.txtiempoCriba.setText(Long.toString(tiempoCriba));

        // reporte();
        frame.btnejcutarcriba.setEnabled(false);
        bk.btnCriba.setEnabled(false);
        bk.lblCheckCriba.setEnabled(true);
    }

    private void VisualizarJTable(Object[][] data, JTable jTable, JScrollPane jScrollPane) {
        String[] cabecera = new String[data[0].length];
        boolean[] editable = new boolean[data[0].length];
        for (int i = 0; i < cabecera.length; i++) {
            cabecera[i] = "";
            editable[i] = false;
        }

        jTable.setModel(new javax.swing.table.DefaultTableModel(
                data,
                cabecera
        ) {
            boolean[] canEdit = editable;

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane.setViewportView(jTable);
        jTable.getTableHeader().setVisible(false);
    }

    private void cargarTablaCriba() {
        Object[][] data = new Object[dataCriba.length][dataCriba[0].length];

        for (int i = 0; i < dataCriba.length; i++) {
            for (int j = 0; j < dataCriba[i].length; j++) {
                if (dataCriba[i][j] == 0 || dataCriba[i][j] == 1) {
                    // data[i][j] = "";
                    frame.jTable9.setValueAt("", i, j);
                }
            }
        }
    }

    private void cargarListaPrimos(ArrayList<Object> listaPrimos) {
        Object[][] data = new Object[listaPrimos.size()][2];
        for (int i = 0; i < listaPrimos.size(); i++) {
            data[i][0] = "Número " + (i + 1);
            data[i][1] = listaPrimos.get(i);
        }

        VisualizarJTable(data, this.frame.jTable8, this.frame.jScrollPane9);

    }

}
