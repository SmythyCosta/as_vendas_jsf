package as.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import as.DAO.DAOFornecedor;
import as.enums.TipoMensagem;
import as.model.ModelFornecedor;
import as.util.Mensagem;

@ViewScoped
@ManagedBean
public class ControllerFornecedor {

    private ModelFornecedor modelFornecedor;
    private List<ModelFornecedor> listaFornecedor;
    private final DAOFornecedor daoFornecedor;

    private boolean modoAlterar;

    /**
     * Construtor
     */
    public ControllerFornecedor() {
        daoFornecedor = new DAOFornecedor();
        modoAlterar = false;
        listarFornecedor();
    }

    /**
     * Limpa o formulario, os objetos e desativa o modo de alterar
     */
    public void novoFornecedor() {
        modelFornecedor = new ModelFornecedor();
        modoAlterar = false;
    }

    /**
     * Salva um fornecedor
     */
    public void salvarFornecedor() {

        //se estiver com o modo alterar desabilitado (modo salvar ativado)
        if (!modoAlterar) {

            //salvo o fornecedor (retorna o codigo do fornecedor inserido)
            if (daoFornecedor.salvarFornecedor(modelFornecedor) > 0) {

                //adiciono a lista de fornecedors
                listaFornecedor.add(modelFornecedor);
                
                novoFornecedor();

                Mensagem.mensagem("Fornecedor incluido com sucesso!", TipoMensagem.OK);
            } else {
                //erro ao salvar fornecedor
                Mensagem.mensagem("Erro ao salvar fornecedor", TipoMensagem.ERRO);
            }

        } else {
            //modo de alteracao de fornecedor
            modoAlterar = false;

            if (daoFornecedor.atualizarFornecedor(modelFornecedor)) {
                
                //atualizo a lista de fornecedors
                listarFornecedor();
                
                Mensagem.mensagem("Fornecedor alterado com sucesso!", TipoMensagem.OK);
            } else {
                Mensagem.mensagem("Erro ao alterar fornecedor!", TipoMensagem.ERRO);
            }
        }

    }
    
    /**
     * Retorna um fonrnecedor pela sua ID
     * @param pId
     * @return fornecedor
     */
    public ModelFornecedor recuperarFornecedor(long pId){
        return daoFornecedor.recuperarFornecedor(pId);
    }

    /**
     * Prepara o formulario para alteracao de um fornecedor
     *
     * @param pModelFornecedor
     */
    public void alterarFornecedor(ModelFornecedor pModelFornecedor) {
        modelFornecedor = pModelFornecedor;
        modoAlterar = true;
    }

    /**
     * Remove um fornecedor da base de dados
     *
     * @param pModelFornecedor
     */
    public void removerFornecedor(ModelFornecedor pModelFornecedor) {
        if (daoFornecedor.removerFornecedor(pModelFornecedor)) {
            listaFornecedor.remove(pModelFornecedor);
            Mensagem.mensagem("Fornecedor removido com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao remover fornecedor!", TipoMensagem.ERRO);
        }
    }

    public ModelFornecedor getModelFornecedor() {
        if (modelFornecedor == null) {
            modelFornecedor = new ModelFornecedor();
        }
        return modelFornecedor;
    }

    public void setModelFornecedor(ModelFornecedor modelFornecedor) {
        this.modelFornecedor = modelFornecedor;
    }

    public List<ModelFornecedor> getModelFornecedores() {
        if (listaFornecedor == null) {
            listaFornecedor = new ArrayList<>();
        }
        return listaFornecedor;
    }

    /**
     * Lista os fornecedors
     * @return list de fornecedores
     */
    public List<ModelFornecedor> listarFornecedor() {
        listaFornecedor = daoFornecedor.listarFornecedores();
        return listaFornecedor;
    }
    
}
