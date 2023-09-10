package kurwaclown.qr_code.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import kurwaclown.qr_code.module.Generator;

public class DataController implements FieldsController{

    @FXML
    private TextField data_tf;

    public void generate() {
        if(validateFields()) Generator.generate(data_tf.getCharacters().toString());
    }

    private Boolean validateFields() {
        return !data_tf.getCharacters().isEmpty();
    }
}
