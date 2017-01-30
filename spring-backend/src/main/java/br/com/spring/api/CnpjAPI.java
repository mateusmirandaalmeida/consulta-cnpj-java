package br.com.spring.api;

import br.com.spring.model.Company;
import br.com.spring.service.CnpjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mateus on 19/12/16.
 */
@RestController
@RequestMapping("/api/cnpj")
public class CnpjAPI {

    @Autowired
    private CnpjService cnpjService;

    @RequestMapping(value = "/generate-captcha", method = RequestMethod.GET)
    public Map generateCaptcha() throws IOException {
        return cnpjService.generateCaptcha();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Company handlingCPNJ(@RequestParam("cnpj") String cnpj, @RequestParam("captcha") String captcha, @RequestParam("cookie") String cookie)
            throws IOException {
        return cnpjService.handlingCNPJ(cnpj, captcha, cookie);
    }

}
