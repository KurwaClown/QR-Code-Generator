package kurwaclown.qr_code;

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
    
}
