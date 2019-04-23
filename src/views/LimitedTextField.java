package views;

import java.util.Objects;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Text field with limited number of characters that can be inserted into it.
 * 
 * @author Alec Agnese, Rami El Khatib
 *
 */
public class LimitedTextField extends JFXTextField {

  private final IntegerProperty maxLength;

  /**
   * Initialize LimitedTextField with default value
   */
  public LimitedTextField() {
    super();
    this.maxLength = new SimpleIntegerProperty(-1);
  }

  /**
   * Get max length property
   * 
   * @return max length property
   */
  public IntegerProperty maxLengthProperty() {
    return this.maxLength;
  }

  /**
   * Get max length of text field
   * 
   * @return max length of text field
   */
  public final Integer getMaxLength() {
    return this.maxLength.getValue();
  }

  /**
   * Set max length of text field
   * 
   * @param maxLength max length to set
   */
  public final void setMaxLength(Integer maxLength) {
    Objects.requireNonNull(maxLength,
        "Max length cannot be null, -1 for no limit");
    this.maxLength.setValue(maxLength);
  }

  @Override
  public void replaceText(int start, int end, String insertedText) {
    if (this.getMaxLength() <= 0) {
      // Default behavior, in case of no max length
      super.replaceText(start, end, insertedText);
    } else {
      // Get the text in the textfield, before the user enters something
      String currentText = this.getText() == null ? "" : this.getText();

      // Compute the text that should normally be in the textfield now
      String finalText = currentText.substring(0, start) + insertedText
          + currentText.substring(end);

      // If the max length is not excedeed
      int numberOfexceedingCharacters = finalText.length()
          - this.getMaxLength();
      if (numberOfexceedingCharacters <= 0) {
        // Normal behavior
        super.replaceText(start, end, insertedText);
      } else {
        // Otherwise, cut the the text that was going to be inserted
        String cutInsertedText = insertedText.substring(
            0,
            insertedText.length() - numberOfexceedingCharacters);

        // And replace this text
        super.replaceText(start, end, cutInsertedText);
      }
    }
  }
}