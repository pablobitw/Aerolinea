    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package aerolineaproyecto.utilidad;
    // Import para PDF (iText / com.lowagie.text)
import aerolineaproyecto.modelo.pojo.Aerolinea;
import aerolineaproyecto.modelo.pojo.Avion;
    import aerolineaproyecto.modelo.pojo.Cliente;
    import aerolineaproyecto.modelo.pojo.Empleado;
import aerolineaproyecto.modelo.pojo.Vuelo;
    import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
    import com.lowagie.text.Element;
    import com.lowagie.text.Font;
    import com.lowagie.text.FontFactory;
    import com.lowagie.text.PageSize;
    import com.lowagie.text.Paragraph;
    import com.lowagie.text.Phrase;
    import com.lowagie.text.pdf.PdfPCell;
    import com.lowagie.text.pdf.PdfPTable;
    import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;

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
        public static void exportarCSVAerolineas(List<Aerolinea> aerolineas, String nombreArchivo) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
        writer.write("ID,Nombre,Dirección,Contacto,Teléfono\n");
        for (Aerolinea aer : aerolineas) {
            writer.write(String.format("%s,%s,%s,%s,%s\n",
                    aer.getId(), aer.getNombre(), aer.getDireccion(),
                    aer.getContacto(), aer.getTelefono()));
        }
    }
}
        public static void exportarExcelAerolineas(List<Aerolinea> aerolineas, String nombreArchivo) throws IOException {
    Workbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("Aerolíneas");

    String[] columnas = {"ID", "Nombre", "Dirección", "Contacto", "Teléfono"};

    // Encabezado
    Row header = sheet.createRow(0);
    for (int i = 0; i < columnas.length; i++) {
        header.createCell(i).setCellValue(columnas[i]);
    }

    // Datos
    int rowNum = 1;
    for (Aerolinea aer : aerolineas) {
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(aer.getId());
        row.createCell(1).setCellValue(aer.getNombre());
        row.createCell(2).setCellValue(aer.getDireccion());
        row.createCell(3).setCellValue(aer.getContacto());
        row.createCell(4).setCellValue(aer.getTelefono());
    }

    try (FileOutputStream out = new FileOutputStream(nombreArchivo)) {
        workbook.write(out);
    }
    workbook.close();
}
public static void exportarPDFAerolineas(List<Aerolinea> aerolineas, String nombreArchivo) throws Exception {
    Document document = new Document(PageSize.A4.rotate());
    PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
    document.open();

    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    Paragraph titulo = new Paragraph("Lista de Aerolíneas", font);
    titulo.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(titulo);
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{2, 3, 4, 3, 3});

    Stream.of("ID", "Nombre", "Dirección", "Contacto", "Teléfono")
          .forEach(tituloCol -> {
              PdfPCell cell = new PdfPCell(new Phrase(tituloCol));
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
              table.addCell(cell);
          });

    for (Aerolinea aer : aerolineas) {
        table.addCell(aer.getId());
        table.addCell(aer.getNombre());
        table.addCell(aer.getDireccion());
        table.addCell(aer.getContacto());
        table.addCell(aer.getTelefono());
    }

    document.add(table);
    document.close();
}
public static void exportarCSVAviones(List<Avion> aviones, String nombreArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
            writer.write("ID,Modelo,Capacidad,Peso\n");
            for (Avion avion : aviones) {
                writer.write(String.format("%s,%s,%d,%.2f\n",
                        avion.getId(),
                        avion.getModelo(),
                        avion.getCapacidad(),
                        avion.getPeso()));
            }
        }
    }

    public static void exportarExcelAviones(List<Avion> aviones, String nombreArchivo) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Aviones");

        String[] columnas = {"ID", "Modelo", "Capacidad", "Peso"};

        Row header = sheet.createRow(0);
        for (int i = 0; i < columnas.length; i++) {
            header.createCell(i).setCellValue(columnas[i]);
        }

        int rowNum = 1;
        for (Avion avion : aviones) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(avion.getId());
            row.createCell(1).setCellValue(avion.getModelo());
            row.createCell(2).setCellValue(avion.getCapacidad());
            row.createCell(3).setCellValue(avion.getPeso());
        }

        try (FileOutputStream out = new FileOutputStream(nombreArchivo)) {
            workbook.write(out);
        }

        workbook.close();
    }

    public static void exportarPDFAviones(List<Avion> aviones, String nombreArchivo) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Paragraph titulo = new Paragraph("Lista de Aviones", font);
        titulo.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2, 3, 2, 2});

        Stream.of("ID", "Modelo", "Capacidad", "Peso")
                .forEach(col -> {
                    PdfPCell cell = new PdfPCell(new Phrase(col));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                });

        for (Avion avion : aviones) {
            table.addCell(avion.getId());
            table.addCell(avion.getModelo());
            table.addCell(String.valueOf(avion.getCapacidad()));
            table.addCell(String.format("%.2f", avion.getPeso()));
        }

        document.add(table);
        document.close();
    }
    public static void exportarCSVVuelos(List<Vuelo> vuelos, String nombreArchivo) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
        writer.write("ID,Numero Pasajeros,Costo Boleto,Tiempo Recorrido,Ciudad Salida,Ciudad Llegada,Fecha Salida,Hora Salida,Fecha Llegada,Hora Llegada\n");
        for (Vuelo v : vuelos) {
            writer.write(String.format("%d,%d,%.2f,%s,%s,%s,%s,%s,%s,%s\n",
                    v.getId(),
                    v.getNumeroPasajeros(),
                    v.getCostoBoleto(),
                    v.getTiempoRecorrido(),
                    v.getCiudadSalida(),
                    v.getCiudadLlegada(),
                    v.getFechaSalida().toString(),
                    v.getHoraSalida().toString(),
                    v.getFechaLlegada().toString(),
                    v.getHoraLlegada().toString()));
        }
    }
}

public static void exportarExcelVuelos(List<Vuelo> vuelos, String nombreArchivo) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo))) {
        // Encabezados
        writer.write("ID,Número Pasajeros,Costo Boleto,Tiempo Recorrido,Ciudad Salida,Ciudad Llegada,Fecha Salida,Hora Salida,Fecha Llegada,Hora Llegada\n");
        
        // Datos
        for (Vuelo v : vuelos) {
            writer.write(String.format("%s,%d,%.2f,%s,%s,%s,%s,%s,%s,%s\n",
                v.getId(),
                v.getNumeroPasajeros(),
                v.getCostoBoleto(),
                v.getTiempoRecorrido(),
                v.getCiudadSalida(),
                v.getCiudadLlegada(),
                v.getFechaSalida().toString(),
                v.getHoraSalida().toString(),
                v.getFechaLlegada().toString(),
                v.getHoraLlegada().toString()
            ));
        }
    }
}

public static void exportarPDFVuelos(List<Vuelo> vuelos, String nombreArchivo) throws Exception {
    Document document = new Document(PageSize.A4.rotate());
    PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
    document.open();

    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    Paragraph titulo = new Paragraph("Lista de Vuelos", font);
    titulo.setAlignment(Paragraph.ALIGN_CENTER);
    document.add(titulo);
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(10);
    table.setWidthPercentage(100);
    table.setWidths(new float[]{2, 3, 3, 3, 4, 4, 3, 2, 3, 2});

    Stream.of("ID", "Número Pasajeros", "Costo Boleto", "Tiempo Recorrido", "Ciudad Salida", "Ciudad Llegada", "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada")
            .forEach(col -> {
                PdfPCell cell = new PdfPCell(new Phrase(col));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            });

    for (Vuelo v : vuelos) {
        table.addCell(String.valueOf(v.getId()));
        table.addCell(String.valueOf(v.getNumeroPasajeros()));
        table.addCell(String.format("%.2f", v.getCostoBoleto()));
        table.addCell(v.getTiempoRecorrido());
        table.addCell(v.getCiudadSalida());
        table.addCell(v.getCiudadLlegada());
        table.addCell(v.getFechaSalida().toString());
        table.addCell(v.getHoraSalida().toString());
        table.addCell(v.getFechaLlegada().toString());
        table.addCell(v.getHoraLlegada().toString());
    }

    document.add(table);
    document.close();
}
public static void exportarBoletoPDF(Cliente cliente, Vuelo vuelo, String rutaArchivo) throws FileNotFoundException, DocumentException {
    Document documento = new Document(PageSize.A5);
    PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
    documento.open();

    Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
    Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);

    documento.add(new Paragraph("Boleto de Vuelo\n\n", tituloFont));

    documento.add(new Paragraph("Cliente:", tituloFont));
    documento.add(new Paragraph(cliente.getNombres() + " " + cliente.getApellidos(), normalFont));
    documento.add(new Paragraph("\n"));

    documento.add(new Paragraph("Detalles del Vuelo:", tituloFont));
    documento.add(new Paragraph("ID Vuelo: " + vuelo.getId(), normalFont));
    documento.add(new Paragraph("Salida: " + vuelo.getCiudadSalida() + " - " + vuelo.getSalida(), normalFont));
    documento.add(new Paragraph("Llegada: " + vuelo.getCiudadLlegada() + " - " + vuelo.getLlegada(), normalFont));
    documento.add(new Paragraph("Costo: $" + vuelo.getCostoBoleto(), normalFont));
    documento.add(new Paragraph("\n"));

    documento.add(new Paragraph("¡Gracias por elegirnos! Buen viaje.", normalFont));

    documento.close();
}

    
}


  