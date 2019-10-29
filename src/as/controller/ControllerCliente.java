package as.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import as.DAO.DAOCliente;
import as.enums.TipoMensagem;
import as.model.ModelCliente;
import as.util.Mensagem;

@ViewScoped
@ManagedBean
public class ControllerCliente {

    private ModelCliente modelCliente;
    private List<ModelCliente> listaCliente;
    private final DAOCliente daoCliente;

    private boolean modoAlterar;

    /**
     * Construtor
     */
    public ControllerCliente() {
        daoCliente = new DAOCliente();
        modoAlterar = false;
        listarCliente();
    }

    /**
     * Limpa o formulario, os objetos e desativa o modo de alterar
     */
    public void novoCliente() {
        modelCliente = new ModelCliente();
        modoAlterar = false;
    }

    /**
     * Salva um cliente
     */
    public void salvarCliente() {

        //se estiver com o modo alterar desabilitado (modo salvar ativado)
        if (!modoAlterar) {

            //salvo o cliente (retorna o codigo do cliente inserido)
            if (daoCliente.salvarCliente(modelCliente) > 0) {

                //adiciono a lista de clientes
                listaCliente.add(modelCliente);
                
                novoCliente();

                Mensagem.mensagem("Cliente incluido com sucesso!", TipoMensagem.OK);
            } else {
                //erro ao salvar cliente
                Mensagem.mensagem("Erro ao salvar cliente", TipoMensagem.ERRO);
            }

        } else {
            //modo de alteracao de cliente
            modoAlterar = false;

            if (daoCliente.atualizarCliente(modelCliente)) {
                
                //atualizo a lista de clientes
                listarCliente();
                
                Mensagem.mensagem("Cliente alterado com sucesso!", TipoMensagem.OK);
            } else {
                Mensagem.mensagem("Erro ao alterar cliente!", TipoMensagem.ERRO);
            }
        }

    }
    
     /**
     * Retorna um cliente pela sua ID
     * @param pId
     * @return cliente
     **/
    public ModelCliente recuperarCliente(long pId){
        return daoCliente.recuperarCliente(pId);
    }

    /**
     * Prepara o formulario para alteracao de um cliente
     *
     * @param pModelCliente
     */
    public void alterarCliente(ModelCliente pModelCliente) {
        modelCliente = pModelCliente;
        modoAlterar = true;
    }

    /**
     * Remove um cliente da base de dados
     *
     * @param pModelCliente
     */
    public void removerCliente(ModelCliente pModelCliente) {
        if (daoCliente.removerCliente(pModelCliente)) {
            listaCliente.remove(pModelCliente);
            Mensagem.mensagem("Cliente removido com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao remover cliente!", TipoMensagem.ERRO);
        }
    }




    public ModelCliente getModelCliente() {
        if (modelCliente == null) {
            modelCliente = new ModelCliente();
        }
        return modelCliente;
    }

    public void setModelCliente(ModelCliente modelCliente) {
        this.modelCliente = modelCliente;
    }

    public List<ModelCliente> getModelClientes() {
        if (listaCliente == null) {
            listaCliente = new ArrayList<>();
        }
        return listaCliente;
    }



    /**
     * Lista os clientes
     **/
    public List<ModelCliente> listarCliente() {
        return listaCliente = daoCliente.listarClientes();
    }

}
