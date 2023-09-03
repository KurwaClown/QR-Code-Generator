module kurwaclown.qr_code {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires java.datatransfer;
    requires java.desktop;
    requires com.google.zxing.javase;


    opens kurwaclown.qr_code to javafx.fxml;
    exports kurwaclown.qr_code;
}