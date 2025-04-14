import java.util.*;
import java.io.Serializable;


/**
 * Representa um usuário do sistema.
 * Cada usuário possui login, senha, nome e pode interagir com outros usuários através de amizades e mensagens.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Login do usuário. */
    private String login;

    /** Nome do usuário. */
    private String nome;

    /** Senha do usuário. */
    private String senha;

    /** Identificação da sessão do usuário. */
    private String idSessao;

    /** Atributos extras do usuário, armazenados em um mapa. */
    private Map<String, Atributo> atributosExtras;

    /** Lista de amigos do usuário. */
    private ArrayList<String> amigos = new ArrayList<>();

    /** Solicitações de amizade recebidas pelo usuário. */
    private Set<String> solicitacoesAmizade = new HashSet<>();

    /** Lista de mensagens recebidas pelo usuário. */
    private ArrayList<String> mensagens= new ArrayList<String>();

    /**
     * Construtor da classe User.
     * @param login Nome do login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
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
     * Pega o nome de login do usuário.
     * @return Nome login do usuário.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o nome login do usuário.
     * @param login Novo nome de login do usuário.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Pega o nome do usuário.
     * @return Nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     * @param nome Novo nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Pega a senha do usuário.
     * @return A senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     * @param senha Nova senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Pega o ID da sessão do usuário.
     * @return O ID da sessão.
     */
    public String getIdSessao() {
        return idSessao;
    }

    /**
     * Define o ID da sessão do usuário.
     * @param idSessao Novo ID da sessão.
     */
    public void setIdSessao(String idSessao) {
        this.idSessao = idSessao;
    }

    /**
     * Adiciona um atributo extra ao usuário.
     * @param chave Nome do atributo.
     * @param valor Valor do atributo.
     */
    public void addAtributoExtra(String chave, String valor) {

        atributosExtras.put(chave, new Atributo(chave, valor));
    }


    /**
     * Obtém um atributo extra do usuário.
     * @param chave Nome do atributo.
     * @return O atributo correspondente.
     */

    public Atributo getAtributoExtra(String chave) {
        Atributo atributo = atributosExtras.get(chave);

        return atributo;
    }

    /**
     * Pega a lista de amigos do usuário.
     * @return Lista de amigos.
     */
    public List<String> getAmigos() {

        return amigos;
    }


    /**
     * Obtém a lista de solicitações de amizade recebidas pelo usuário.
     * @return Lista de solicitações de amizade.
     */
    public Set<String> getSolicitacoesAmizade() {

        return solicitacoesAmizade;
    }

    /**
     * Obtém a lista de mensagens recebidas pelo usuário.
     * @return Lista de mensagens.
     */
    public ArrayList<String> getMensagens(){
        return mensagens;
    }

}