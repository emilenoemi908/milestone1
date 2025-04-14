/**
 * Exceção lançada quando ocorre um erro relacionado ao envio ou leitura de recados.
 */
public class RecadoException extends RuntimeException {

  /**
   * Constrói uma nova RecadoException com a mensagem especificada.
   * @param message A mensagem detalhando a causa do erro.
   */
  public RecadoException(String message) {
    super(message);
  }
}