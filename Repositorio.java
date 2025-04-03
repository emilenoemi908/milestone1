import java.io.*;
import java.util.*;

/**
 * Repositorio: Gerencia(abre sessões, cria usuários,carrega e salva dados) usuários, suas sessões, amizades e envia/ler mensagens/recados.
 */
public class Repositorio {

    private Map<String, User> users= new HashMap<>();

    /**
     * Construtor do repositório.
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
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }

    }

    /**
     * Reseta o repositorio de usuários, removendo todos e deletando o arquivo de dados "users.dat".
     */

    public void zerarSistema() {

        users.clear();

        File arquivo = new File("users.dat");

        if (arquivo.exists()) {
            arquivo.delete();
        } else {
            System.out.println("O arquivo não existe.");
        }
    }

    /**
     * Salva os dados dos usuários no arquivo "users.dat".
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
     * Finaliza a execução do repositorio.
     */
    public void quit(){

        System.exit(0);
    }

    /**
     * Busca o usuário pelo Id da sessão.
     * @param id ID sessão do usuário.
     * @return User correspondente ao id ou null se não for encontrado.
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
     * Cria um novo usuário.
     * @param login Nome do login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     * @throws IllegalArgumentException Se os dados forem inválidos.
     */
    public void CriarUsuario(String login, String senha, String nome){

        if (login == null || login.trim().isEmpty() ) {
            throw new IllegalArgumentException("Login inválido.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha inválida.");
        }


        if(users.containsKey(login)){
            throw new IllegalArgumentException("Conta com esse nome já existe.");
        }

        User newUser= new User (login, senha, nome);
        users.put(login, newUser);

    }

    /**
     * Retorna um atributo específico do usuário.
     * @param login Nome do login do usuário.
     * @param atributo Nome do atributo que deve ser recuperado.
     * @return Valor do atributo solicitado.
     * @throws InvalidUserException Se o usuário não existir.
     * @throws InvalidAtributeException Se o atributo não estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {

        User user = users.get(login);

        if (user==null) {
            throw new InvalidUserException("Usuário não cadastrado.");
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
                    throw new InvalidAtributeException("Atributo não preenchido.");
                }


                String nome = atributoExtra.getNome();
                String valor = atributoExtra.getValor();

                if (valor != null && !valor.trim().isEmpty()) {
                    return valor;
                }
                else {
                    throw new IllegalArgumentException("Atributo não preenchido.");
                }

        }
    }

    /**
     * Abre uma sessão para um usuário.
     * @param login Nome do login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão gerado.
     * @throws IllegalArgumentException Se o login ou senha forem inválidos.
     */
    public String abrirSessao(String login, String senha) {



        if (login == null || senha == null || login.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        User user = users.get(login);

        if (user == null || !senha.equals(user.getSenha())) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
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
     * Edita o perfil do usuário, alterando um atributo especifico (login, nome, senha) ou preenchendo um novo.
     * @param id ID sessão do usuário.
     * @param atributo Atributo a ser editado.
     * @param valor Novo valor do atributo.
     * @throws InvalidUserException Se o usuário não for encontrado.
     */
    public void editarPerfil(String id, String atributo, String valor) {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        User usuarioEncontrado = getUserById(id);


        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
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
     * Adiciona um amigo à lista de amigos do usuário.
     * @param id ID do usuário.
     * @param amigo Nome do amigo a ser adicionado.
     * @throws InvalidUserException Se o amigo não existir ou se já forem amigos.
     */
    public  void adicionarAmigo(String id, String amigo) {

        User userAmigo = users.get(amigo);

        if (userAmigo == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (amigo == null || amigo.trim().isEmpty()) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        User usuario = getUserById(id);

        if (usuario == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (!users.containsKey(amigo)) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (usuario.getIdSessao().equals(userAmigo.getIdSessao())) {
            throw new InvalidUserException("Usuário não pode adicionar a si mesmo como amigo.");
        }

        if (usuario.getAmigos().contains(amigo)) {
            throw new InvalidUserException("Usuário já está adicionado como amigo.");
        }

        if (userAmigo.getSolicitacoesAmizade().contains(usuario.getLogin()) || usuario.getSolicitacoesAmizade().contains(userAmigo)) {


            usuario.getAmigos().add(userAmigo.getLogin());
            userAmigo.getAmigos().add(usuario.getLogin());


            usuario.getSolicitacoesAmizade().remove(amigo);
            userAmigo.getSolicitacoesAmizade().remove(usuario.getLogin());
        }


        if (usuario.getSolicitacoesAmizade().contains(amigo) || userAmigo.getSolicitacoesAmizade().contains(usuario.getNome())) {

            throw new IllegalArgumentException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }

        usuario.getSolicitacoesAmizade().add(userAmigo.getLogin());


    }

    /**
     * Verifica se dois usuários são amigos.
     * @param login Nome de login de um dos usuários.
     * @param amigo Nome de um dos usuários.
     * @return true se forem amigos, false caso contrário.
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
     * Exibi a lista de amigos do usuário.
     * @param login Nome de login do usuário.
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
     * Envia recado/mensagem para outro usuário.
     * @param id ID sessão do usuário.
     * @param destinatario Nome do destinatário da mensagem.
     * @param mensagem Conteúdo do recado/mensagem.
     * @throws RecadoException Se houver erro no envio do recado.
     */
    public void enviarRecado(String id, String destinatario, String mensagem) throws RecadoException {

        if (id == null || id.trim().isEmpty() || destinatario == null || destinatario.trim().isEmpty()) {
            throw new RecadoException("ID ou destinatário inválido.");
        }

        User userDestinatario = users.get(destinatario);
        User usuarioEncontrado = getUserById(id);

        if (usuarioEncontrado == null || userDestinatario == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (id.equals(userDestinatario.getIdSessao())) {
            throw new InvalidUserException("Usuário não pode enviar recado para si mesmo.");
        }

        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new RecadoException("Mensagem inválida.");
        }


        userDestinatario.getMensagens().add(mensagem);
    }

    /**
     * Faz a leitura do recado mais antigo da lista de racdos do usuário.
     * @param id ID sessão do usuário.
     * @return Recado removido da lista de recados.
     * @throws RecadoException Se não houver recados.
     * @throws InvalidUserException Se o usuário não existir.
     * @throws IllegalArgumentException Se id for inválido.
     */
    public String lerRecado (String id) throws RecadoException {


        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }

        User usuarioEncontrado = getUserById(id);



        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        if (usuarioEncontrado.getMensagens().isEmpty()) {
            throw new RecadoException("Não há recados.");
        }

        String recadoRemovido = usuarioEncontrado.getMensagens().remove(0);


        return recadoRemovido;

    }

}