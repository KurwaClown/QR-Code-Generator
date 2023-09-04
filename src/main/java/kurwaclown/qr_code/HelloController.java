package kurwaclown.qr_code;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Scope("singleton")
public class HelloController {

    private Network network;

    @Autowired
    public void setNetwork(WindowsNetwork defaultWindowsNetwork){
        this.network = defaultWindowsNetwork;
        System.out.println("Setting network " + this.network);
    }

    public Network getNetwork() {
        return network;
    }

    @FXML
    private ImageView qrCode_iv;

    @FXML
    private TextField connection_tf;

    @FXML
    private PasswordField password_tf;

    @FXML
    private TextField filename_tf;

    @FXML
    protected void onGenerateButtonClicked() {

        if(password_tf.getCharacters().isEmpty()) return;
        if(!filename_tf.getCharacters().isEmpty()) Generator.setFilename(filename_tf.getCharacters().toString());
        File qrCodeFile = new File(Generator.getFilename() + ".png");
        Generator.generateWifiQR(getNetwork());
        Image image = new Image(qrCodeFile.toURI().toString());
        qrCode_iv.setImage(image);
    }

    @FXML
    protected void OnCurrentConnectionButtonClicked(){
        String fieldText = getNetwork().findSSID();
        connection_tf.setText(fieldText);
    }
}