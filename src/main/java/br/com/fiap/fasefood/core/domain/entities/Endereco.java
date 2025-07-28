package br.com.fiap.fasefood.core.domain.entities;

public class Endereco {

    private final Long id;
    private String logradouro;
    private String numero;
    private String cep;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;


    public Endereco(Long id, String logradouro, String numero, String cep, String complemento,
                    String bairro, String cidade, String uf) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public void atualizar(String logradouro, String numero, String cep, String complemento,
                          String bairro, String cidade, String uf) {
        if (logradouro != null) this.logradouro = logradouro;
        if (numero != null) this.numero = numero;
        if (cep != null) this.cep = cep;
        if (complemento != null) this.complemento = complemento;
        if (bairro != null) this.bairro = bairro;
        if (cidade != null) this.cidade = cidade;
        if (uf != null) this.uf = uf;
    }

    public Long getId() { return id; }
    public String getLogradouro() { return logradouro; }
    public String getNumero() { return numero; }
    public String getCep() { return cep; }
    public String getComplemento() { return complemento; }
    public String getBairro() { return bairro; }
    public String getCidade() { return cidade; }
    public String getUf() { return uf; }
}