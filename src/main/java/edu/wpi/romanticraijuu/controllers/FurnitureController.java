package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class FurnitureController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
