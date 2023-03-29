package edu.wpi.romanticraijuu;

public class FurnitureFields extends FormFields {
  private String furniture;

  public FurnitureFields(String name, String location, String notes, String furniture) {
    super(name, location, notes);
    this.furniture = furniture;
  }

  public FurnitureFields(String name, String location, String furniture) {
    super(name, location);
    this.furniture = furniture;
  }

  public String getFurniture() {
    return furniture;
  }
}
