package controllers;

import customExceptions.EmptyDataException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXComboBox;
import javafx.stage.Stage;
import model.Leader;
import model.Match;
import model.Player;
import com.jfoenix.controls.JFXTextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Clase

/**
 * Entidad que representa el controlador de la ventana de selcción del líder.
 */
public class SelectionWindowController implements Initializable {

    // Atributos

    /**
     * Es la imagen perteneciente al lider de tipo Dominador.
     */
    @FXML private ImageView dominatorImage;

    /**
     * Es la imagen perteneciente al lider de tipo Protector.
     */
    @FXML private ImageView protectorImage;

    /**
     * Es la imagen perteneciente al lider de tipo Estratega.
     */
    @FXML private ImageView strategistImage;

    /**
     * Es el compo de texto para el nombre de la facción.
     */
    @FXML private JFXTextField tfFactionName;

    /**
     * Es el campo de texto para el número de miembros.
     */
    @FXML private JFXTextField tfMembers;

    /**
     * Es el campo de texto para el nombre del liíer.
     */
    @FXML private JFXTextField tfCaptainName;

    /**
     * Es el menú desplegable para indicar el nivel de experiencia del líder.
     */
    @FXML private JFXComboBox<Leader.LevelExperience> cbExperienceLevel;

    // Relación

    /**
     * Es el jugador.
     */
    private Player player;

    // Métodos

    /**
     * Inicializa los valores que muestran en el menú desplegable de la experiencia del líder.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbExperienceLevel.getItems().addAll(Leader.LevelExperience.LOW, Leader.LevelExperience.MEDIUM, Leader.LevelExperience.HIGHT);
    }

    /**
     * Cambia el jugador.
     * @param player - El nuevo jugador.
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Controla la acción de presionar el botón dedicado a crear una partida con el lider de tipo Dominador.
     * @param event - Es el evento producido al presionar el botón.
     */
    @FXML
    void dominatorClicked(MouseEvent event) {

        try {
            int members = Integer.parseInt(tfMembers.getText());
            player.getMatch().createUserFaction(tfFactionName.getText(), members, Match.LeaderType.DOMINATOR, tfCaptainName.getText(), cbExperienceLevel.getValue());
            chooseTime(cbExperienceLevel.getValue());
            startGameBoard(event);

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Causado por:\n" + "Espacio en banco o caracter invalido digitado", ButtonType.CLOSE);
            alert.setHeaderText("La cantidad de miembros de la nueva faccion debe ser un numero");
            alert.show();

        }catch (EmptyDataException e){
            e.message();
        }
    }

    /**
     * Controla la acción de presionar el botón dedicado a crear una partida con el lider de tipo Estratega.
     * @param event - Es el evento producido al presionar el botón.
     */
    @FXML
    void estrategistClicked(MouseEvent event) {
        try {
            int members = Integer.parseInt(tfMembers.getText());
            player.getMatch().createUserFaction(tfFactionName.getText(), members, Match.LeaderType.STRATEGIST, tfCaptainName.getText(), cbExperienceLevel.getValue());
            chooseTime(cbExperienceLevel.getValue());
            startGameBoard(event);

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Causado por:\n" + "Espacio en banco o caracter invalido digitado", ButtonType.CLOSE);
            alert.setHeaderText("La cantidad de miembros de la nueva faccion debe ser un numero");
            alert.show();

        }catch (EmptyDataException e){
            e.message();
        }
    }

    /**
     * Controla la acción de presionar el botón dedicado a crear una partida con el lider de tipo Protector.
     * @param event - Es el evento producido al presionar el botón.
     */
    @FXML
    void protectorClicked(MouseEvent event) {
        try {
            int members = Integer.parseInt(tfMembers.getText());
            player.getMatch().createUserFaction(tfFactionName.getText(), members, Match.LeaderType.PROTECTOR, tfCaptainName.getText(), cbExperienceLevel.getValue());
            chooseTime(cbExperienceLevel.getValue());
            startGameBoard(event);

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Causado por:\n" + "Espacio en banco o caracter invalido digitado", ButtonType.CLOSE);
            alert.setHeaderText("La cantidad de miembros de la nueva faccion debe ser un numero");
            alert.show();

        }catch (EmptyDataException e){
            e.message();
        }
    }

    /**
     * Inicializa la ventana de juego, donde se enfrentaran jugador y maquina.
     * @param event Es el evento al presionar el boton
     */
    private void startGameBoard(MouseEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/userInterface/GameBoardGUI.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        GameBoardController gameBoardController = loader.getController();
        gameBoardController.setPlayer(this.player);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Star Battle Game");
        stage.show();
    }


    private void chooseTime(Leader.LevelExperience levelExperience){
        switch (levelExperience){
            case LOW:
                player.getMatch().setTime(Match.DURATION_MATCH_LOW_LEVEL);
                break;
            case MEDIUM:
                player.getMatch().setTime(Match.DURATION_MATCH_MEDIUM_LEVEL);
                break;
            case HIGHT:
                player.getMatch().setTime(Match.DURATION_MATCH_HIGH_LEVEL);
        }
    }
}
