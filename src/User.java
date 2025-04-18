import java.util.*;
import java.io.Serializable;


/**
 * Representa um usu�rio do sistema.
 * Cada usu�rio possui login, senha, nome e pode interagir com outros usu�rios atrav�s de amizades e mensagens.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Login do usu�rio. */
    private String login;

    /** Nome do usu�rio. */
    private String nome;

    /** Senha do usu�rio. */
    private String senha;

    /** Identifica��o da sess�o do usu�rio. */
    private String idSessao;

    /** Atributos extras do usu�rio, armazenados em um mapa. */
    private Map<String, Atributo> atributosExtras;

    /** Lista de amigos do usu�rio. */
    private ArrayList<String> amigos = new ArrayList<>();

    /** Solicita��es de amizade recebidas pelo usu�rio. */
    private Set<String> solicitacoesAmizade = new HashSet<>();

    /** Lista de mensagens recebidas pelo usu�rio. */
    private ArrayList<String> mensagens= new ArrayList<String>();

    /**
     * Construtor da classe User.
     * @param login Nome do login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     */

    public User(String login, String senha, String nome) {
        this.login = login;
        this.nome = nome;
        this.senha = senha;
        this.idSessao = null;
        this.atributosExtras = new HashMap<>();
        this.amigos = new ArrayList<>();
        this.solicitacoesAmizade=new HashSet<>();
        this.mensagens= new ArrayList<String>();
    }

    /**
     * Pega o nome de login do usu�rio.
     * @return Nome login do usu�rio.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o nome login do usu�rio.
     * @param login Novo nome de login do usu�rio.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Pega o nome do usu�rio.
     * @return Nome do usu�rio.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usu�rio.
     * @param nome Novo nome do usu�rio.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Pega a senha do usu�rio.
     * @return A senha do usu�rio.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usu�rio.
     * @param senha Nova senha do usu�rio.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Pega o ID da sess�o do usu�rio.
     * @return O ID da sess�o.
     */
    public String getIdSessao() {
        return idSessao;
    }

    /**
     * Define o ID da sess�o do usu�rio.
     * @param idSessao Novo ID da sess�o.
     */
    public void setIdSessao(String idSessao) {
        this.idSessao = idSessao;
    }

    /**
     * Adiciona um atributo extra ao usu�rio.
     * @param chave Nome do atributo.
     * @param valor Valor do atributo.
     */
    public void addAtributoExtra(String chave, String valor) {

        atributosExtras.put(chave, new Atributo(chave, valor));
    }


    /**
     * Obt�m um atributo extra do usu�rio.
     * @param chave Nome do atributo.
     * @return O atributo correspondente.
     */

    public Atributo getAtributoExtra(String chave) {
        Atributo atributo = atributosExtras.get(chave);

        return atributo;
    }

    /**
     * Pega a lista de amigos do usu�rio.
     * @return Lista de amigos.
     */
    public List<String> getAmigos() {

        return amigos;
    }


    /**
     * Obt�m a lista de solicita��es de amizade recebidas pelo usu�rio.
     * @return Lista de solicita��es de amizade.
     */
    public Set<String> getSolicitacoesAmizade() {

        return solicitacoesAmizade;
    }

    /**
     * Obt�m a lista de mensagens recebidas pelo usu�rio.
     * @return Lista de mensagens.
     */
    public ArrayList<String> getMensagens(){
        return mensagens;
    }

}