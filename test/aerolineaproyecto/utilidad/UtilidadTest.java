package aerolineaproyecto.utilidad;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilidadTest {

    @Test
    public void testCifrarPassword_ValorConocido() {
        String pass = "12345";
        String esperado = "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5";
        String resultado = Utilidad.cifrarPassword(pass);
        assertEquals(esperado, resultado);
    }

    @Test
    public void testCifrarPassword_NullSafe() {
        String resultado = Utilidad.cifrarPassword(null);

        try {
            Utilidad.cifrarPassword(null);
            fail("Se esperaba NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testMostrarAlertaSimple_NoLanzaExcepcion() {
        try {
            Utilidad.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Prueba", "Contenido de prueba");
        } catch (Exception e) {
            fail("No se esperaba excepción al mostrar alerta: " + e.getMessage());
        }
    }


}
