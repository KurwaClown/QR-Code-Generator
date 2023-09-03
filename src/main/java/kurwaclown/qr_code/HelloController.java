package kurwaclown.qr_code;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import java.io.File;

public class HelloController {

    @FXML
    private ImageView qrCode;

    @FXML
    private TextField ssidField;

    @FXML
    private TextField passwordField;

    @FXML
    protected void onGenerateButtonClicked() {

        if(passwordField.getCharacters().isEmpty()) return;
        Network network = ssidField.getCharacters().isEmpty() ? new WindowsNetwork(passwordField.getCharacters().toString())
                                                                : new WindowsNetwork(ssidField.getCharacters().toString(), passwordField.getCharacters().toString());
        File qrCodeFile = new File("GeneratedQR.png");
        Generator.generateWifiQR(network);
        Image image = new Image(qrCodeFile.toURI().toString());
        qrCode.setImage(image);
    }

    @FXML
    protected void OnCurrentConnectionChecked(){
        String fieldText = WindowsNetwork.getInstance().findSSID();
        ssidField.setText(fieldText);
    }
}