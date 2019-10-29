
package as.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ControllerMenu {

    /**
     * Navega para tela de FormaPagamento
     * @return String
     */
    public String irParaFormaPagamento() {
        return "/viewFormaPagamento";
    }

    /**
     * Navega para tela de clientes
     * @return String
     */
    public String irParaCliente() {
        return "/viewCliente";
    }
    
    /**
     * Navega para tela de fornecedores
     * @return String
     */
    public String irParaFornecedor() {
        return "/viewFornecedor";
    }
    
    /**
     * Navega para tela de produtos
     * @return String
     */
    public String irParaProduto() {
        return "/viewProduto";
    }
    
    /**
     * Navega para tela de venda
     * @return String
     */
    public String irParaVenda() {
        return "/viewVenda";
    }

}
