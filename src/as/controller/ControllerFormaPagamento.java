package as.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import as.DAO.DAOFormaPagamento;
import as.enums.TipoMensagem;
import as.model.ModelFormaPagamento;
import as.util.Mensagem;

@ViewScoped
@ManagedBean
public class ControllerFormaPagamento {

    private ModelFormaPagamento modelFormaPagamento;
    private List<ModelFormaPagamento> listaFormaPagamento;
    private final DAOFormaPagamento daoFormaPagamento;

    private boolean modoAlterar;

    /**
     * Construtor
     */
    public ControllerFormaPagamento() {
        daoFormaPagamento = new DAOFormaPagamento();
        modoAlterar = false;
        listarFormaPagamento();
    }

    /**
     * Limpa o formulario, os objetos e desativa o modo de alterar
     */
    public void novoFormaPagamento() {
        modelFormaPagamento = new ModelFormaPagamento();
        modoAlterar = false;
    }

    /**
     * Salva um formaPagamento
     */
    public void salvarFormaPagamento() {

        //se estiver com o modo alterar desabilitado (modo salvar ativado)
        if (!modoAlterar) {

            //salvo o formaPagamento (retorna o codigo do formaPagamento inserido)
            if (daoFormaPagamento.salvarFormaPagamento(modelFormaPagamento) > 0) {

                //adiciono a lista de formaPagamentos
                listaFormaPagamento.add(modelFormaPagamento);

                novoFormaPagamento();
                
                Mensagem.mensagem("Forma de Pagamento incluido com sucesso!", TipoMensagem.OK);
                
            } else {
                //erro ao salvar formaPagamento
                Mensagem.mensagem("Erro ao salvar Forma de Pagamento", TipoMensagem.ERRO);
            }

        } else {
            //modo de alteracao de formaPagamento
            modoAlterar = false;

            if (daoFormaPagamento.atualizarFormaPagamento(modelFormaPagamento)) {
                
                //atualizo a lista de formaPagamentos
                listarFormaPagamento();
                
                Mensagem.mensagem("Forma de Pagamento alterado com sucesso!", TipoMensagem.OK);
            } else {
                Mensagem.mensagem("Erro ao alterar Forma de Pagamento!", TipoMensagem.ERRO);
            }
        }

    }
    
    /**
     * Retorna um forma de pagamento pela sua ID
     * @param pId
     * @return forma pagamento
     */
    public ModelFormaPagamento recuperarFormaPagamento(long pId){
        return daoFormaPagamento.recuperarFormaPagamento(pId);
    }

    /**
     * Prepara o formulario para alteracao de um formaPagamento
     *
     * @param pModelFormaPagamento
     */
    public void alterarFormaPagamento(ModelFormaPagamento pModelFormaPagamento) {
        modelFormaPagamento = pModelFormaPagamento;
        modoAlterar = true;
    }

    /**
     * Remove um formaPagamento da base de dados
     *
     * @param pModelFormaPagamento
     */
    public void removerFormaPagamento(ModelFormaPagamento pModelFormaPagamento) {
        if (daoFormaPagamento.removerFormaPagamento(pModelFormaPagamento)) {
            listaFormaPagamento.remove(pModelFormaPagamento);
            Mensagem.mensagem("Forma de Pagamento removido com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao remover forma de Pagamento!", TipoMensagem.ERRO);
        }
    }

    public ModelFormaPagamento getModelFormaPagamento() {
        if (modelFormaPagamento == null) {
            modelFormaPagamento = new ModelFormaPagamento();
        }
        return modelFormaPagamento;
    }

    public void setModelFormaPagamento(ModelFormaPagamento modelFormaPagamento) {
        this.modelFormaPagamento = modelFormaPagamento;
    }

    public List<ModelFormaPagamento> getModelFormaPagamentos() {
        if (listaFormaPagamento == null) {
            listaFormaPagamento = new ArrayList<>();
        }
        return listaFormaPagamento;
    }

    /**
     * Lista os formaPagamentos
     */
    public List<ModelFormaPagamento> listarFormaPagamento() {
        return listaFormaPagamento = daoFormaPagamento.listarFormaPagamentos();
    }

}
