/**
 * Classe que representa um atributo gen�rico.
 */

import java.io.Serializable;

public class Atributo implements Serializable{

    /*** Identificador de vers�o para serializa��o.*/
    private static final long serialVersionUID = 1L;

    /** Nome do atributo */
    private String nome;

    /** Valor do atributo */
    private String valor;

    /**
     * Construtor do atributo.
     * @param nome Nome do atributo.
     * @param valor Valor do atributo.
     */
    public Atributo(String nome,String valor){
        this.nome=nome;
        this.valor=valor;
    }

    /**
     * Obt�m o nome do atributo.
     * @return O nome do atributo.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do atributo.
     * @param nome Novo nome do atributo.
     */
    public void setNome(String nome){
        this.nome=nome;
    }

    /**
     * Obt�m o valor do atributo.
     * @return O valor do atributo.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Define o valor do atributo.
     * @param valor Novo valor do atributo.
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
