package as.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import as.DAO.DAOProduto;
import as.enums.TipoMensagem;
import as.model.ModelFornecedor;
import as.model.ModelProduto;
import as.util.Mensagem;

@ViewScoped
@ManagedBean
public class ControllerProduto {

    private ModelProduto modelProduto;
    private List<ModelProduto> listaProduto;
    private List<ModelFornecedor> listaFornecedor;
    private final DAOProduto daoProduto;
    private final ControllerFornecedor controllerFornecedor;

    private boolean modoAlterar;

    /**
     * Construtor
     */
    public ControllerProduto() {
        daoProduto = new DAOProduto();
        controllerFornecedor = new ControllerFornecedor();
        modoAlterar = false;
        listarProduto();
        listarFornecedores();
    }

    /**
     * Limpa o formulario, os objetos e desativa o modo de alterar
     */
    public void novoProduto() {
        modelProduto = new ModelProduto();
        modoAlterar = false;
    }

    /**
     * Salva um produto
     * (caso o modo de alterar esteja true, faz um update do produto)
     */
    public void salvarProduto() {

        //se estiver com o modo alterar desabilitado (modo salvar ativado)
        if (!modoAlterar) {

            //salvo o produto (retorna o codigo do produto inserido)
            if (daoProduto.salvarProduto(modelProduto) > 0) {

                //adiciono a lista de produtos
                listaProduto.add(modelProduto);
                
                novoProduto();

                Mensagem.mensagem("Produto incluido com sucesso!", TipoMensagem.OK);
            } else {
                //erro ao salvar produto
                Mensagem.mensagem("Erro ao salvar produto", TipoMensagem.ERRO);
            }

        } else {
            //modo de alteracao de produto
            modoAlterar = false;

            if (daoProduto.atualizarProduto(modelProduto)) {
                
                //atualizo a lista de produtos
                listarProduto();
                
                Mensagem.mensagem("Produto alterado com sucesso!", TipoMensagem.OK);
            } else {
                Mensagem.mensagem("Erro ao alterar produto!", TipoMensagem.ERRO);
            }
        }

    }
        
    /**
     * Retorna um produto pela sua ID
     * @param pId
     * @return produto
     */
    public ModelProduto recuperarProduto(long pId){
        return daoProduto.recuperarProduto(pId);
    }

    /**
     * Prepara o formulario para alteracao de um produto
     *
     * @param pModelProduto
     */
    public void alterarProduto(ModelProduto pModelProduto) {
        modelProduto = pModelProduto;
        modoAlterar = true;
    }

    /**
     * Remove um produto da base de dados e da lista
     *
     * @param pModelProduto
     */
    public void removerProduto(ModelProduto pModelProduto) {
        if (daoProduto.removerProduto(pModelProduto)) {
            listaProduto.remove(pModelProduto);
            Mensagem.mensagem("Produto removido com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao remover produto!", TipoMensagem.ERRO);
        }
    }

    /**
     * Retorna a lista de produtos (instancia uma caso seja nula)
     * @return List de produtos
     */
    public List<ModelProduto> getModelProdutos() {
        if (listaProduto == null) {
            listaProduto = new ArrayList<>();
        }
        return listaProduto;
    }

    /**
     * Lista os produtos
     */
    public List<ModelProduto> listarProduto() {
        return listaProduto = daoProduto.listarProdutos();
    }
    
    /**
     * Lista os fornecedores dos produtos
     */
    public List<ModelFornecedor> listarFornecedores(){
        return listaFornecedor = controllerFornecedor.listarFornecedor();
    }
    
    /**
     * Recupera um produto (instancia um case seja null)
     * @return ModelProduto
     */
    public ModelProduto getModelProduto() {
        if (modelProduto == null) {
            modelProduto = new ModelProduto();
        }
        return modelProduto;
    }

    /**
     * Seta um produto
     * @param modelProduto 
     */
    public void setModelProduto(ModelProduto modelProduto) {
        this.modelProduto = modelProduto;
    }

    /**
     * Retorna a lista de fornecedores dos produtos
     * @return List de fornecedores
     */
    public List<ModelFornecedor> getListaFornecedor() {
        return listaFornecedor;
    }

    /**
     * Seta a lista de fornecedores
     * @param listaFornecedor 
     */
    public void setListaFornecedor(List<ModelFornecedor> listaFornecedor) {
        this.listaFornecedor = listaFornecedor;
    }
    
}
