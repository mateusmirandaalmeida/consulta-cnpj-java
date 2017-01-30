package br.com.spring.service;

import br.com.spring.model.Company;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * Created by mateus on 19/12/16.
 */
@Service
public class CnpjService {

    private static RestTemplate restTemplate = new RestTemplate();
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; rv:32.0) Gecko/20100101 Firefox/32.0";

    private static final String BASE = "http://www.receita.fazenda.gov.br";
    private static final String SOLICITACAO = BASE.concat("/pessoajuridica/cnpj/cnpjreva/Cnpjreva_Solicitacao2.asp");
    private static final String GERA_CAPTCHA = BASE.concat("/pessoajuridica/cnpj/cnpjreva/captcha/gerarCaptcha.asp");
    private static final String VALIDACAO = "http://www.receita.fazenda.gov.br/pessoajuridica/cnpj/cnpjreva/valida.asp";

    public Company handlingCNPJ(String cnpj, String captcha, String cookie) throws MalformedURLException{
        cookie = cookie.split(";")[0];
        WebClient client = new WebClient(BrowserVersion.FIREFOX_38);
        WebRequest request;
        try {
            request = new WebRequest(new URL(VALIDACAO), HttpMethod.POST);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Error creating request for url "+VALIDACAO, ex);
        }
        //Setting the request headers
        request.getAdditionalHeaders().put("Host", "www.receita.fazenda.gov.br");
        request.getAdditionalHeaders().put("User-Agent", USER_AGENT);
        request.getAdditionalHeaders().put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9, */* ;q=0.8");
        request.getAdditionalHeaders().put("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.5,en;q=0.3");
        request.getAdditionalHeaders().put("Accept-Encoding", "gzip, deflate");
        request.getAdditionalHeaders().put("Referer", VALIDACAO);
        request.getAdditionalHeaders().put("Cookie", "flag=1; "+cookie);
        request.getAdditionalHeaders().put("Connection", "keep-alive");
        request.getAdditionalHeaders().put("Content-Type", "application/x-www-form-urlencoded");

        //Setting the request params
        request.setRequestParameters(new ArrayList<NameValuePair>());
        request.getRequestParameters().add(new NameValuePair("origem", "comprovante"));
        request.getRequestParameters().add(new NameValuePair("cnpj", cnpj));
        request.getRequestParameters().add(new NameValuePair("txtTexto_captcha_serpro_gov_br", captcha));
        request.getRequestParameters().add(new NameValuePair("submit1", "Consultar"));
        request.getRequestParameters().add(new NameValuePair("search_type", "cnpj"));

        try {
            HtmlPage page = client.getPage(request);
            Company company = new Company();
            company.setNumeroInscricao(cnpj);
            return createCompanyByHTML(company, page, cookie);
        } catch (IOException | FailingHttpStatusCodeException ex) {
            throw new RuntimeException("Error requesting page", ex);
        }
    }

    public Company createCompanyByHTML(Company company, HtmlPage page, String cookie){
        DomNodeList<DomElement> tdList = page.getElementsByTagName("td");
        for (DomElement td : tdList) {
            Iterator<HtmlElement> itFont = td.getElementsByTagName("font").iterator();
            while (itFont.hasNext()) {
                HtmlElement font = itFont.next();
                switch (font.asText()) {
                    case "NOME EMPRESARIAL":
                        company.setRazaoSocial(itFont.next().asText());
                        break;
                    case "TÍTULO DO ESTABELECIMENTO (NOME DE FANTASIA)":
                        company.setNomeFantasia(itFont.next().asText());
                        break;
                    case "CÓDIGO E DESCRIÇÃO DA ATIVIDADE ECONÔMICA PRINCIPAL":
                        company.setCnaePrincipal(itFont.next().asText());
                        break;
                    case "CÓDIGO E DESCRIÇÃO DAS ATIVIDADES ECONÔMICAS SECUNDÁRIAS":
                        company.setCnaesSecundario(itFont.next().asText());
                        break;
                    case "CÓDIGO E DESCRIÇÃO DA NATUREZA JURÍDICA":
                        company.setNaturezaJuridica(itFont.next().asText());
                        break;
                    case "LOGRADOURO":
                        company.setLogradouro(itFont.next().asText());
                        break;
                    case "NÚMERO":
                        company.setNumero(itFont.next().asText());
                        break;
                    case "COMPLEMENTO":
                        company.setComplemento(itFont.next().asText());
                        break;
                    case "CEP":
                        company.setCep(itFont.next().asText());
                        break;
                    case "BAIRRO/DISTRITO":
                        company.setBairro(itFont.next().asText());
                        break;
                    case "MUNICÍPIO":
                        company.setCidade(itFont.next().asText());
                        break;
                    case "UF":
                        company.setUf(itFont.next().asText());
                        break;
                    case "SITUAÇÃO CADASTRAL":
                        company.setSituacaoCadastral(itFont.next().asText());
                        break;
                    case "DATA DA SITUAÇÃO CADASTRAL":
                        company.setSituacaoCadastralData(itFont.next().asText());
                        break;
                    case "MOTIVO DE SITUAÇÃO CADASTRAL":
                        company.setMotivoSituacaoCadastral(itFont.next().asText());
                        break;
                    case "SITUAÇÃO ESPECIAL":
                        company.setSituacaoEspecial(itFont.next().asText());
                        break;
                    case "DATA DA SITUAÇÃO ESPECIAL":
                        company.setSituacaoEspecialData(itFont.next().asText());
                        break;
                    case "TELEFONE":
                        company.setTelefone(itFont.next().asText());
                        break;
                    case "ENDEREÇO ELETRÔNICO":
                        company.setEmail(itFont.next().asText());
                        break;
                    case "ENTE FEDERATIVO RESPONSÁVEL (EFR)":
                        company.setEnteFederativoResponsavel(cookie);
                        break;
                }
            }
        }
        return company;
    }

    public Map generateCaptcha() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String token = getCookie();
        Map toReturn = new HashMap();
        headers.set("Pragma", "no-cache");
        headers.set("Origin", BASE);
        headers.set("Host", BASE);
        headers.set("User-Agent", USER_AGENT);
        headers.set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headers.set("Accept-Language", "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4");
        headers.set("Accept-Encoding", "gzip, deflate, sdch, br");
        headers.set("Referer", SOLICITACAO);
        headers.set("Cookie", "flag=1; ".concat(token));
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Resource> response = restTemplate.exchange(GERA_CAPTCHA, org.springframework.http.HttpMethod.GET, entity, Resource.class);
        toReturn.put("cookie", token);
        byte[] image =  IOUtils.toByteArray(response.getBody().getInputStream());
        toReturn.put("captcha", "data:image/png;base64,".concat(base64Encode(image)));
        return toReturn;
    }

    public String getCookie(){
        ResponseEntity<String> response = restTemplate.getForEntity(SOLICITACAO, String.class);
        HttpHeaders headers = response.getHeaders();
        return headers.get("Set-Cookie").get(0);
    }

    public static String base64Encode(byte[] value) {
        return Base64.encodeBase64String(value);
    }

}
