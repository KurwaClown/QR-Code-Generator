package kurwaclown.qr_code.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kurwaclown.qr_code.module.Generator;
import kurwaclown.qr_code.Network;
import kurwaclown.qr_code.WindowsNetwork;
import org.springframework.beans.factory.annotation.Autowired;

public class WifiController implements  FieldsController{


    private Network network;

    @Autowired
    public void setNetwork(WindowsNetwork defaultWindowsNetwork){
        this.network = defaultWindowsNetwork;
    }

    public Network getNetwork() {
        return network;
    }

    @FXML
    private TextField connection_tf;

    @FXML
    private PasswordField password_tf;
    @Override
    public void generate() {
        if (validateFields()) Generator.generateWifiQR(getNetwork());
    }

    @Override
    public Boolean validateFields() {
        //Change the ssid if the user input one
        if(!connection_tf.getCharacters().isEmpty()) network.setSSID(connection_tf.getCharacters().toString());
        return !password_tf.getCharacters().isEmpty();

    }
}
