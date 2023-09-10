package kurwaclown.qr_code.module;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Contact {

    public enum Information_Type{
        FULLNAME, PHONE, MAIL, URL, ADDRESS, BIRTHDAY, TITLE;
    }
    private String fullname;
    private String phone;
    private String mail;
    private String url;
    private String address;
    private String birthday;
    private String title;

    public Contact(){

    }
    public Contact(Map<Information_Type, String> informations){
        if(informations.isEmpty()) throw new IllegalArgumentException("Constructor need at least one information inside the map");

        for (Map.Entry<Information_Type, String> information :
                informations.entrySet()) {
            switch (information.getKey()){
                case FULLNAME -> fullname = information.getValue();
                case PHONE -> phone = information.getValue();
                case MAIL -> mail = information.getValue();
                case URL -> url = information.getValue();
                case ADDRESS -> address = information.getValue();
                case BIRTHDAY -> birthday = information.getValue();
                case TITLE -> title = information.getValue();
            }
        }
    }

    public Boolean ensureOneInformation(){
        return Stream.of(fullname, phone, mail, url, address, birthday, title).anyMatch(Objects::nonNull);
    }

    public String getInformationsAsString(){
        Map<String, String> informations = getAvailableInformationsMap();
        StringBuilder data = new StringBuilder("BEGIN:VCARD\n");
        data.append("VERSION:4.0\n");
        for (Map.Entry<String, String> entry :
                informations.entrySet()) {
            data.append(String.format("%s:%s\n", entry.getKey(), entry.getValue()));
        }
        data.append("END:VCARD");
        return data.toString();
    }

    public Map<String, String> getAvailableInformationsMap(){
        if (!ensureOneInformation()) throw new NullPointerException("Need at least one information to be set");

        Map<String, String> availableInformations = new HashMap<>();
        if(fullname != null) availableInformations.put("FN", fullname);
        if(phone != null) availableInformations.put("TEL", phone);
        if(mail != null) availableInformations.put("EMAIL", mail);
        if(url != null) availableInformations.put("URL", url);
        if(address != null) availableInformations.put("ADR", address);
        if(birthday != null) availableInformations.put("BDAY", birthday);
        if(birthday != null) availableInformations.put("TITLE", title);

        return  availableInformations;
    }

}
