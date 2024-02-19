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
import com.HenrrySS.benchmarkmaven.Views.JDPermutaciones;
import com.HenrrySS.benchmarkmaven.Views.JDProductoMatrices;
import com.HenrrySS.benchmarkmaven.Views.JDQuickSort;
import com.HenrrySS.benchmarkmaven.Views.JDReporte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;

/**
 *
 * @author henrr
 */
public class Benchmark {

    private FRMBenchmark frame;

    public Benchmark(FRMBenchmark framePrincipal) {
        this.frame = framePrincipal;
        this.frame.btnQuickSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonQuicksort(e);
            }

        });
        this.frame.btnMultiplicacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonMultiplicacion(e);
            }

        });
        this.frame.btnPermutaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonPermutacion(e);
            }

        });
        this.frame.btnCriba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonCriba(e);
            }

        });
        this.frame.btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonReporte(e);
            }

        });
        this.frame.lblCheckQuickSort.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                lblChangeProperty(evt);
            }  
        });
        this.frame.lblCheckMultiplicacion.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                lblChangeProperty(evt);
            }  
        });
        this.frame.lblCheckPermutaciones.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                lblChangeProperty(evt);
            }  
        });
        this.frame.lblCheckCriba.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                lblChangeProperty(evt);
            }  
        });

        setVisible(true);
    }

    private void clickButtonQuicksort(ActionEvent e) {
        JDQuickSort jDQuickSort = new JDQuickSort(frame, true);
        new QuickSort(frame, jDQuickSort);
        jDQuickSort.setLocationRelativeTo(null);
        jDQuickSort.setVisible(true);
    }

    private void clickButtonMultiplicacion(ActionEvent e) {
        JDProductoMatrices jDProductoMatrices = new JDProductoMatrices(frame, true);
        new ProductoMatrices(frame, jDProductoMatrices);
        jDProductoMatrices.setLocationRelativeTo(null);
        jDProductoMatrices.setVisible(true);
    }

    private void clickButtonPermutacion(ActionEvent e) {
        JDPermutaciones per = new JDPermutaciones(frame, true);
        new Permutacion(frame, per);
        per.setLocationRelativeTo(null);
        per.setVisible(true);
    }

    private void clickButtonCriba(ActionEvent e) {
        JDCribaEratostenes criba = new JDCribaEratostenes(frame, true);
        new CribaEratostenes(frame, criba);
        criba.setLocationRelativeTo(null);
        criba.setVisible(true);
    }

    private void clickButtonReporte(ActionEvent e) {
        JDReporte reporte = new JDReporte(frame, true);
        new Reporte(frame, reporte);
        reporte.setLocationRelativeTo(null);
        reporte.setVisible(true);
    }

    private void lblChangeProperty(java.beans.PropertyChangeEvent evt) {
        if (frame.lblCheckQuickSort.isEnabled() == true
                & frame.lblCheckMultiplicacion.isEnabled() == true
                & frame.lblCheckPermutaciones.isEnabled() == true
                & frame.lblCheckCriba.isEnabled() == true) {

            frame.btnReporte.setEnabled(true);
        } else {
            frame.btnReporte.setEnabled(false);
        }

    }
    
    
    private void setVisible(Boolean b){
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/HenrrySS/benchmarkmaven/resources/benchmarking.png"));
        this.frame.setIconImage(icon.getImage());
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(b);
    }

}
