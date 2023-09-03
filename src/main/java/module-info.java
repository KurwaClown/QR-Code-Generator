module kurwaclown.qr_code {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.zxing;
    requires java.datatransfer;
    requires java.desktop;
    requires com.google.zxing.javase;
    requires spring.core;
    requires spring.context;
    requires spring.beans;

    opens kurwaclown.qr_code to javafx.fxml, spring.core;
    exports kurwaclown.qr_code;
}