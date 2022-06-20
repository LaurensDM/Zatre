package listeners;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class TextFieldNumberOnlyListener implements ChangeListener<String> {

    private TextField field;

    public TextFieldNumberOnlyListener(TextField tf) {
        this.field = tf;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue,
                        String newValue) {
        if (!newValue.matches("\\d*")) {
            field.setText(newValue.replaceAll("[^\\d]", ""));
        }
    }
}
