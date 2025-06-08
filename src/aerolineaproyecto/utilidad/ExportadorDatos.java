/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.utilidad;
// Import para PDF (iText / com.lowagie.text)
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Empleado;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

// Import para Apache POI - Excel
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author Pablo Silva
 */
public class ExportadorDatos {

    public static void exportarCSV(List<Empleado> empleados, String nombreArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("ID,Nombre,Tipo,Dirección,Fecha Nacimiento,Salario\n");
            for (Empleado emp : empleados) {
                writer.write(String.format("%s,%s,%s,%s,%s,%.2f\n",
                        emp.getId(), emp.getNombre(), emp.getTipoEmpleado(),
                        emp.getDireccion(), emp.getFechaNacimiento(), emp.getSalario()));
            }
        }
    }

    public static void exportarExcel(List<Empleado> empleados, String nombreArchivo) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Empleados");

        // Encabezado
        Row header = sheet.createRow(0);
        String[] columnas = {"ID", "Nombre", "Tipo", "Dirección", "Fecha Nacimiento", "Salario"};
        for (int i = 0; i < columnas.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columnas[i]);
        }

        // Datos
        int rowIndex = 1;
        for (Empleado emp : empleados) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getNombre());
            row.createCell(2).setCellValue(emp.getTipoEmpleado());
            row.createCell(3).setCellValue(emp.getDireccion());
            row.createCell(4).setCellValue(emp.getFechaNacimiento());
            row.createCell(5).setCellValue(emp.getSalario());
        }

        try (FileOutputStream fos = new FileOutputStream(nombreArchivo)) {
            workbook.write(fos);
        }

        workbook.close();
    }

    public static void exportarPDF(List<Empleado> empleados, String nombreArchivo) throws Exception {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph titulo = new Paragraph("Lista de Empleados", font);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" ")); // espacio

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 3, 2, 4, 3, 2});

        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Tipo");
        table.addCell("Dirección");
        table.addCell("Fecha Nacimiento");
        table.addCell("Salario");

        for (Empleado emp : empleados) {
            table.addCell(emp.getId());
            table.addCell(emp.getNombre());
            table.addCell(emp.getTipoEmpleado());
            table.addCell(emp.getDireccion());
            table.addCell(emp.getFechaNacimiento());
            table.addCell(String.format("%.2f", emp.getSalario()));
        }

        document.add(table);
        document.close();
    }
    public static void exportarCSVClientes(List<Cliente> clientes, String pathArchivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(pathArchivo)) {
            writer.println("Nombres,Apellidos,Nacionalidad,Fecha de Nacimiento");
            for (Cliente cliente : clientes) {
                writer.printf("%s,%s,%s,%s%n",
                        cliente.getNombres(),
                        cliente.getApellidos(),
                        cliente.getNacionalidad(),
                        cliente.getFechaNacimiento());
            }
        }
    }

    public static void exportarExcelClientes(List<Cliente> clientes, String pathArchivo) throws IOException {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Clientes");

            Row header = sheet.createRow(0);
            String[] columnas = {"Nombres", "Apellidos", "Nacionalidad", "Fecha de Nacimiento"};
            for (int i = 0; i < columnas.length; i++) {
                header.createCell(i).setCellValue(columnas[i]);
            }

            int rowNum = 1;
            for (Cliente cliente : clientes) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cliente.getNombres());
                row.createCell(1).setCellValue(cliente.getApellidos());
                row.createCell(2).setCellValue(cliente.getNacionalidad());
                row.createCell(3).setCellValue(cliente.getFechaNacimiento());
            }

            try (FileOutputStream fileOut = new FileOutputStream(pathArchivo)) {
                workbook.write(fileOut);
            }
        }
    }

    public static void exportarPDFClientes(List<Cliente> clientes, String pathArchivo) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(pathArchivo));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph titulo = new Paragraph("Lista de Clientes", font);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 3, 3, 3});

        Stream.of("Nombres", "Apellidos", "Nacionalidad", "Fecha de Nacimiento")
                .forEach(tituloCol -> {
                    PdfPCell cell = new PdfPCell(new Phrase(tituloCol));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                });

        for (Cliente cliente : clientes) {
            table.addCell(cliente.getNombres());
            table.addCell(cliente.getApellidos());
            table.addCell(cliente.getNacionalidad());
            table.addCell(cliente.getFechaNacimiento() != null ? cliente.getFechaNacimiento().toString() : "");

        }

        document.add(table);
        document.close();
    }
}