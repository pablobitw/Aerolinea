package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class BoletoTest {

    @Test
    public void testConstructorYGetters() {
        Vuelo vuelo = new Vuelo();
        vuelo.setId(123);

        Cliente cliente = new Cliente();

        Date fecha = new Date();

        Boleto boleto = new Boleto("b001", 123, fecha, "12A", vuelo, cliente);

        assertEquals("b001", boleto.getId());
        assertEquals(123, boleto.getIdVuelo());
        assertEquals(fecha, boleto.getFechaCompra());
        assertEquals("12A", boleto.getNumAsiento());
        assertEquals(vuelo, boleto.getVuelo());
        assertEquals(cliente, boleto.getCliente());
    }

    @Test
    public void testSettersYIdVuelo() {
        Boleto boleto = new Boleto();

        boleto.setId("b002");
        boleto.setNumAsiento("14C");
        Date fecha = new Date();
        boleto.setFechaCompra(fecha);

        assertEquals("b002", boleto.getId());
        assertEquals("14C", boleto.getNumAsiento());
        assertEquals(fecha, boleto.getFechaCompra());

        boleto.setIdVuelo(50);
        assertEquals(50, boleto.getIdVuelo());

        Vuelo vuelo = new Vuelo();
        vuelo.setId(99);
        boleto.setVuelo(vuelo);

        assertEquals(vuelo, boleto.getVuelo());
        assertEquals(99, boleto.getIdVuelo());

        boleto.setVuelo(null);
        assertNull(boleto.getVuelo());
        assertEquals(0, boleto.getIdVuelo());
    }
}
