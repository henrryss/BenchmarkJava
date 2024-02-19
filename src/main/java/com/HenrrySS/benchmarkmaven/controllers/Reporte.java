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
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
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
        this.frame.btnGenerarReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                generarPDFReporte(e);

            }

        });
    }

    public Reporte() {

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
        // Informacion Sistema Operativo
        this.frame.lblSistemaOperativo.setText(os.toString());
        this.frame.lblArquitectura.setText(Integer.toString(si.getOperatingSystem().getBitness()) + " bits");
        this.frame.lblParches.setText(os.getVersionInfo().getBuildNumber());
        this.frame.lblFabricanteSO.setText(si.getOperatingSystem().getManufacturer());
        this.frame.lblVersionSO.setText(si.getOperatingSystem().getVersionInfo().getVersion());
        long uptime = si.getOperatingSystem().getSystemUptime();
        // Convierte los segundos a días, horas, minutos y segundos
        long days = uptime / (24 * 60 * 60);
        long hours = (uptime % (24 * 60 * 60)) / (60 * 60);
        long minutes = (uptime % (60 * 60)) / 60;
        long seconds = uptime % 60;

        this.frame.lblTiempoEncendido
                .setText(days + " días, " + hours + " horas, " + minutes + " minutos, " + seconds + " segundos.");

    }

    private void CargarPanelMemoria() {
        // Informacion Memoria RAM
        GlobalMemory mem = har.getMemory();
        String memoriasRAM = "";
        if (mem.getPhysicalMemory().size() > 1) {
            memoriasRAM = Integer.toString(mem.getPhysicalMemory().size()) + " Slots";
        } else {
            memoriasRAM = "1 Slot";
        }
        this.frame.lblMemoriaRAM.setText(memoriasRAM);
        this.frame.lblRAMTotal.setText(Long.toString(mem.getTotal() / (1000000000)) + " GB");
        this.frame.lblRAMUsada.setText(Long.toString((mem.getTotal() - mem.getAvailable()) / (1000000000)) + " GB");
        this.frame.lblRAMDisponible.setText(Long.toString(mem.getAvailable() / (1000000000)) + " GB");
        this.frame.lblSWAPTotal.setText(Long.toString(mem.getVirtualMemory().getSwapTotal() / (1000000000)) + " GB");
        this.frame.lblSWAPUsada.setText(Long.toString(mem.getVirtualMemory().getSwapUsed() / (1000000000)) + " GB");

    }

    private void CargarPanelCPU() {
        // Informatcion CPU
        ProcessorIdentifier cpuData = cpu.getProcessorIdentifier();
        this.frame.lblFabricanteCPU.setText(cpuData.getVendor());
        this.frame.lblModeloCPU.setText(cpuData.getMicroarchitecture());
        this.frame.lblDescripcionCPU.setText(cpuData.getName());
        this.frame.lblSockets.setText(Integer.toString(cpu.getPhysicalPackageCount()));
        this.frame.lblCPUFisicas.setText(Integer.toString(cpu.getPhysicalProcessorCount()));
        this.frame.lblCPULogico.setText(Integer.toString(cpu.getLogicalProcessorCount()));
    }

    private void CargarPanelRendimiento() {

        long tiempoQuicksort = QuickSort.tiempoQuicksort;
        long tiempoMultiplicacion = ProductoMatrices.tiempoMultiplicacion;
        long tiempoPermutacion = Permutacion.tiempoPermutacion;
        long tiempoCriba = CribaEratostenes.tiempoCriba;
        long tiempoTotal = tiempoQuicksort + tiempoMultiplicacion + tiempoPermutacion + tiempoCriba;

        // rendimiento en milisegundos
        this.frame.lblQuickms.setText(Long.toString(tiempoQuicksort));
        this.frame.lblMultMatms.setText(Long.toString(tiempoMultiplicacion));
        this.frame.lblPermutacionms.setText(Long.toString(tiempoPermutacion));
        this.frame.lblCribas.setText(Long.toString(tiempoCriba));
        this.frame.lblTOTALms.setText(Long.toString(tiempoTotal));

        // rendimiento en segundos
        java.text.DecimalFormat formatoSalidaDecimal = new java.text.DecimalFormat("0.00");// para dos decimales

        float tiemposecqs = (float) tiempoQuicksort / 1000;// tiempo en segundos quicksort
        this.frame.lblQuicks.setText(formatoSalidaDecimal.format(tiemposecqs));
        float tiemposecmul = (float) tiempoMultiplicacion / 1000;// tiempo en segundos multiplicacion
        this.frame.lblMultMats.setText(formatoSalidaDecimal.format(tiemposecmul));
        float tiemposecper = (float) tiempoPermutacion / 1000;// tiempo en segundos permutaciones
        this.frame.lblPermutacions.setText(formatoSalidaDecimal.format(tiemposecper));
        float tiemposeceras = (float) tiempoCriba / 1000;// tiempo en segundos criba de erastotenes
        this.frame.jLabel83.setText(formatoSalidaDecimal.format(tiemposeceras));

        this.frame.lblTOTALs.setText(formatoSalidaDecimal.format(tiemposecqs + tiemposecmul + tiemposecper + tiemposeceras));
    }

    public void generarPDFReporte(ActionEvent e) {

        // Crear un JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar");

        // Configurar el filtro de extensiones si es necesario (opcional)
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos PDF", ".pdf");
        fileChooser.setFileFilter(filter);

        // Mostrar el cuadro de diálogo
        int resultado = fileChooser.showSaveDialog(null);

        // Procesar la selección del usuario
        if (resultado == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Obtener la ruta del archivo
            String rutaArchivo = archivoSeleccionado.getAbsolutePath().concat(".pdf");
            //generamos el reporte en pdf
            reporteJasper(rutaArchivo);

        }
    }

    private void reporteJasper(String path) {
        try {

            InputStream logoSistema = getClass()
                    .getResourceAsStream("/com/HenrrySS/benchmarkmaven/resources/benchmarking.png");

            // Ruta del archivo JRXML
            String userDir = System.getProperty("user.dir");
            String jrxmlFilePath = "\\src\\main\\java\\com\\HenrrySS\\benchmarkmaven\\resources\\ReporteJasper\\ReporteBenchmark.jrxml";

            // Compilar el archivo JRXML en un objeto JasperReport
            JasperReport jasper = JasperCompileManager.compileReport(userDir + jrxmlFilePath);
            // JasperReport jasper = (JasperReport)JRLoader.loadObject(reporteJasper);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("logosistema", logoSistema);
            // Obtener datos (puedes reemplazar esto con tus propios datos o usar
            // JRDataSource)
            JRDataSource dataSource = obtenerFuenteDeDatos();

            // Parámetros para el informe (puedes agregar más según sea necesario)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parameters, dataSource);

            // Exportar el informe a PDF
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
            exporter.exportReport();

            //abrir el archivo PDF
            abrirArchivoPDF(path);
        } catch (JRException ex) {
            Logger.getLogger(Reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abrirArchivoPDF(String rutaArchivo) {
        try {
            // Comprobar si Desktop es compatible con la operación
            if (Desktop.isDesktopSupported()) {
                // Obtener la instancia de Desktop
                Desktop desktop = Desktop.getDesktop();

                // Abrir el archivo con la aplicación predeterminada
                desktop.open(new File(rutaArchivo));

            } else {
                JOptionPane.showMessageDialog(null, "La apertura de archivos no es compatible en este entorno.");

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al intentar abrir el archivo PDF.");
        }
    }

    private JRDataSource obtenerFuenteDeDatos() {
        // Los datos que se visualizan en pantalla

        return new JRBeanCollectionDataSource(List.of(new HashMap<String, Object>() {
            {
                // Sistema
                put("SistemaOperativo", frame.lblSistemaOperativo.getText());
                put("Arquitectura", frame.lblArquitectura.getText());
                put("Parches", frame.lblParches.getText());
                put("Fabricante", frame.lblFabricanteSO.getText());
                put("VersionSO", frame.lblVersionSO.getText());
                put("TiempoEncendido", frame.lblTiempoEncendido.getText());
                // cpu
                put("FabricanteCPU", frame.lblFabricanteCPU.getText());
                put("ModeloCPU", frame.lblModeloCPU.getText());
                put("DescripcionCPU", frame.lblDescripcionCPU.getText());
                put("Sockets", frame.lblSockets.getText());
                put("CPUsfisicas", frame.lblCPUFisicas.getText());
                put("CPUslogicas", frame.lblCPULogico.getText());
                // memoriaRam
                put("MemoriaRAM", frame.lblMemoriaRAM.getText());
                put("TotalRAM", frame.lblRAMTotal.getText());
                put("RAMUsada", frame.lblRAMUsada.getText());
                put("RAMDisponible", frame.lblRAMDisponible.getText());
                put("SWAPTotal", frame.lblSWAPTotal.getText());
                put("SWAPUsada", frame.lblSWAPUsada.getText());
                // rendimiento
                put("Quicksortms", frame.lblQuickms.getText().concat(" mseg."));
                put("Quicksorts", frame.lblQuicks.getText().concat(" seg."));

                put("Matricesms", frame.lblMultMatms.getText().concat(" mseg."));
                put("Matricess", frame.lblMultMats.getText().concat(" seg."));

                put("Permutacionms", frame.lblPermutacionms.getText().concat(" mseg."));
                put("Permutacions", frame.lblPermutacions.getText().concat(" seg."));

                put("Cribams", frame.lblCribas.getText().concat(" mseg."));
                put("Cribas", frame.jLabel83.getText().concat(" seg."));
                // totales
                put("TOTALms", frame.lblTOTALms.getText().concat(" mseg."));
                put("TOTALs", frame.lblTOTALs.getText().concat(" seg."));
            }
        }));
    }

}
