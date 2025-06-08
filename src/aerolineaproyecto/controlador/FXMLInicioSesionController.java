package aerolineaproyecto.controlador;

import aerolineaproyecto.modelo.dao.SesionManagerDAO;
import aerolineaproyecto.modelo.pojo.Empleado;
import static com.sun.javafx.scene.control.skin.Utils.getResource;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pablo Silva
 */
public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfContraseña;
    @FXML
    private Label lbErrorUsuario;
    @FXML
    private Label lbErrorPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnIniciarSesion(ActionEvent event) {
        String user = tfUsuario.getText();
        String pass = tfContraseña.getText();
        boolean camposValidos = true;
        lbErrorUsuario.setText("");
        lbErrorPassword.setText("");
        if (user == null || user.equals("")) {
            lbErrorUsuario.setText("Ingresa correctamente los datos.");
            camposValidos = false;
        }
        if (pass == null || pass.equals("")) {
            lbErrorPassword.setText("Ingresa correctamente los datos.");
            camposValidos = false;
        }
        if (!camposValidos) {
            return;
        }
        Empleado empleado = SesionManagerDAO.verificarCredenciales(user, pass);

        if (empleado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/aerolineaproyecto/vista/FXMLPantallaPrincipal.fxml"));
                Parent root = loader.load();
            FXMLPantallaPrincipalController controlador = loader.getController();
            controlador.inicializarEmpleado(empleado);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            } catch (Exception e) {
                lbErrorUsuario.setText("Error al cargar la pantalla principal.");
                e.printStackTrace();
            }
        } else {
            lbErrorUsuario.setText("");
            lbErrorPassword.setText("Usuario o contraseña incorrectos.");
        }
    }
    
}
