package as.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import as.DAO.DAOUsuario;
import as.enums.TipoMensagem;
import as.model.ModelUsuario;
import as.util.Criptografia;
import as.util.Mensagem;

@ViewScoped
@ManagedBean
public class ControllerUsuario {

    private ModelUsuario usuario;
    private List<ModelUsuario> listaUsuario;
    private final DAOUsuario daoUsuario;
    private String confirmeSenha;

    private boolean modoAlterar;

    /**
     * Construtor
     */
    public ControllerUsuario() {
        daoUsuario = new DAOUsuario();
        modoAlterar = false;
        confirmeSenha = "";
        listarUsuario();
    }

    /**
     * Limpa o formulario, os objetos e desativa o modo de alterar
     */
    public void novoUsuario() {
        usuario = new ModelUsuario();
        confirmeSenha = "";
        modoAlterar = false;
    }

    /**
     * Salva um usuário
     * @param pUsuario
     */
    public void salvarUsuario(ModelUsuario pUsuario) {

        //se estiver com o modo alterar desabilitado (modo salvar ativado)
        if (!modoAlterar) {

            if (senhasConferem(pUsuario.getSenha(), this.confirmeSenha)) {

                //verifico se o login nao esta cadastrado
                if (existeUsuario(pUsuario.getLogin()) == false) {

                    //criptografo a senha
                    usuario.setSenha(Criptografia.SHA1(usuario.getSenha()));
                    
                    //salvo o usuário (retorna o codigo do usuário inserido)
                    if (daoUsuario.salvarUsuario(usuario) > 0) {

                        novoUsuario();
                        
                        Mensagem.mensagem("Usuário incluido com sucesso!", TipoMensagem.OK);
                        
                    } else {
                        //erro ao salvar usuário
                        Mensagem.mensagem("Erro ao salvar usuário", TipoMensagem.ERRO);
                    }

                } else {
                    Mensagem.mensagem("Este login já está em uso!", TipoMensagem.AVISO);
                }

            } else {
                Mensagem.mensagem("Senhas não conferem!", TipoMensagem.AVISO);
            }

        } else {
            //modo de alteracao de usuário
            modoAlterar = false;

            if (daoUsuario.atualizarUsuario(usuario)) {

                //atualizo a lista de usuários
                listarUsuario();

                Mensagem.mensagem("Usuário alterado com sucesso!", TipoMensagem.OK);
            } else {
                Mensagem.mensagem("Erro ao alterar usuário!", TipoMensagem.ERRO);
            }
        }
    }

    /**
     * Retorna um usuário pela sua ID
     *
     * @param pId
     * @return usuário
     */
    public ModelUsuario recuperarUsuario(long pId) {
        return daoUsuario.recuperarUsuario(pId);
    }

    /**
     * Prepara o formulario para alteracao de um usuário
     *
     * @param pUsuario
     */
    public void alterarUsuario(ModelUsuario pUsuario) {
        usuario = pUsuario;
        modoAlterar = true;
    }

    /**
     * Remove um usuário da base de dados
     *
     * @param pUsuario
     */
    public void removerUsuario(ModelUsuario pUsuario) {
        if (daoUsuario.removerUsuario(pUsuario)) {
            listaUsuario.remove(pUsuario);
            Mensagem.mensagem("Usuário removido com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao remover usuário!", TipoMensagem.ERRO);
        }
    }

    /**
     * VAlida o usuario informado
     *
     * @param pUsuario
     * @return String
     */
    public String validarLogin(ModelUsuario pUsuario) {

        //criptografo a senha
        pUsuario.setSenha(Criptografia.SHA1(pUsuario.getSenha()));
        
        //busco o usuario no banco
        this.usuario = daoUsuario.validarLogin(pUsuario);

        if (this.usuario != null) {

            return this.irParaTelaPrincipal();
        } else {
            Mensagem.mensagem("Usuário ou senha inválidos", TipoMensagem.AVISO);
            return "";
        }
    }

    /**
     * Verifica se o login ja esta sendo usado por outro usuario
     *
     * @param pLogin
     * @return boolean
     */
    public boolean existeUsuario(String pLogin) {

        return daoUsuario.existeUsuario(pLogin);
    }

    /**
     * Verifica se as senhas informadas conferem
     *
     * @param pSenha
     * @param pConfirmeSenha
     * @return boolean
     */
    public boolean senhasConferem(String pSenha, String pConfirmeSenha) {
        if (pSenha.equals(pConfirmeSenha)) {
            return true;
        }
        return false;
    }

    /**
     * Navega para tela principal do sistema
     *
     * @return String
     */
    public String irParaTelaPrincipal() {

        //adiciono o usuario logado a sessao
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sessao", this.usuario);
        
        return "/viewMain.xhtml?faces-redirect=true";
    }

    /**
     * GET usuario
     *
     * @return
     */
    public ModelUsuario getUsuario() {
        if (usuario == null) {
            usuario = new ModelUsuario();
        }
        return usuario;
    }

    /**
     * SET usuario
     *
     * @param modelUsuario
     */
    public void setUsuario(ModelUsuario modelUsuario) {
        this.usuario = modelUsuario;
    }

    /**
     * REtorna uma lista de usuarios cadastrados
     *
     * @return List<ModelUsuario>
     */
    public List<ModelUsuario> getUsuarios() {
        if (listaUsuario == null) {
            listaUsuario = new ArrayList<>();
        }
        return listaUsuario;
    }

    /**
     * Lista os usuários
     */
    public void listarUsuario() {
        listaUsuario = daoUsuario.listarUsuarios();
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

}
