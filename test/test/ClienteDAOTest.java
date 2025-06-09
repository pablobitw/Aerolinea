package test;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import aerolineaproyecto.modelo.pojo.Cliente;
import aerolineaproyecto.modelo.dao.ClienteDAO;

public class ClienteDAOTest {

    @Test
    public void testCargarClientesNoNull() {
        List<Cliente> clientes = ClienteDAO.cargarClientes();
        assertNotNull("La lista de clientes no debe ser null", clientes);
    }

    @Test
    public void testAgregarClienteYGuardar() {
        List<Cliente> clientes = ClienteDAO.cargarClientes();
        int initialSize = clientes.size();

        Cliente nuevo = new Cliente();
        nuevo.setNombres("Test");
        nuevo.setApellidos("User");
        nuevo.setNacionalidad("Testland");
        nuevo.setFechaNacimiento(java.time.LocalDate.of(1990, 1, 1));

        clientes.add(nuevo);
        ClienteDAO.guardarClientes(clientes);

        List<Cliente> clientesDespues = ClienteDAO.cargarClientes();
        assertEquals("La lista debe tener un cliente más después de guardar", initialSize + 1, clientesDespues.size());

        // Limpiar: eliminar el cliente de prueba para no afectar futuras pruebas
        clientesDespues.removeIf(c -> "Test".equals(c.getNombres()) && "User".equals(c.getApellidos()));
        ClienteDAO.guardarClientes(clientesDespues);
    }
}
