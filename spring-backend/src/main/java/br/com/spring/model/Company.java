package br.com.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by mateus on 19/12/16.
 */
@Entity
public class Company extends AbstractModelIdentifier{

    @Column(unique = true)
    private String numeroInscricao;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnaePrincipal;
    private String cnaesSecundario;
    private String naturezaJuridica;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
    private String bairro;
    private String cidade;
    private String uf;
    private String situacaoCadastral;
    private String situacaoCadastralData;
    private String motivoSituacaoCadastral;
    private String situacaoEspecial;
    private String situacaoEspecialData;
    private String telefone;
    private String email;
    private String enteFederativoResponsavel;

    public String getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(String numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnaePrincipal() {
        return cnaePrincipal;
    }

    public void setCnaePrincipal(String cnaePrincipal) {
        this.cnaePrincipal = cnaePrincipal;
    }

    public String getCnaesSecundario() {
        return cnaesSecundario;
    }

    public void setCnaesSecundario(String cnaesSecundario) {
        this.cnaesSecundario = cnaesSecundario;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public void setNaturezaJuridica(String naturezaJuridica) {
        this.naturezaJuridica = naturezaJuridica;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getSituacaoCadastral() {
        return situacaoCadastral;
    }

    public void setSituacaoCadastral(String situacaoCadastral) {
        this.situacaoCadastral = situacaoCadastral;
    }

    public String getSituacaoCadastralData() {
        return situacaoCadastralData;
    }

    public void setSituacaoCadastralData(String situacaoCadastralData) {
        this.situacaoCadastralData = situacaoCadastralData;
    }

    public String getMotivoSituacaoCadastral() {
        return motivoSituacaoCadastral;
    }

    public void setMotivoSituacaoCadastral(String motivoSituacaoCadastral) {
        this.motivoSituacaoCadastral = motivoSituacaoCadastral;
    }

    public String getSituacaoEspecial() {
        return situacaoEspecial;
    }

    public void setSituacaoEspecial(String situacaoEspecial) {
        this.situacaoEspecial = situacaoEspecial;
    }

    public String getSituacaoEspecialData() {
        return situacaoEspecialData;
    }

    public void setSituacaoEspecialData(String situacaoEspecialData) {
        this.situacaoEspecialData = situacaoEspecialData;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnteFederativoResponsavel() {
        return enteFederativoResponsavel;
    }

    public void setEnteFederativoResponsavel(String enteFederativoResponsavel) {
        this.enteFederativoResponsavel = enteFederativoResponsavel;
    }
}
