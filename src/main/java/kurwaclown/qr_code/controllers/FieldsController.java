package kurwaclown.qr_code.controllers;

import org.springframework.stereotype.Controller;

@Controller
public interface FieldsController
{
    void generate();

    private Boolean validateFields(){return false;}
}
