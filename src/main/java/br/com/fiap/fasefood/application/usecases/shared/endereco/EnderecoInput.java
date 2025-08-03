package br.com.fiap.fasefood.application.usecases.shared.endereco;

public record EnderecoInput(
        String logradouro,
        String numero,
        String cep,
        String complemento,
        String bairro,
        String cidade,
        String uf
) {
}
