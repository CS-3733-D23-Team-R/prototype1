package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class ServiceRequestController {

  @FXML MFXButton cancelButton;
  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField nameField;
  @FXML MFXTextField locationField;
  @FXML MFXTextField notesField;
  @FXML ChoiceBox mealTypeBox;

  private String name;
  private String location;
  private String notes;
  private String mealType;

  ObservableList<String> mealTypeList =
      FXCollections.observableArrayList("Chicken", "Beef", "Fish");

  @FXML
  public void initialize() {
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    clearButton.setOnMouseClicked(event -> clear());
    submitButton.setOnMouseClicked(event -> submit());
    mealTypeBox.setValue("Select Meal");
    mealTypeBox.setItems(mealTypeList);
  }

  @FXML
  public void submit() {
    name = nameField.getText();
    location = locationField.getText();
    mealType = mealTypeBox.getValue().toString();
    if (mealType == "Select Meal") {
      mealType = "";
    }
    notes = notesField.getText();
    System.out.println(name + " " + location + " " + mealType + " " + notes);
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  public void clear() {
    nameField.clear();
    locationField.clear();
    notesField.clear();
  }
}
