package kurwaclown.qr_code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public class Generator {
    private static final int DEFAULT_LENGTH = 250;

    private static String formatWifiString(String ssid, String password){
        return String.format("WIFI:S:%s;T:WPA;P:%s;", ssid, password);
    }
    public static void generateWifiQR(String ssid, String password){
        String wifiData = formatWifiString(ssid, password);
        generate(wifiData);
    }

    public static void generateWifiQR(Network network){
        String wifiData = String.format("WIFI:S:%s;T:WPA;P:%s;", network.getSSID(), network.getPassword());
        generate(wifiData);
    }
    public static void generate(String data){
        generate(data, DEFAULT_LENGTH);
    }

    public static void generate(String data, String filename){
        generate(data, DEFAULT_LENGTH, filename);
    }
    public static void generate (String data, int sideLength){
        generate(data, sideLength, sideLength);
    }

    public static void generate(String data, int sideLength, String filename){
        generate(data, sideLength, sideLength, filename);
    }
    public static void generate(String data, int width, int height){
        generate(data, width, height, "GeneratedQR.png");
    }
    public static void generate(String data, int width, int height, String filename) {

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be greater than zero.");
        }

        Map<EncodeHintType, Object> hints = new HashMap<>();

        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hints);

            Path qrCodeFile = new File(filename).toPath();
            MatrixToImageWriter.writeToPath(
                    bitMatrix,
                    "png",
                    qrCodeFile);
        } catch (WriterException | IOException e){
            e.printStackTrace();
        };
    }
}
