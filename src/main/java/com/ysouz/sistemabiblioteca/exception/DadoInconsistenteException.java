package com.ysouz.sistemabiblioteca.exception;

/**
 * Lançada quando um usuário é encontrado, mas os dados associados a ele
 * violam alguma regra de integridade esperada pelo sistema
 * (por exemplo, ausência de endereço, que é obrigatório).
 * <p>
 *  Diferente de {@link UsuarioNaoEncontradoException}, que indica que
 *  o usuário simplesmente não existe, esta exceção sinaliza que o
 *  usuário existe, porém, num estado inconsistente com as regras de domínio.
 */
public class DadoInconsistenteException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public DadoInconsistenteException(String message) {
        super(message);
    }
}
