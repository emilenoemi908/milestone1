import java.io.*;
import java.util.*;

/**
 * Repositorio: Gerencia(abre sess�es, cria usu�rios,carrega e salva dados) usu�rios, suas sess�es, amizades e envia/ler mensagens/recados.
 */
public class Repositorio {

    private Map<String, User> users= new HashMap<>();

    /**
     * Construtor do reposit�rio.
     */
    public Repositorio(){
        this.users=new HashMap<>();
    }

    /**
     * Quando existe "users.dat", carrega os dados.
     */
    public void carregarDados(){

        File arquivo= new File("users.dat");

        if(!arquivo.exists()){
            return;
        }

        try(ObjectInputStream lendo= new ObjectInputStream(new FileInputStream("users.dat"))){
            users=(Map<String, User>) lendo.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar usu�rios: " + e.getMessage());
        }

    }

    /**
     * Reseta o repositorio de usu�rios, removendo todos e deletando o arquivo de dados "users.dat".
     */

    public void zerarSistema() {

        users.clear();

        File arquivo = new File("users.dat");

        if (arquivo.exists()) {
            arquivo.delete();
        } else {
            System.out.println("O arquivo n�o existe.");
        }
    }

    /**
     * Salva os dados dos usu�rios no arquivo "users.dat".
     */
    public void encerrarSistema() {

        try(ObjectOutputStream writeUser= new ObjectOutputStream(new FileOutputStream("users.dat"))){

            writeUser.writeObject(users);

        }

        catch(IOException e){
            System.out.println("Erro ao salvar cadastro" + e.getMessage());
        }
    }

    /**
     * Finaliza a execu��o do repositorio.
     */
    public void quit(){

        System.exit(0);
    }

    /**
     * Busca o usu�rio pelo Id da sess�o.
     * @param id ID sess�o do usu�rio.
     * @return User correspondente ao id ou null se n�o for encontrado.
     */
    private User getUserById(String id) {
        for (User user : users.values()) {
            if (id.equals(user.getIdSessao())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Cria um novo usu�rio.
     * @param login Nome do login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     * @throws IllegalArgumentException Se os dados forem inv�lidos.
     */
    public void CriarUsuario(String login, String senha, String nome){

        if (login == null || login.trim().isEmpty() ) {
            throw new IllegalArgumentException("Login inv�lido.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha inv�lida.");
        }


        if(users.containsKey(login)){
            throw new IllegalArgumentException("Conta com esse nome j� existe.");
        }

        User newUser= new User (login, senha, nome);
        users.put(login, newUser);

    }

    /**
     * Retorna um atributo espec�fico do usu�rio.
     * @param login Nome do login do usu�rio.
     * @param atributo Nome do atributo que deve ser recuperado.
     * @return Valor do atributo solicitado.
     * @throws InvalidUserException Se o usu�rio n�o existir.
     * @throws InvalidAtributeException Se o atributo n�o estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {

        User user = users.get(login);

        if (user==null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        switch (atributo.toLowerCase()) {

            case "Login":
                return user.getLogin();
            case "nome":
                return user.getNome();
            case "Senha":
                return user.getSenha();
            default:

                Atributo atributoExtra = user.getAtributoExtra(atributo);

                if(atributoExtra == null){
                    throw new InvalidAtributeException("Atributo n�o preenchido.");
                }


                String nome = atributoExtra.getNome();
                String valor = atributoExtra.getValor();

                if (valor != null && !valor.trim().isEmpty()) {
                    return valor;
                }
                else {
                    throw new IllegalArgumentException("Atributo n�o preenchido.");
                }

        }
    }

    /**
     * Abre uma sess�o para um usu�rio.
     * @param login Nome do login do usu�rio.
     * @param senha Senha do usu�rio.
     * @return ID da sess�o gerado.
     * @throws IllegalArgumentException Se o login ou senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) {



        if (login == null || senha == null || login.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Login ou senha inv�lidos.");
        }

        User user = users.get(login);

        if (user == null || !senha.equals(user.getSenha())) {
            throw new IllegalArgumentException("Login ou senha inv�lidos.");
        }

        if (user.getIdSessao() != null) {
            return user.getIdSessao();
        }

        String id = null;
        do {
            id = UUID.randomUUID().toString();
        } while (id == null || id.trim().isEmpty());

        user.setIdSessao(id);
        users.put(user.getLogin(), user);

        return id;

    }

    /**
     * Edita o perfil do usu�rio, alterando um atributo especifico (login, nome, senha) ou preenchendo um novo.
     * @param id ID sess�o do usu�rio.
     * @param atributo Atributo a ser editado.
     * @param valor Novo valor do atributo.
     * @throws InvalidUserException Se o usu�rio n�o for encontrado.
     */
    public void editarPerfil(String id, String atributo, String valor) {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        User usuarioEncontrado = getUserById(id);


        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        switch (atributo.toLowerCase()) {
            case "nome":
                usuarioEncontrado.setNome(valor);
                break;
            case "senha":
                usuarioEncontrado.setSenha(valor);
                break;
            default:

                usuarioEncontrado.addAtributoExtra(atributo, valor);
                break;
        }

    }

    /**
     * Adiciona um amigo � lista de amigos do usu�rio.
     * @param id ID do usu�rio.
     * @param amigo Nome do amigo a ser adicionado.
     * @throws InvalidUserException Se o amigo n�o existir ou se j� forem amigos.
     */
    public  void adicionarAmigo(String id, String amigo) {

        User userAmigo = users.get(amigo);

        if (userAmigo == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (amigo == null || amigo.trim().isEmpty()) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        User usuario = getUserById(id);

        if (usuario == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (!users.containsKey(amigo)) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (usuario.getIdSessao().equals(userAmigo.getIdSessao())) {
            throw new InvalidUserException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        }

        if (usuario.getAmigos().contains(amigo)) {
            throw new InvalidUserException("Usu�rio j� est� adicionado como amigo.");
        }

        if (userAmigo.getSolicitacoesAmizade().contains(usuario.getLogin()) || usuario.getSolicitacoesAmizade().contains(userAmigo)) {


            usuario.getAmigos().add(userAmigo.getLogin());
            userAmigo.getAmigos().add(usuario.getLogin());


            usuario.getSolicitacoesAmizade().remove(amigo);
            userAmigo.getSolicitacoesAmizade().remove(usuario.getLogin());
        }


        if (usuario.getSolicitacoesAmizade().contains(amigo) || userAmigo.getSolicitacoesAmizade().contains(usuario.getNome())) {

            throw new IllegalArgumentException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        }

        usuario.getSolicitacoesAmizade().add(userAmigo.getLogin());


    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     * @param login Nome de login de um dos usu�rios.
     * @param amigo Nome de um dos usu�rios.
     * @return true se forem amigos, false caso contr�rio.
     */
    public boolean ehAmigo(String login, String amigo) {

        User user = users.get(login);
        User userAmigo=users.get(amigo);

        if (user== null || userAmigo==null) {
            return true;
        }

        if(user.getAmigos().contains(amigo) && userAmigo.getAmigos().contains(user)){
            return true;
        }
        if(user.getAmigos().contains(amigo) && !userAmigo.getAmigos().contains(user)){
            return true;
        }
        if(!user.getAmigos().contains(amigo) && userAmigo.getAmigos().contains(user)){
            return true;
        }

        return false;

    }

    /**
     * Exibi a lista de amigos do usu�rio.
     * @param login Nome de login do usu�rio.
     * @return Lista de amigos formatada como string.
     */
    public String getAmigos(String login) {

        User user= users.get(login);

        if(user!=null || user.getAmigos()!=null){

            List<String> amigos = new ArrayList<>(user.getAmigos());

            return amigos.toString().replace("[", "{").replace("]", "}").replace(", ", ",");

        }

        return "{}";

    }

    /**
     * Envia recado/mensagem para outro usu�rio.
     * @param id ID sess�o do usu�rio.
     * @param destinatario Nome do destinat�rio da mensagem.
     * @param mensagem Conte�do do recado/mensagem.
     * @throws RecadoException Se houver erro no envio do recado.
     */
    public void enviarRecado(String id, String destinatario, String mensagem) throws RecadoException {

        if (id == null || id.trim().isEmpty() || destinatario == null || destinatario.trim().isEmpty()) {
            throw new RecadoException("ID ou destinat�rio inv�lido.");
        }

        User userDestinatario = users.get(destinatario);
        User usuarioEncontrado = getUserById(id);

        if (usuarioEncontrado == null || userDestinatario == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (id.equals(userDestinatario.getIdSessao())) {
            throw new InvalidUserException("Usu�rio n�o pode enviar recado para si mesmo.");
        }

        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new RecadoException("Mensagem inv�lida.");
        }


        userDestinatario.getMensagens().add(mensagem);
    }

    /**
     * Faz a leitura do recado mais antigo da lista de racdos do usu�rio.
     * @param id ID sess�o do usu�rio.
     * @return Recado removido da lista de recados.
     * @throws RecadoException Se n�o houver recados.
     * @throws InvalidUserException Se o usu�rio n�o existir.
     * @throws IllegalArgumentException Se id for inv�lido.
     */
    public String lerRecado (String id) throws RecadoException {


        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID inv�lido.");
        }

        User usuarioEncontrado = getUserById(id);



        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        if (usuarioEncontrado.getMensagens().isEmpty()) {
            throw new RecadoException("N�o h� recados.");
        }

        String recadoRemovido = usuarioEncontrado.getMensagens().remove(0);


        return recadoRemovido;

    }

}