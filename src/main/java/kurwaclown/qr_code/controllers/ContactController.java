package kurwaclown.qr_code.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ContactController implements FieldsController{

    @FXML
    private TextField fullname_tf;

    @FXML
    private TextField phone_tf;

    @FXML
    private TextField mail_tf;

    @FXML
    private TextField url_tf;

    @FXML
    private TextField address_tf;

    @FXML
    private DatePicker birthday_dp;

    @Override
    public void generate() {

    }

    @Override
    public Boolean validateFields() {
        return null;
    }
}
