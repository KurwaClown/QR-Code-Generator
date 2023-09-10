package kurwaclown.qr_code.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import kurwaclown.qr_code.module.Contact;
import kurwaclown.qr_code.module.Generator;

import java.util.HashMap;
import java.util.Map;

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
    private TextField city_tf;

    @FXML
    private TextField postalCode_tf;

    @FXML
    private TextField title_tf;

    @Override
    public void generate() {
        Map<Contact.Information_Type, String> informations = new HashMap<>();
        informations.put(Contact.Information_Type.FULLNAME, fullname_tf.getCharacters().toString());
        informations.put(Contact.Information_Type.PHONE, phone_tf.getCharacters().toString());
        informations.put(Contact.Information_Type.MAIL, mail_tf.getCharacters().toString());
        informations.put(Contact.Information_Type.URL, url_tf.getCharacters().toString());
        informations.put(Contact.Information_Type.ADDRESS, buildAddressString());

        Contact contactInformations = new Contact(informations);

        if (validateFields(contactInformations))Generator.generateContactQr(contactInformations);
    }

    public String buildAddressString(){
        StringBuilder addressInformation = new StringBuilder();

        if(!address_tf.getCharacters().isEmpty()) addressInformation.append(address_tf.getCharacters().toString()).append(";");
        if(!city_tf.getCharacters().isEmpty()) addressInformation.append(city_tf.getCharacters().toString()).append(";");
        if(!postalCode_tf.getCharacters().isEmpty()) addressInformation.append(postalCode_tf.getCharacters().toString()).append(";");

        return addressInformation.toString();
    }


    private Boolean validateFields(Contact contactInformations) {
        return contactInformations.ensureOneInformation();
    }
}
