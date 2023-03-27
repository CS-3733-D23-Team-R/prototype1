package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton cancelButton;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
