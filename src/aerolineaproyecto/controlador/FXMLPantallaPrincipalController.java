
package aerolineaproyecto.controlador;

import aerolineaproyecto.AerolineaProyecto;
import aerolineaproyecto.modelo.pojo.Empleado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class FXMLPantallaPrincipalController implements Initializable {

    @FXML
    private Label lbNombre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
         {
        try{
            Stage escenarioBase= (Stage) lbNombre.getScene().getWindow();
            FXMLLoader cargador= new FXMLLoader(AerolineaProyecto.class.getResource("vista/FXMLInicioSesion.fxml"));
            Parent vista= cargador.load();
            Scene escenaInicio = new Scene(vista);
            escenarioBase.setScene(escenaInicio);
            escenarioBase.setTitle("InicioSesion");
            escenarioBase.centerOnScreen();
        }catch(IOException e){
            e.getMessage();
            e.printStackTrace();
            }
        }
    }
    private Empleado empleado;

    public void inicializarEmpleado(Empleado empleado) {
    this.empleado = empleado;
    lbNombre.setText("Bienvenido(a), " + empleado.getNombre());
        }

    @FXML
    private void btnIrBoletos(ActionEvent event) {
    }

    @FXML
    private void btnIrVuelos(ActionEvent event) {
        try {
        Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();

        // cargo la nueva vista
        FXMLLoader loader = new FXMLLoader(AerolineaProyecto.class.getResource("vista/FXMLVuelos.fxml"));
        Parent vistaVuelos = loader.load();
        Scene escenaVuelos = new Scene(vistaVuelos);
        escenarioBase.setScene(escenaVuelos);
        escenarioBase.setTitle("Gestión de Vuelos");
        escenarioBase.centerOnScreen();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al cargar la vista de Vuelos: " + e.getMessage());
    }
    }

    @FXML
    private void btnIrAerolineas(ActionEvent event) {
        try {
        Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();

        // cargo la nueva vista
        FXMLLoader loader = new FXMLLoader(AerolineaProyecto.class.getResource("vista/FXMLAerolineas.fxml"));
        Parent vistaEmpleados = loader.load();
        Scene escenaEmpleados = new Scene(vistaEmpleados);
        escenarioBase.setScene(escenaEmpleados);
        escenarioBase.setTitle("Gestión de Aerolineas");
        escenarioBase.centerOnScreen();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al cargar la vista de Aerolineas: " + e.getMessage());
    }
    }

    @FXML
    private void btnIrEmpleados(ActionEvent event) {
    try {
        Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();

        // cargo la nueva vista
        FXMLLoader loader = new FXMLLoader(AerolineaProyecto.class.getResource("vista/FXMLEmpleados.fxml"));
        Parent vistaEmpleados = loader.load();

        // creo la nueva escena y la asigno
        Scene escenaEmpleados = new Scene(vistaEmpleados);
        escenarioBase.setScene(escenaEmpleados);
        escenarioBase.setTitle("Gestión de Empleados");
        escenarioBase.centerOnScreen();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al cargar la vista de empleados: " + e.getMessage());
    }
}

    @FXML
    private void btnIrAviones(ActionEvent event){
        try {
     
        Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AerolineaProyecto.class.getResource("vista/FXMLAviones.fxml"));
        Parent vistaEmpleados = loader.load();
        Scene escenaEmpleados = new Scene(vistaEmpleados);
        escenarioBase.setScene(escenaEmpleados);
        escenarioBase.setTitle("Gestión de Aviones");
        escenarioBase.centerOnScreen();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al cargar la vista de Aviones: " + e.getMessage());
    }
    }
        
    

    @FXML
    private void btnIrClientes(ActionEvent event) {
        try {
     
        Stage escenarioBase = (Stage) lbNombre.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(AerolineaProyecto.class.getResource("vista/FXMLClientes.fxml"));
        Parent vistaEmpleados = loader.load();
        Scene escenaEmpleados = new Scene(vistaEmpleados);
        escenarioBase.setScene(escenaEmpleados);
        escenarioBase.setTitle("Gestión de Clientes");
        escenarioBase.centerOnScreen();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al cargar la vista de Clientes: " + e.getMessage());
    }
    }
    }

