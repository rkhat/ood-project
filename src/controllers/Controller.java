package controllers;

/**
 * Controller interface for all the child pages
 * 
 * @author Alec Agnese, Rami El Khatib
 */
public interface Controller {
  /**
   * Set the parent controller.
   * 
   * @param parentController parent controller
   */
  public void setParentController(TopController parentController);

  /**
   * Updates parent controller. This should be called after the parent
   * controller is set. This method is mostly used to modify the toolbar on the
   * parent controller.
   */
  public void updateParentController();

  /**
   * Action to perform when back is pressed.
   */
  public void back();
  
  /**
   * Reset focus to default node
   */
  public void focus();
}
