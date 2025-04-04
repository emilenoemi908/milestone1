import java.util.List;
import java.util.Set;

/**
 * Interface simplificada para as opera��es do sistema.
 * Encapsula o acesso ao reposit�rio de dados e fornece m�todos para gerenciar usu�rios,sess�es, amigos e recados.
 */

public class Facade{

    private Repositorio repositorio;

    /**
     * Inicializa o reposit�rio.
     */
    public Facade() {
        this.repositorio = new Repositorio();
    }

    /**
     * Cria um novo usu�rio no sistema.
     * @param login Nome de login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     */
    public void criarUsuario(String login, String senha, String nome) {

        repositorio.carregarDados();
        repositorio.CriarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sess�o para um usu�rio.
     * @param login Nome de login do usu�rio.
     * @param senha Senha do usu�rio.
     * @return ID da sess�o.
     */
    public String abrirSessao(String login, String senha){
        repositorio.carregarDados();
        return repositorio.abrirSessao(login, senha);
    }

    /**
     * Retorna um atributo espec�fico de um usu�rio.
     * @param login Nome de login do usu�rio.
     * @param atributo Nome do atributo desejado.
     * @return Valor do atributo.
     */
    public String getAtributoUsuario(String login, String atributo){
        repositorio.carregarDados();
        return repositorio.getAtributoUsuario(login, atributo);
    }


    /**
     * Reseta o sistema, apagando todos os dados.
     */
    public void zerarSistema(){
        repositorio.zerarSistema();
    }

    /**
     * Encerra o sistema, salvando os dados.
     */
    public void encerrarSistema(){
        repositorio.encerrarSistema();
    }

    /**
     * Finaliza a execu��o do sistema.
     */
    public void quit(){
        repositorio.quit();
    }

    /**
     * Edita o perfil de um usu�rio.
     * @param id ID do usu�rio.
     * @param atributo Atributo a ser alterado.
     * @param valor Novo valor do atributo.
     */
    public void editarPerfil(String id, String atributo, String valor){

        repositorio.carregarDados();
        repositorio.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um amigo a lista de amigos do usu�rio.
     * @param id ID do usu�rio.
     * @param amigo ID do amigo a ser adicionado.
     */
    public void adicionarAmigo(String id, String amigo){

        repositorio.adicionarAmigo(id,amigo);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     * @param login Nome de login do usu�rio.
     * @param amigo Nome do amigo.
     * @return true se forem amigos, false caso contr�rio.
     */
    public boolean ehAmigo(String login, String amigo){

        return repositorio.ehAmigo(login,amigo);
    }

    /**
     * Retorna a lista de amigos de um usu�rio.
     * @param login Nome de login do usu�rio.
     * @return Lista formatada de amigos.
     */
    public String getAmigos(String login){
        repositorio.carregarDados();
        return repositorio.getAmigos(login);
    }

    /**
     * Envia um recado para um usu�rio.
     * @param id ID do remetente.
     * @param destinatario ID do destinat�rio.
     * @param mensagem Conte�do do recado.
     */
    public void enviarRecado(String id,String destinatario, String mensagem){
        repositorio.enviarRecado(id, destinatario, mensagem);

    }


    /**
     * L� um recado recebido por um usu�rio.
     * @param id ID do usu�rio.
     * @return Mensagem do recado.
     */
    public String lerRecado(String id){
        return repositorio.lerRecado(id);
    }


}
