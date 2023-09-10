package kurwaclown.qr_code.module;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import kurwaclown.qr_code.Network;

public class Generator {
    private static final int DEFAULT_LENGTH = 250;

    private static String filename = "GeneratedQR";

    public static void setFilename(String filename) {
        Generator.filename = filename;
    }

    public static String getFilename() {
        return filename;
    }

    private static String formatWifiString(String ssid, String password){
        return String.format("WIFI:S:%s;T:WPA;P:%s;", ssid, password);
    }

    public static void generateContactQr(Contact contactInformations){
        generate(contactInformations.getInformationsAsString());
    }

    public static void generateWifiQR(Network network){
        if(network.getPassword() == null) throw new NullPointerException("Password must be set");
        generateWifiQR(network.getSSID(), network.getPassword());
    }

    public static void generateWifiQR(String ssid, String password){
        String wifiData = formatWifiString(ssid, password);
        generate(wifiData);
    }

    public static void generate(String data){
        generate(data, DEFAULT_LENGTH);
    }

    public static void generate (String data, int sideLength){
        generate(data, sideLength, sideLength);
    }

    public static void generate(String data, int width, int height) {

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than zero.");
        }

        Map<EncodeHintType, Object> hints = new HashMap<>();

        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 0);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            Path qrCodePath = new File(filename + ".png").toPath();

            //TODO : QR Color Selection
            int onColor = 0xFF000000; // Black
            int offColor = 0x00000000; // Transparent
            MatrixToImageWriter.writeToPath(
                    bitMatrix,
                    "png",
                    qrCodePath,
                    new MatrixToImageConfig(onColor,offColor));

        } catch (WriterException | IOException e){
            e.printStackTrace();
        };
    }
}
