package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class Bd {

        ArrayList<Integer> listaPuntuaciones = new ArrayList<>();
        private Statement sentenciaSQL = null;
        private Connection conexion = null;
        private ResultSet result;
        private String sql1;

        final void abrirConexion() throws SQLException {
            // Conectar a la base de datos

            try {
                // Para conectarse a mysql mediante jdbc (Java DataBase Connection)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Coge la base de datos que se especifica despu�s de localhost. En donde root
                // se escribe el nombre de administrador y donde
                // est� vac�o hay que poner la contrase�a
                conexion = DriverManager.getConnection("jdbc:mysql://localhost/marveldc", "root", "1234");

            } catch (ClassNotFoundException cn) {
                cn.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("Conectado");
            }
        }

        final void cerrarConexion() throws SQLException {
            // Desconectar de la base de datos

            try {
                sentenciaSQL.close();
                conexion.close();
            } catch (SQLException ex) {
                System.out.println("Eeerrorrr");
            } finally {
                System.out.println("Desconectado");
            }
        }

        public void resetearUniverso() throws SQLException {

            int result1;

            try {

                abrirConexion();

                sentenciaSQL = conexion.createStatement();

                // Borra y resetea toda la tabla guardado
                sql1 = "truncate table seleccionados;";

                result1 = sentenciaSQL.executeUpdate(sql1);

                // Imprime el mensaje si se ha reseteado la tabla guardado
                if (result1 >= -1) {

                    System.out.println("Se ha reseteado bien la tabla 'seleccionados'");

                }

            } catch (SQLException ex) {
                System.out.println("Error");
            } finally {
                try {
                    cerrarConexion();
                } catch (SQLException ex) {
                    System.out.println("Error");
                }
            }
        }

        public int insertar(Label Pregunta1, RadioButton BOTON1A, RadioButton BOTON2A, RadioButton BOTON3A,
                            RadioButton BOTON4A, int contador) throws SQLException {

            int contador2 = 0;

            try {
                abrirConexion();

                sentenciaSQL = conexion.createStatement();

                /*
                 * Selecciona todo de la tabla preguntas unida a la tabla respuestas en la
                 * condici�n de que tengan la misma id correspondientes.
                 */
                sql1 = "SELECT * from preguntas join respuestas on fidpregunta = idpreguntas order by idpreguntas;";

                result = sentenciaSQL.executeQuery(sql1);

                // Si el contador es 0, entonces genera todo lo correspondiente para la primera
                // pregunta
                if (contador == 0) {

                    // Bucle que sirve para colocar la primera pregunta y sus respuestas
                    while (result.next() && contador == result.getInt(1) || contador == 0) {

                        if ((contador != result.getInt(1) && contador < result.getInt(1)) && contador2 == 0) {
                            contador = result.getInt(1);
                        }

                        contador2++;

                        if (contador == result.getInt(1)) {
                            Pregunta1.setText(result.getString("pregunta"));

                            // Posiciona las respuestas
                            switch (contador2) {
                                case 1:
                                    BOTON1A.setText(result.getString("respuestas"));
                                    break;

                                case 2:
                                    BOTON2A.setText(result.getString("respuestas"));
                                    BOTON3A.setVisible(false);
                                    BOTON4A.setVisible(false);
                                    break;

                                case 3:
                                    BOTON3A.setVisible(true);
                                    BOTON3A.setText(result.getString("respuestas"));
                                    BOTON4A.setVisible(false);
                                    break;

                                case 4:
                                    BOTON4A.setVisible(true);
                                    BOTON4A.setText(result.getString("respuestas"));
                                    break;
                            }
                        }
                    }

                } else {

                    // Bucle que repite hasta llegar hasta la �ltima posici�n generada
                    do {

                        if (result.next()
                                && ((contador != result.getInt(1) && contador <= result.getInt(1)) && contador2 == 0)) {

                            contador = result.getInt(1);
                            contador2++;
                        }

                    } while ((contador >= result.getInt(1)) && contador2 == 0);

                    do {

                        if (contador == result.getInt(1)) {

                            Pregunta1.setText(result.getString("pregunta"));

                            // Posiciona las respuestas
                            switch (contador2) {
                                case 1:
                                    BOTON1A.setText(result.getString("respuestas"));
                                    break;

                                case 2:
                                    BOTON2A.setText(result.getString("respuestas"));
                                    BOTON3A.setVisible(false);
                                    BOTON4A.setVisible(false);
                                    break;

                                case 3:
                                    BOTON3A.setVisible(true);
                                    BOTON3A.setText(result.getString("respuestas"));
                                    BOTON4A.setVisible(false);
                                    break;

                                case 4:
                                    BOTON4A.setVisible(true);
                                    BOTON4A.setText(result.getString("respuestas"));
                                    break;
                            }

                            contador2++;
                        }

                    } while (result.next() && (contador == result.getInt(1)));
                }

            } catch (SQLException ex) {
                System.out.println("ERROR");
            } finally {
                try {
                    cerrarConexion();
                } catch (SQLException ex) {
                    System.out.println("Error");
                }
                return contador;
            }
        }

        public void guardarUniverso(String universo) throws SQLException {

            int result1;

            try {

                abrirConexion();

                sentenciaSQL = conexion.createStatement();

                // Selecciona 5 preguntas al azar de la tabla preguntas y las guarda
                sql1 = "INSERT INTO seleccionados (idpersonajes, nombre, foto, universo, puntos, genero) SELECT idpersonaje, nombre, foto, universo, puntos, genero FROM personajes where universo = '"
                        + universo + "';";

                result1 = sentenciaSQL.executeUpdate(sql1);

                // Se muestra en la consola si se ha metido las preguntas correctamente
                if (result1 >= 1) {
                    System.out.println("Se ha seleccionado y guardado correctamente las preguntas");
                }

            } catch (SQLException ex) {
                System.out.println("Error");
            } finally {
                try {
                    cerrarConexion();
                } catch (SQLException ex) {
                    System.out.println("Error");
                }
            }

        }

        public String nombreUsuario(TextField texto) {
            String nombre = "";
            try {
                nombre = texto.getText();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nombre;
        }

        // descartar en base al género del personaje comparado con el género elegido
        public void descartarPersonajes(String genero){ // se pasa por parámetro el radioButton que haya sido seleccionado

            int result;

            try {

                System.out.println(genero);

                abrirConexion();
                sentenciaSQL = conexion.createStatement();
                sql1 = "DELETE FROM seleccionados WHERE genero !='" + genero + "';";
                result = sentenciaSQL.executeUpdate(sql1);

                if(result>=1){
                    System.out.println("Se han descartado los personajes cuyo genero no ha sido seleccionado");
                }

                cerrarConexion();

            } catch (SQLException ex) {
                System.out.println("ERROR al eliminar por genero");
            }
        }

        public int puntuacionUsuarioPorPregunta(RadioButton rbtn){

            int puntuacionUsuario=0;
            String textoRespuesta;


            try {

                textoRespuesta=rbtn.getText();

                abrirConexion();
                sentenciaSQL = conexion.createStatement();
                sql1 = "SELECT `puntosRespuestas` FROM `respuestas` WHERE (`respuestas` ='" + textoRespuesta + "');";
                result = sentenciaSQL.executeQuery(sql1);

                if(result.next()) {
                    puntuacionUsuario = result.getInt("puntosRespuestas");
                }
                cerrarConexion();

            } catch (SQLException ex) {
                System.out.println("ERROR al cargar puntos del usuario");
            }
            return puntuacionUsuario;
        }

        public int compararPuntos(int puntuacionUsuario){

            int puntosPersonaje=0;
            ArrayList<Integer>listaPuntos = new ArrayList<>();
            int cercano = 0;
            int diferencia = Integer.MAX_VALUE;

            try {

                abrirConexion();
                sentenciaSQL = conexion.createStatement();
                sql1 = "SELECT `puntos` FROM `seleccionados`";
                result = sentenciaSQL.executeQuery(sql1);

                while (result.next()){
                    listaPuntos.add(result.getInt("puntos"));
                }

                //inicializado valor máximo de variable de tipo int
                for (int i = 0; i < listaPuntos.size(); i++) {
                    if (listaPuntos.get(i) == puntuacionUsuario) {
                        puntosPersonaje = listaPuntos.get(i);
                    } else {
                        if(Math.abs(listaPuntos.get(i)-puntuacionUsuario)<diferencia){
                            cercano=listaPuntos.get(i);
                            diferencia = Math.abs(listaPuntos.get(i)-puntuacionUsuario);
                            puntosPersonaje=cercano;
                        }
                    }
                }

                cerrarConexion();

            } catch (SQLException ex) {
                System.out.println("ERROR al cargar puntos del personaje");
            }
            return puntosPersonaje;
        }

        public void mostrarPersonaje(int puntosPersonaje, Label nombre, ImageView foto, String nombreusuario){

            String sql1;
            String direccionImagen;
            ResultSet result;

            try {

                System.out.println("Entra en el metodo MOSTRAR PERSONAJE");

                abrirConexion();
                sentenciaSQL = conexion.createStatement();
                sql1 = "SELECT nombre,foto FROM seleccionados WHERE (puntos ='" + puntosPersonaje + "');";
                result = sentenciaSQL.executeQuery(sql1);

                if(result.next()){

                    System.out.println("Entra en el (if result.next) del metodo MOSTRAR PERSONAJE");
                    System.out.println(result.getString("nombre"));
                    System.out.println(result.getString("foto"));

                    nombre.setText(/*nombreusuario + " te pareces a: " +*/ result.getString("nombre"));

                    direccionImagen=result.getString("foto");

                    File file = new File(direccionImagen);
                    Image image = new Image(file.toURI().toString());
                    foto.setImage(image);

                }

                cerrarConexion();

            } catch (SQLException ex) {
                System.out.println("ERROR al mostrar el personaje");
            }
        }

        public int preguntasTotal() throws SQLException {

            int total = 0;

            try {

                abrirConexion();

                sentenciaSQL = conexion.createStatement();

                sql1 = "SELECT count() from preguntas;";

                result = sentenciaSQL.executeQuery(sql1);

                if (result.next()) {
                    total = result.getInt(1);
                }

            } catch (SQLException ex) {
                System.out.println("ERROR");
            } finally {
                try {
                    cerrarConexion();
                } catch (SQLException ex) {
                    System.out.println("Error");
                }
                return total;
            }
        }


    }
