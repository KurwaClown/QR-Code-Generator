package kurwaclown.qr_code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

        controller.setFilenameFieldContent();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(App_Config.class);
        launch();
    }
}