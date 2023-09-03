package kurwaclown.qr_code;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class HelloController {

    private Network network;

    @Autowired
    public void setNetwork(WindowsNetwork defaultWindowsNetwork){
        this.network = defaultWindowsNetwork;
    }

    @FXML
    private ImageView qrCode_iv;

    @FXML
    private TextField connection_tf;

    @FXML
    private TextField password_tf;

    @FXML
    protected void onGenerateButtonClicked() {

        if(password_tf.getCharacters().isEmpty()) return;
        Network network = connection_tf.getCharacters().isEmpty() ? new WindowsNetwork(password_tf.getCharacters().toString())
                                                                : new WindowsNetwork(connection_tf.getCharacters().toString(), password_tf.getCharacters().toString());
        File qrCodeFile = new File("GeneratedQR.png");
        Generator.generateWifiQR(network);
        Image image = new Image(qrCodeFile.toURI().toString());
        qrCode_iv.setImage(image);
    }

    @FXML
    protected void OnCurrentConnectionButtonClicked(){
        String fieldText = network.findSSID();
        connection_tf.setText(fieldText);
    }
}