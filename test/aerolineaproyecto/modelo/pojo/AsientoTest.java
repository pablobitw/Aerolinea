package aerolineaproyecto.modelo.pojo;

import org.junit.Test;
import static org.junit.Assert.*;

public class AsientoTest {

    @Test
    public void testConstructorYGetters() {
        Asiento asiento = new Asiento("12A", true, "Económica");

        assertEquals("12A", asiento.getNumeroAsiento());
        assertTrue(asiento.getOcupado());
        assertEquals("Económica", asiento.getClaseAsiento());
        assertEquals(12, asiento.getFila());
        assertEquals("A", asiento.getColumna());
    }

    @Test
public void testSetters() {
    Asiento asiento = new Asiento("10B", false, "Business");

    asiento.setNumeroAsiento("15C");
    asiento.setEstaOcupado(true);
    asiento.setClaseAsiento("Primera");

    assertEquals("15C", asiento.getNumeroAsiento());
    assertTrue(asiento.getOcupado());
    assertEquals("Primera", asiento.getClaseAsiento());

    assertEquals(10, asiento.getFila());
    assertEquals("B", asiento.getColumna());
}

}
