package views;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Text field that displays currency numbers.
 * 
 * @author Alec Agnese, Rami El Khatib
 *
 */
public class CurrencyTextField extends JFXTextField {

  private final IntegerProperty precision; // precision
  private final StringProperty currency; // currency symbol

  /**
   * Constructor with default value for precision and currency
   */
  public CurrencyTextField() {
    super();
    this.precision = new SimpleIntegerProperty(0);
    this.currency = new SimpleStringProperty("$");
    setText(currency.getValue());
  }

  /**
   * Get precision Property
   * 
   * @return precision property
   */
  public IntegerProperty precisionProperty() {
    return this.precision;
  }

  /**
   * Get precision property value
   * 
   * @return precision property value
   */
  public final Integer getPrecision() {
    return this.precision.getValue();
  }

  /**
   * Set precision property value
   * 
   * @param precision precision integer to set
   */
  public final void setPrecision(Integer precision) {
    if (precision <= 0)
      throw new IllegalArgumentException("Precision cannnot be negative");
    this.precision.setValue(precision);
  }

  /**
   * Get currency property
   * 
   * @return currency property
   */
  public StringProperty currencyProperty() {
    return this.currency;
  }

  /**
   * Get currency property value
   * 
   * @return currency property value
   */
  public final String getCurrency() {
    return this.currency.getValue();
  }

  /**
   * Set currency property value
   * 
   * @param currency currency symbol to set
   */
  public final void setCurrency(String currency) {
    this.currency.setValue(currency);
    setText(currency);
  }

  @Override
  public void replaceText(int start, int end, String insertedText) {
    // get currency symbol and length
    String sCurrency = currency.getValue();
    int cLength = sCurrency.length();

    // Get the text in the textfield, before the user enters something
    String currentText = this.getText() == null ? "" : this.getText();

    // Compute the text that should normally be in the textfield now
    String finalText = currentText.substring(0, start) + insertedText
        + currentText.substring(end);

    if (finalText.length() >= cLength
        && finalText.substring(0, cLength).equals(sCurrency)
        && constraint(finalText.substring(cLength))) {
      // only replace if final text starts with currency symbol
      // followed by a number satisfying the constraint
      super.replaceText(start, end, insertedText);
    }
  }

  /**
   * Tests whether the text is a double with correct precision.
   * 
   * @param text The text to test
   * @return true if precision
   */
  private boolean constraint(String text) {
    // allow empty text
    if (text.equals(""))
      return true;

    try {
      // parse double
      double number = Double.parseDouble(text);
      // currency can't be negative
      if (number < 0) {
        return false;
      }
      int decimalPoint = text.indexOf(".");

      // if precision == 0 don't allow decimal point
      if (precision.getValue() == 0) return decimalPoint == -1;

      // if precision > 0 get number of decimal places
      int decimalPlaces = text.length() - decimalPoint - 1;
      // return true if no decimal point or number of decimal places < precision
      if (decimalPoint == -1 || decimalPlaces <= precision.getValue()) {

        return true;
      }
      // else return false
      return false;

    } catch (NumberFormatException e) {
      // currency must be a number
      return false;
    }
  }
}