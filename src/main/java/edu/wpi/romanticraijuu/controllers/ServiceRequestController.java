package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField nameField;
  @FXML MFXTextField locationField;
  @FXML MFXTextField notesField;

  private String name;
  private String location;
  private String notes;

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    clearButton.setOnMouseClicked(event -> clear());
    submitButton.setOnMouseClicked(event -> submit());
  }

  @FXML
  public void submit() {
    name = nameField.getText();
    location = locationField.getText();
    notes = notesField.getText();
    System.out.println(name + " " + location + " " + notes);
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  public void clear() {
    nameField.clear();
    locationField.clear();
    notesField.clear();
  }
}
