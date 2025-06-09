/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aerolineaproyecto.utilidad;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Vuelo;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import java.awt.Color;



/**
 *
 * @author PABLO
 */



public class GenerarBoleto {

    public static void generarPDF(Cliente cliente, Vuelo vuelo, String rutaSalida) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
        document.open();

        Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        document.add(new Paragraph("BOLETO DE VUELO", tituloFont));
        document.add(new Paragraph(" ")); // Línea vacía

        document.add(new Paragraph("Cliente: " + cliente.getNombres(), normalFont));
        document.add(new Paragraph("ID de Vuelo: " + vuelo.getId(), normalFont));
        document.add(new Paragraph("Ciudad de salida: " + vuelo.getCiudadSalida(), normalFont));
        document.add(new Paragraph("Ciudad de llegada: " + vuelo.getCiudadLlegada(), normalFont));
        document.add(new Paragraph("Fecha y hora de salida: " + vuelo.getSalida(), normalFont));
        document.add(new Paragraph("Hora de llegada: " + vuelo.getLlegada(), normalFont));
        document.add(new Paragraph("Costo: $" + vuelo.getCostoBoleto(), normalFont));

        document.add(new Paragraph("\n¡Gracias por su compra! ✈️", normalFont));

        document.close();
    }
}
