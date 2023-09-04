package kurwaclown.qr_code;

public abstract class Network {

    private String password;
    private String SSID;

    public Network(){
        this.SSID = findSSID();
        password = "";
    }


    public String getPassword(){
        return password;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public String getSSID(){
        return SSID;
    }
    public void setSSID(String newSSID){
        this.SSID = newSSID;
    }

    abstract String findSSID();


}
