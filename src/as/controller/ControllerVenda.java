package as.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import as.DAO.DAOVenda;
import as.enums.TipoMensagem;
import as.model.ModelCliente;
import as.model.ModelFormaPagamento;
import as.model.ModelProduto;
import as.model.ModelVenda;
import as.util.Mensagem;


@ViewScoped
@ManagedBean
public class ControllerVenda {

    private ModelVenda modelVenda;

    private List<ModelVenda> listaVendas;
    private List<ModelCliente> listaClientes;
    private List<ModelProduto> listaProdutos;
    private List<ModelFormaPagamento> listaFormaPagamento;
    private List<ModelVenda> carrinhoDeCompra;
    private final DAOVenda daoVenda;

    private final ControllerCliente controllerCliente;
    private final ControllerProduto controllerProduto;
    private final ControllerFormaPagamento controllerFormaPagamento;

    private ModelVenda dadosVenda;//armazena as informaçoes imutaveis de uma venda (venda e forma de pagamento)

    /**
     * Construtor
     */
    public ControllerVenda() {
        daoVenda = new DAOVenda();
        controllerCliente = new ControllerCliente();
        controllerProduto = new ControllerProduto();
        controllerFormaPagamento = new ControllerFormaPagamento();
        dadosVenda = new ModelVenda();
//        listarVenda();
        listarClientes();
        listarProdutos();
        listarFormaPagamentos();
    }

    /**
     * Limpa o formulario, os objetos e desativa o modo de alterar
     */
    public void novoVenda() {
        modelVenda = new ModelVenda();
        carrinhoDeCompra = new ArrayList<>();
    }

    /**
     * Reseto o form para a adição de um novo item da venda
     */
    public void novoItemDeVenda() {
        ModelVenda modelVendaTemp = modelVenda;
        modelVenda = new ModelVenda();
        modelVenda.setModelCliente(modelVendaTemp.getModelCliente());
        modelVenda.setModelFormaPagamento(modelVendaTemp.getModelFormaPagamento());
    }

    /**
     * Adiciona um produto ao carrinho de compras
     *
     * @return boolean
     */
    public boolean adicionarProdutoCarrinho() {

        boolean status = false;

        if (this.temNoEstoque(modelVenda.getModelProduto(), modelVenda.getQuantidade())) {

            //adiciono ao carrinho
            carrinhoDeCompra.add(modelVenda);
            this.calcularTotalItemVenda();
            novoItemDeVenda();
            status = true;

        }

        return status;
    }

    /**
     * cria um item da venda
     */
    public void criarItemVenda() {
        if (adicionarProdutoCarrinho()) {
            Mensagem.addMessagemOk("Produto adicionado ao carrinho!");
        } else {
            Mensagem.addMessagemAviso("Quantidade indisponível no estoque!");
        }
    }

    /**
     * Calcula o valor total de cada item adicionado ao carrinho
     */
    public void calcularTotalItemVenda() {
        modelVenda.setValorTotal(modelVenda.getModelProduto().getValor() * modelVenda.getQuantidade());
    }

    /**
     * Salva uma venda
     */
    public void salvarVenda() {

        //controla os erros de cada venda
        int erroVenda = 0;

        //crio o numero do pedido
        long proximoPedido = getProximoPedido();

        Date dataVenda = new Date();

        //verifico se tem estoque do produto
//        if (temNoEstoque()) {
        for (ModelVenda venda : carrinhoDeCompra) {

            //seto o numero do pedido
            venda.setPedido(proximoPedido);

            //seto a data da venda
            venda.setDataVenda(dataVenda);

            //salvo o venda (retorna o codigo do venda inserido)
            if (daoVenda.salvarVenda(venda) > 0) {

                //adiciono a lista de vendas
                listaVendas.add(venda);

                //atualizo o form vendas
                novoVenda();

            } else {
                erroVenda++;
            }

        }
//        } else {
//            Mensagem.mensagem("Estoque do produto insuficiente!", TipoMensagem.AVISO);
//        }

        if (erroVenda == 0) {
            Mensagem.mensagem("Venda fechada com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao fechar a venda", TipoMensagem.ERRO);
        }
    }

    /**
     * Retorna um venda pela sua ID
     *
     * @param pId
     * @return venda
     */
    public ModelVenda recuperarVenda(long pId) {
        return daoVenda.recuperarVenda(pId);
    }

    /**
     * Remove um venda da base de dados
     *
     * @param pModelVenda
     */
    public void removerVenda(ModelVenda pModelVenda) {
        if (daoVenda.removerVenda(pModelVenda)) {
            listaVendas.remove(pModelVenda);
            Mensagem.mensagem("Venda removida com sucesso!", TipoMensagem.OK);
        } else {
            Mensagem.mensagem("Erro ao remover venda!", TipoMensagem.ERRO);
        }
    }

    /**
     * Remove um item de venda do carrinho de compras
     *
     * @param pModelVenda
     */
    public void removerProdutoCarrinhoCompras(ModelVenda pModelVenda) {
        carrinhoDeCompra.remove(pModelVenda);
    }

    /**
     * Verifica se existe a quantidade de produtos solicitados no estoque
     *
     * @param pProdutoAVerificar
     * @param pQuantidadeSolicitada
     * @return boolean
     */
    public boolean temNoEstoque(ModelProduto pProdutoAVerificar, int pQuantidadeSolicitada) {
        boolean status = false;

        //Recupero o produto do banco
        ModelProduto produtoEstoque = controllerProduto.recuperarProduto(pProdutoAVerificar.getID());

        //verifico estoque
        if (produtoEstoque.getEstoque() >= pQuantidadeSolicitada) {
            status = true;
        }

        return status;
    }

    /**
     * Recupero o proximo numero de pedido
     *
     * @return long
     */
    private long getProximoPedido() {
        long numeroProximoModelVenda = 1;
        long numeroUltimoPedido = daoVenda.getUltimoPedido();

        if (numeroUltimoPedido > 0) {
            numeroProximoModelVenda = numeroUltimoPedido + 1;
        }
        
        return numeroProximoModelVenda;
    }

    /**
     * Lista os vendas
     */
    public void listarVenda() {
        listaVendas = daoVenda.listarVendas();
    }

    /**
     * Lista os vendas de uma venda
     *
     * @return lista de vendas
     */
    public List<ModelCliente> listarClientes() {
        return listaClientes = controllerCliente.listarCliente();
    }

    /**
     * Lista os produtos de uma venda
     *
     * @return lista de produtos
     */
    public List<ModelProduto> listarProdutos() {
        return listaProdutos = controllerProduto.listarProduto();
    }

    /**
     * Lista as formas de pagamento de uma venda
     *
     * @return lista de formas de pagamento
     */
    public List<ModelFormaPagamento> listarFormaPagamentos() {
        return listaFormaPagamento = controllerFormaPagamento.listarFormaPagamento();
    }

    //GETTERS AND SETTERS
    public List<ModelVenda> getListaVendas() {
        return listaVendas;
    }

    public void setListaVendas(List<ModelVenda> listaVendas) {
        this.listaVendas = listaVendas;
    }

    public List<ModelCliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<ModelCliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ModelVenda getModelVenda() {
        if (modelVenda == null) {
            modelVenda = new ModelVenda();
        }
        return modelVenda;
    }

    public void setModelVenda(ModelVenda modelVenda) {
        this.modelVenda = modelVenda;
    }

    public List<ModelVenda> getModelVendas() {
        if (listaVendas == null) {
            listaVendas = new ArrayList<>();
        }
        return listaVendas;
    }

    public List<ModelProduto> getListaProdutos() {
        if (listaProdutos == null) {
            listaProdutos = new ArrayList();
        }
        return listaProdutos;
    }

    public void setListaProdutos(List<ModelProduto> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public List<ModelVenda> getCarrinhoDeCompra() {
        if (carrinhoDeCompra == null) {
            carrinhoDeCompra = new ArrayList<>();
        }
        return carrinhoDeCompra;
    }

    public void setCarrinhoDeCompra(List<ModelVenda> carrinhoDeCompra) {
        this.carrinhoDeCompra = carrinhoDeCompra;
    }

    public List<ModelFormaPagamento> getListaFormaPagamento() {
        return listaFormaPagamento;
    }

    public void setListaFormaPagamento(List<ModelFormaPagamento> listaFormaPagamento) {
        this.listaFormaPagamento = listaFormaPagamento;
    }

    public ModelVenda getDadosVenda() {
        return dadosVenda;
    }

    public void setDadosVenda(ModelVenda dadosVenda) {
        this.dadosVenda = dadosVenda;
    }
}
