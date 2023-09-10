package kurwaclown.qr_code;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import kurwaclown.qr_code.controllers.*;
import kurwaclown.qr_code.module.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Scope("singleton")
public class MainController {

    private record ViewInformations(String dataType, String viewName, FieldsController controller){};

    private final List<ViewInformations> viewsInformations;
    @Autowired
    public MainController(DataController dataController, WifiController wifiController, ContactController contactController) {
        this.viewsInformations = List.of(
                new ViewInformations("Simple string", "DataString_Fields.fxml", dataController),
                new ViewInformations("Wi-fi Connection", "WIFI_Fields.fxml", wifiController),
                new ViewInformations("Contact Informations", "Contact_Fields.fxml", contactController)
        );
    }

    @FXML
    private ImageView qrCode_iv;

    @FXML
    private ComboBox<String> informationType_cbb;

    @FXML
    private TextField filename_tf;

    @FXML
    private CheckBox filename_cb;

    @FXML
    private VBox qrInformation_VB;

    @FXML
    private Pane fields_pane;

    @FXML
    protected void onGenerateButtonClicked() {
        filenameChangeCheck();

        generateDataQRCode();

        modifyImageViewContent();
    }

    @FXML
    protected void onInformationTypeChanged() throws IOException{

        ViewInformations currentViewInformations = getCurrentViewInformations();

        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(currentViewInformations.viewName));
        loader.setController(currentViewInformations.controller);

        if (!fields_pane.getChildren().isEmpty()) this.fields_pane.getChildren().remove(0);
        this.fields_pane.getChildren().add(loader.load());
    }

    @FXML
    protected void onFilenameCheckboxClicked(){
        filename_tf.setDisable(!filename_cb.isSelected());
    }


    private void generateDataQRCode() {
        getCurrentViewInformations().controller.generate();
    }

    private ViewInformations getCurrentViewInformations(){
        ViewInformations foundView = viewsInformations.stream()
                .filter(info -> info.dataType.equals(informationType_cbb.getValue()))
                .findFirst()
                .orElse(null);
        if (foundView == null) {
            throw new IllegalStateException("An unknown data type was searched");
        }

        return foundView;
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

    public void setFilenameFieldContent() {
        filename_tf.setText(Generator.getFilename());
    }
}