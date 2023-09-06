package kurwaclown.qr_code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
@Scope("singleton")
public class MainController {

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
    private ComboBox<String> informationType_cbb;

    @FXML
    private TextField connection_tf;

    @FXML
    private PasswordField password_tf;

    @FXML
    private TextField filename_tf;

    @FXML
    private CheckBox filename_cb;

    @FXML
    private VBox qrInformation_VB;

    @FXML
    private Pane fields_pane;

    @FXML
    private TextField data_tf;

    private final Map<String, String> sceneMap = Map.ofEntries(Map.entry("Simple string", "DataString_Fields.fxml"),
            Map.entry("Wi-fi Connection", "WIFI_Fields.fxml"));

    @FXML
    protected void onGenerateButtonClicked() {
        String qrType = informationType_cbb.getValue();

        if(qrType.equals("Simple string")) generateDataQRCode();
        else generateWifiQRCode();

        modifyImageViewContent();
    }

    @FXML
    protected void OnCurrentConnectionButtonClicked(){
        String fieldText = getNetwork().findSSID();
        connection_tf.setText(fieldText);
    }

    @FXML
    protected void onInformationTypeChanged() throws IOException{
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(sceneMap.get(informationType_cbb.getValue())));
        loader.setController(this);
        this.fields_pane.getChildren().remove(0);
        this.fields_pane.getChildren().add(loader.load());
        setFilenameFieldContent();
    }

    @FXML
    protected void onFilenameCheckboxClicked(){
        filename_tf.setDisable(!filename_cb.isSelected());
    }


    private void generateDataQRCode() {
        if(data_tf.getCharacters().isEmpty()) return;
        filenameChangeCheck();

        Generator.generate(data_tf.getCharacters().toString());
    }

    private void generateWifiQRCode() {
        if(password_tf.getCharacters().isEmpty()) return;
        filenameChangeCheck();

        Generator.generateWifiQR(getNetwork());
    }

    private void filenameChangeCheck(){
        if(!filename_cb.isSelected()) return;

        String field_text = filename_tf.getCharacters().toString();
        if(field_text.isEmpty() || field_text.equals(Generator.getFilename())) return;

        Generator.setFilename(field_text);
    }

    private void modifyImageViewContent() {
        File qrCodeFile = new File(Generator.getFilename() + ".png");
        Image image = new Image(qrCodeFile.toURI().toString());
        qrCode_iv.setImage(image);
    }

    public void modifyInformationFields(Node node){
        fields_pane.getChildren().add(node);
    }

    public void setFilenameFieldContent() {
        filename_tf.setText(Generator.getFilename());
    }
}