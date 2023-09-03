package kurwaclown.qr_code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class WindowsNetwork extends Network{

    public WindowsNetwork(){
    }
    public WindowsNetwork(String password){
        super(password);
    }
    public WindowsNetwork(String ssid, String password) {
        super(ssid, password);
    }

    @Override
    public String findSSID(){
        try{
            Process process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "netsh wlan show interfaces | findstr /c:\" SSID\""});

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            return reader.readLine().split(": ")[1].trim();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }


}
