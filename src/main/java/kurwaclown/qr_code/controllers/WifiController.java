package kurwaclown.qr_code.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kurwaclown.qr_code.module.Generator;
import kurwaclown.qr_code.Network;
import kurwaclown.qr_code.WindowsNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WifiController implements  FieldsController{

    private static int count = 0;

    public  WifiController(){
        count++;
        System.out.println(count);
    }
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

        if (validateFields()) {
            network.setPassword(password_tf.getCharacters().toString());
            Generator.generateWifiQR(getNetwork());
        };
    }

    private Boolean validateFields() {
        //Change the ssid if the user input one
        if(!connection_tf.getCharacters().isEmpty()) network.setSSID(connection_tf.getCharacters().toString());
        return !password_tf.getCharacters().isEmpty();

    }

    @FXML
    protected void OnCurrentConnectionButtonClicked(){
        String fieldText = getNetwork().findSSID();
        connection_tf.setText(fieldText);
    }

}
