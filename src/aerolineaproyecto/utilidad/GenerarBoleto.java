package aerolineaproyecto.utilidad;
import aerolineaproyecto.modelo.pojo.Boleto;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.pojo.Vuelo;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
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
    public static void generarPDF(Cliente cliente, Vuelo vuelo, Boleto boleto, String rutaSalida) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(rutaSalida));
        document.open();

        
        Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLUE);
        Font subtituloFont = new Font(Font.HELVETICA, 16, Font.BOLD, Color.BLUE);
        Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);

        Paragraph titulo = new Paragraph("Boleto de Vuelo", tituloFont);
        titulo.setAlignment(Element.ALIGN_LEFT);
        document.add(titulo);
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Cliente:", subtituloFont));
        document.add(new Paragraph(cliente.getNombres(), normalFont));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Detalles del Vuelo:", subtituloFont));
        document.add(new Paragraph("ID Vuelo: " + vuelo.getId(), normalFont));
        document.add(new Paragraph("Salida: " + vuelo.getCiudadSalida() + " - " + vuelo.getSalida(), normalFont));
        document.add(new Paragraph("Llegada: " + vuelo.getCiudadLlegada() + " - " + vuelo.getLlegada(), normalFont));
        document.add(new Paragraph("Número de asiento: " + boleto.getNumAsiento(), normalFont));
        document.add(new Paragraph("Costo: $" + vuelo.getCostoBoleto(), normalFont));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("¡Gracias por elegirnos! Buen viaje.", normalFont));

        document.close();
    }
}

