package kurwaclown.qr_code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class MainApplication extends Application {

    private static AnnotationConfigApplicationContext context;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("QRCode.fxml"));
        MainController controller = context.getBean(MainController.class);
        fxmlLoader.setController(controller);

        Scene scene = new Scene(fxmlLoader.load());

        setDefaultViewDetails(controller);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void setDefaultViewDetails(MainController controller) throws IOException{
        FXMLLoader fields = new FXMLLoader(MainApplication.class.getResource("WIFI_Fields.fxml"));
        fields.setController(controller);

        GridPane fields_loaded = fields.load();
        controller.modifyInformationFields(fields_loaded);

        controller.setFilenameFieldContent();
    }



    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(App_Config.class);
        launch();
    }
}