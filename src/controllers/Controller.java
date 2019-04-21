package controllers;

/**
 *
 * @author Alec Agnese, Rami El Khatib
 */
public interface Controller {
  public void setParentController(TopController mainVC);
  public void updateParentController();
  public void back();
}
