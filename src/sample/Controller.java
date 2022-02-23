package sample;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

public class Controller {

    @FXML
    private Label pr1;

    @FXML
    private Button bttmStart;

    @FXML
    private RadioButton rdbt4;

    @FXML
    private Button Empezar;

    @FXML
    private Button term;

    @FXML
    private Button dc;

    @FXML
    private Button mv;

    @FXML
    private Button sig;

    @FXML
    private RadioButton rdbt1;

    @FXML
    private Label introducir;

    @FXML
    private Label miembrosGrupo;

    @FXML
    private Label nombrePersonaje;

    @FXML
    private ImageView fotoPersonaje;

    @FXML
    private ImageView fondoFinalDC;

    @FXML
    private ImageView fondoFinalMarvel;

    @FXML
    private ImageView fondoMarvel;

    @FXML
    private ImageView fondoNombre;

    @FXML
    private ImageView fondoDC;

    @FXML
    private RadioButton rdbt3;

    @FXML
    private ImageView imagen1;

    @FXML
    private RadioButton rdbt2;

    @FXML
    private TextField nombre;

    @FXML
    private Button easteregg;

    private Bd b1 = new Bd();
    private int contador = 0;
    private int contador2 = 0;
    private String universo = "";
    private String nombreUsuario = "";
    private int puntuacionUsuario = 0;
    private int puntosPersonaje = 0;

    public void activeButton() {

        Alert alert = new Alert(AlertType.ERROR);

        ToggleGroup group1 = new ToggleGroup();

        try {

            // Mete los botones a un grupo de propiedad ToggleGroup. Sirve para que solo
            // seleccione 1 bot�n de un grupo (no permite seleccionar m�s de 1 a la vez)
            rdbt1.setToggleGroup(group1);
            rdbt2.setToggleGroup(group1);
            rdbt3.setToggleGroup(group1);
            rdbt4.setToggleGroup(group1);

        } catch (Exception e) {

            alert.setHeaderText(null);
            alert.setContentText(e.getCause().toString());
            alert.showAndWait();
        }
    }

    public void Empezar() throws SQLException {

        Alert alert = new Alert(AlertType.ERROR);

        try {

            if (universo != "") {

                // Pasar a la secci�n de preguntas y respuestas del cuestionario
                bttmStart.setVisible(false);
                imagen1.setVisible(false);
                fondoNombre.setVisible(true);
                introducir.setVisible(true);
                nombre.setVisible(true);
                Empezar.setVisible(true);

                mv.setVisible(false);
                dc.setVisible(false);

                b1.resetearUniverso();
                b1.guardarUniverso(universo);
            }
        } catch (Exception e) {

            alert.setHeaderText(null);
            alert.setContentText(e.getCause().toString());
            alert.showAndWait();
        }
    }

    public void Empezar2() throws SQLException {

        Alert alert = new Alert(AlertType.ERROR);

        try {

            if (nombre.getText() != "") {
                // Pasar a la secci�n de preguntas y respuestas del cuestionario
                introducir.setVisible(false);
                nombre.setVisible(false);
                fondoNombre.setVisible(false);
                pr1.setVisible(true);
                rdbt1.setVisible(true);
                rdbt2.setVisible(true);
                rdbt3.setVisible(true);
                rdbt4.setVisible(true);
                Empezar.setVisible(false);
                sig.setVisible(true);

                if (universo == "Marvel") {
                    fondoMarvel.setVisible(true);
                } else if (universo == "DC") {
                    fondoDC.setVisible(true);
                }

                contador = b1.insertar(pr1, rdbt1, rdbt2, rdbt3, rdbt4, contador);
                b1.nombreUsuario(nombre);

                System.out.println("EL NOMBRE DEL USUARIO A SIDO INTRODUCIDO CORRECTAMENTE");
            }
        } catch (Exception e) {

            alert.setHeaderText(null);
            alert.setContentText(e.getCause().toString());
            alert.showAndWait();
        }
    }

    public void respuestasUsuario() {

        try {
            System.out.println("Entra en el metodo RESPUESTASUSUARIO");
            System.out.println("El contador vale: " + contador2);

            if (rdbt1.isSelected()) {
                puntuacionUsuario += b1.puntuacionUsuarioPorPregunta(rdbt1);
            } else if (rdbt2.isSelected()) {
                puntuacionUsuario += b1.puntuacionUsuarioPorPregunta(rdbt2);
            } else if (rdbt3.isSelected()) {
                puntuacionUsuario += b1.puntuacionUsuarioPorPregunta(rdbt3);
            } else if (rdbt4.isSelected()) {
                puntuacionUsuario += b1.puntuacionUsuarioPorPregunta(rdbt4);
            }
            System.out.println("La puntuacion que lleva el usuario es: " + puntuacionUsuario);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarMiembros() {
        try {
            miembrosGrupo.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void terminar() throws SQLException {

        Alert alert = new Alert(AlertType.ERROR);

        try {
            if (universo == "Marvel") {
                fondoMarvel.setVisible(false);
            } else if (universo == "DC") {
                fondoDC.setVisible(false);
            }

            if (universo == "Marvel") {
                fondoFinalMarvel.setVisible(true);
            } else if (universo == "DC") {
                fondoFinalDC.setVisible(true);
            }
            easteregg.setVisible(true);
            sig.setVisible(false);
            pr1.setVisible(false);
            rdbt1.setVisible(false);
            rdbt2.setVisible(false);
            rdbt3.setVisible(false);
            rdbt4.setVisible(false);

            term.setVisible(false);

            System.out.println("Entra en el metodo TERMINAR");
            respuestasUsuario(); // tiene que imprimir el syso de la puntuacion que lleva el usuario
            contador2++;
            System.out.println("El usuario tiene-> " + puntuacionUsuario);
            puntosPersonaje = b1.compararPuntos(puntuacionUsuario);
            System.out.println("Los puntos del personaje mas parecido son-> " + puntosPersonaje);
            b1.mostrarPersonaje(puntosPersonaje, nombrePersonaje, fotoPersonaje, nombreUsuario);

            fotoPersonaje.setVisible(true);
            nombrePersonaje.setVisible(true);

            b1.resetearUniverso();

        } catch (Exception e) {

            alert.setHeaderText(null);
            alert.setContentText(e.getCause().toString());
            alert.showAndWait();
        }

    }

    public void Siguiente() throws SQLException {

        Alert alert = new Alert(AlertType.ERROR);
        String genero = "";

        try {
            if (rdbt1.isSelected() || rdbt2.isSelected() || rdbt3.isSelected() || rdbt4.isSelected()) {
                contador = b1.insertar(pr1, rdbt1, rdbt2, rdbt3, rdbt4, contador);
                System.out.println("Entra en el metodo SIGUIENTE");
                respuestasUsuario();

                if (contador2 == 0) {
                    System.out.println("ESTA ES LA PRIMERA PREGUNTA, YA QUE CONTADOR VALE " + contador2);
                    if (rdbt1.isSelected()) {
                        genero = "H";
                        b1.descartarPersonajes(genero);
                    } else if (rdbt2.isSelected()) {
                        genero = "M";
                        b1.descartarPersonajes(genero);
                    }
                }
                contador2++;

                if (contador2 == 9) {
                    sig.setVisible(false);
                    term.setVisible(true);
                }

                if (rdbt1.isSelected()) {
                    rdbt1.setSelected(false);
                } else if (rdbt2.isSelected()) {
                    rdbt2.setSelected(false);
                } else if (rdbt3.isSelected()) {
                    rdbt3.setSelected(false);
                } else if (rdbt4.isSelected()) {
                    rdbt4.setSelected(false);
                }
            }
        } catch (Exception e) {
            alert.setHeaderText(null);
            alert.setContentText(e.getCause().toString());
            alert.showAndWait();
        }
    }

    public void guardarMarvel() {
        try {
            universo = "Marvel";

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarDC() {
        try {
            universo = "DC";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * public void Siguiente() throws SQLException {
     *
     * int total;
     *
     * Alert alert = new Alert(AlertType.ERROR);
     *
     * try {
     *
     * rdbt1.setSelected(false); rdbt2.setSelected(false); rdbt3.setSelected(false);
     * rdbt4.setSelected(false);
     *
     * contador = b1.insertar(pr1, rdbt1, rdbt2, rdbt3, rdbt4, contador);
     *
     * total = b1.preguntasTotal();
     *
     * // Si el total de preguntas es la misma que el contador actual, entonces
     * aparece // el bot�n terminar if (total == contador) { sig.setVisible(false);
     * term.setVisible(true); }
     *
     * } catch (Exception e) {
     *
     * alert.setHeaderText(null); alert.setContentText(e.getCause().toString());
     * alert.showAndWait(); } }
     */

}