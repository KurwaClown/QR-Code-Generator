package kurwaclown.qr_code.module;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Reader {
    public enum DataType{STRING, WIFI, CONTACT}

    private static String processScan(String data){
        if(data.startsWith("BEGIN:VCARD")) return processVCard(data);
        else if (data.startsWith("WIFI:")) return processWifi(data);
        else return data;
    }

        private static String processScan(String data, DataType dataType){
        switch (dataType){
            case CONTACT -> {
                return processVCard(data);
            }
            case WIFI -> {
                return processWifi(data);
            }
            case STRING -> {
                return data;
            }
            default -> throw new IllegalStateException("Unexpected value: " + dataType);
        }
    }

    private static String processWifi(String data) {
        String[] wifiData = data.substring(5) //Removes the "WIFI:" characters
                                .split(";"); //Split the data;
        StringBuilder processedData = new StringBuilder();

        for (String wifiDatum : wifiData) {
            if (wifiDatum.startsWith("S:"))
                processedData.append(String.format("Connection name : %s", wifiDatum.substring(2)));
            else if (wifiDatum.startsWith("P:"))
                processedData.append(String.format("Password : %s", wifiDatum.substring(2)));
        }

        return processedData.toString();
    }

    private static String processVCard(String data) {
        String[] contactData = data.split("\n");

        StringBuilder processedData = new StringBuilder();

        for (String contactDatum:
             contactData) {
            String prefix = contactDatum.split(":")[0];
            String value = contactDatum.split(":")[1];

            switch (prefix) {
                case "FN" -> processedData.append(formatContactDatum("Full name", value));
                case "TEL" -> processedData.append(formatContactDatum("Phone number", value));
                case "EMAIL" -> processedData.append(formatContactDatum("E-mail", value));
                case "ADR" -> processedData.append(formatContactDatum("Address",
                        value.substring(0, value.length() - 1))
                        .replace(";", ", "));
                case "URL" -> processedData.append(formatContactDatum("URL", value));
                case "BDAY" -> processedData.append(formatContactDatum("Birthday", value));
                case "TITLE" -> processedData.append(formatContactDatum("Title", value));
            }
        }
        return processedData.toString().trim();
    }

    private static String formatContactDatum(String informationName, String information) {
        return String.format("%s : %s\n", informationName, information);
    }

    private static Result scanImage(String filePath) throws  IOException, NotFoundException{
        Map<DecodeHintType, Object> hintMap = new HashMap<>();

        hintMap.put(DecodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);

        BufferedImage image = ImageIO.read(new FileInputStream(filePath));

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

        return new MultiFormatReader().decode(binaryBitmap, hintMap);
    }


    public static String readQRCode(String filePath) {
        try{
            Result qrCodeResult = scanImage(filePath);
            return processScan(qrCodeResult.getText());
        } catch (IOException ioException){
            return "File not image or not readable : " + ioException.getMessage();
        } catch (NotFoundException notFoundException){
            return String.format("File not found, verify the file path : %s", notFoundException.getMessage());
        }

    }

    public static String readQRCode(String filePath, DataType dataType) throws IOException, NotFoundException{
        Result qrCodeResult = scanImage(filePath);

        return processScan(qrCodeResult.getText(), dataType);
    }
}
