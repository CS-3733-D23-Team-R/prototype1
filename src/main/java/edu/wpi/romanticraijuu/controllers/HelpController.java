package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HelpController extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @FXML MFXButton helpButton;

  @FXML
  public void initialize() {
    helpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  public void close(Stage primaryStage) {
    primaryStage.close();
  }

  @Override
  public void start(Stage primaryStage) {}
}
