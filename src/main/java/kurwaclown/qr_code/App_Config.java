package kurwaclown.qr_code;

import kurwaclown.qr_code.controllers.ContactController;
import kurwaclown.qr_code.controllers.DataController;
import kurwaclown.qr_code.controllers.WifiController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "kurwaclown.qr_code")
public class App_Config {

    @Bean
    public WindowsNetwork defaultWindowsNetwork(){
        return new WindowsNetwork();
    }

    @Bean
    public DataController dataController() {
        return new DataController();
    }

    @Bean
    public WifiController wifiController() {
        return new WifiController();
    }

    @Bean
    public ContactController contactController() {
        return new ContactController();
    }
}
