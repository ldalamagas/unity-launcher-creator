package com.ulc.controllers;

import com.ulc.Main;
import com.ulc.model.Launcher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    private ObservableList<Launcher.LauncherType> launcherTypes = FXCollections.observableArrayList(
            Launcher.LauncherType.Application,
            Launcher.LauncherType.Link,
            Launcher.LauncherType.Directory);

    private File initialDirectory = null;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtIcon;

    @FXML
    private TextField txtExecutable;

    @FXML
    private ComboBox cmbType;

    @FXML
    private CheckBox chkTerminal;

    @FXML
    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbType.getItems().addAll(launcherTypes);
        cmbType.setValue(Launcher.LauncherType.Application);
        txtDescription.requestFocus();
    }

    @FXML
    public void onCreateClicked(ActionEvent actionEvent) {

        Launcher l = new Launcher(
                txtName.getText(),
                txtDescription.getText(),
                txtIcon.getText(),
                txtExecutable.getText(),
                (Launcher.LauncherType)cmbType.getValue(),
                chkTerminal.isSelected());

        logger.info("Creating launcher");

        Launcher.createLauncher(l);
    }

    @FXML
    public void onClearClicked(ActionEvent actionEvent) {
        txtName.clear();
        txtDescription.clear();
        txtIcon.clear();
        txtExecutable.clear();
        cmbType.setValue(Launcher.LauncherType.Application);
        chkTerminal.setSelected(false);
    }

    @FXML
    public void onExitClicked(ActionEvent actionEvent) {
        logger.info("Closing {}", Main.APP_NAME);
        Platform.exit();
    }

    public void onSelectIconClicked(ActionEvent actionEvent) {
        File selection = selectFile("Select launcher icon", initialDirectory);
        if (selection != null) {
            txtIcon.setText(selection.getAbsolutePath());
            initialDirectory = selection.getParentFile();
        }
    }

    public void onSelectExecutableClicked(ActionEvent actionEvent) {
        File selection = selectFile("Select launcher executable", initialDirectory);
        if (selection != null) {
            txtExecutable.setText(selection.getAbsolutePath());
            initialDirectory = selection.getParentFile();
        }
    }

    private File selectFile(String dialogTitle, File initialDirectory) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle(dialogTitle);
        if (initialDirectory != null) {
            chooser.setInitialDirectory(initialDirectory);
        }
        return chooser.showOpenDialog(stage);
    }
}
