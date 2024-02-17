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
import com.HenrrySS.benchmarkmaven.Views.JDReporte;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

/**
 *
 * @author henrr
 */
public class Reporte {

    private JDReporte frame;
    private SystemInfo si;
    private HardwareAbstractionLayer har;
    private CentralProcessor cpu;
    private OperatingSystem os;

    public Reporte(FRMBenchmark framePrincipal, JDReporte frameVista) {
        this.frame = frameVista;
        CargarReporte();
    }

    private void CargarReporte() {

        si = new SystemInfo();
        har = si.getHardware();
        cpu = har.getProcessor();
        os = si.getOperatingSystem();

        CargarPanelSistema();
        CargarPanelCPU();
        CargarPanelMemoria();
        CargarPanelRendimiento();
    }

    private void CargarPanelSistema() {
        //Informacion Sistema Operativo
        this.frame.jLabel32.setText(os.toString());
        this.frame.jLabel34.setText(Integer.toString(si.getOperatingSystem().getBitness()) + " bits");
        this.frame.jLabel35.setText(os.getVersionInfo().getBuildNumber());
        this.frame.jLabel36.setText(si.getOperatingSystem().getManufacturer());
        this.frame.jLabel37.setText(si.getOperatingSystem().getVersionInfo().getVersion());
        long uptime = si.getOperatingSystem().getSystemUptime();
        // Convierte los segundos a días, horas, minutos y segundos
        long days = uptime / (24 * 60 * 60);
        long hours = (uptime % (24 * 60 * 60)) / (60 * 60);
        long minutes = (uptime % (60 * 60)) / 60;
        long seconds = uptime % 60;

        this.frame.jLabel38.setText(days + " días, "
                + hours + " horas, "
                + minutes + " minutos, "
                + seconds + " segundos.");

    }

    private void CargarPanelMemoria() {
        //Informacion Memoria RAM
        GlobalMemory mem = har.getMemory();
        String memoriasRAM = "";
        if (mem.getPhysicalMemory().size() > 1) {
            memoriasRAM = Integer.toString(mem.getPhysicalMemory().size()) + " Slots";
        } else {
            memoriasRAM = "1 Slot";
        }
        this.frame.jLabel85.setText(memoriasRAM);
        this.frame.jLabel47.setText(Long.toString(mem.getTotal() / (1000000000)) + " GB");
        this.frame.jLabel48.setText(Long.toString((mem.getTotal() - mem.getAvailable()) / (1000000000)) + " GB");
        this.frame.jLabel49.setText(Long.toString(mem.getAvailable() / (1000000000)) + " GB");
        this.frame.jLabel50.setText(Long.toString(mem.getVirtualMemory().getSwapTotal() / (1000000000)) + " GB");
        this.frame.jLabel51.setText(Long.toString(mem.getVirtualMemory().getSwapUsed() / (1000000000)) + " GB");

    }

    private void CargarPanelCPU() {
        //Informatcion CPU
        ProcessorIdentifier cpuData = cpu.getProcessorIdentifier();
        this.frame.jLabel60.setText(cpuData.getVendor());
        this.frame.jLabel61.setText(cpuData.getMicroarchitecture());
        this.frame.jLabel62.setText(cpuData.getName());
        this.frame.jLabel63.setText(Integer.toString(cpu.getPhysicalPackageCount()));
        this.frame.jLabel64.setText(Integer.toString(cpu.getPhysicalProcessorCount()));
        this.frame.jLabel65.setText(Integer.toString(cpu.getLogicalProcessorCount()));
    }

    private void CargarPanelRendimiento() {

        long tiempoQuicksort = QuickSort.tiempoQuicksort;
        long tiempoMultiplicacion = ProductoMatrices.tiempoMultiplicacion;
        long tiempoPermutacion = Permutacion.tiempoPermutacion;
        long tiempoCriba = CribaEratostenes.tiempoCriba;
        long tiempoTotal = tiempoQuicksort + tiempoMultiplicacion + tiempoPermutacion + tiempoCriba;
         
        // rendimiento en milisegundos
        this.frame.jLabel75.setText(Long.toString(tiempoQuicksort));
        this.frame.jLabel76.setText(Long.toString(tiempoMultiplicacion));
        this.frame.jLabel77.setText(Long.toString(tiempoPermutacion));
        this.frame.jLabel78.setText(Long.toString(tiempoCriba));
        this.frame.jLabel79.setText(Long.toString(tiempoTotal));
        
        // rendimiento en segundos

        java.text.DecimalFormat formatoSalidaDecimal = new java.text.DecimalFormat("0.00");//para dos decimales

        float tiemposecqs = (float)tiempoQuicksort / 1000;// tiempo en segundos quicksort
        this.frame.jLabel80.setText(formatoSalidaDecimal.format(tiemposecqs));
        float tiemposecmul = (float)tiempoMultiplicacion / 1000;// tiempo en segundos multiplicacion
        this.frame.jLabel81.setText(formatoSalidaDecimal.format(tiemposecmul));
        float tiemposecper = (float)tiempoPermutacion / 1000;// tiempo en segundos permutaciones
        this.frame.jLabel82.setText(formatoSalidaDecimal.format(tiemposecper));
        float tiemposeceras = (float)tiempoCriba / 1000;// tiempo en segundos criba de erastotenes
        this.frame.jLabel83.setText(formatoSalidaDecimal.format(tiemposeceras));

        this.frame.jLabel84.setText(Float.toString(tiemposecqs + tiemposecmul + tiemposecper + tiemposeceras));
    }

}
