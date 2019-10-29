
package as.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "venda")
public class ModelVenda implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -890305478818813249L;

	@Id
    @Column(name = "ven_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    //chave estrangeira para cliente(toda venda eh feita para um cliente)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_id")
    private ModelCliente modelCliente;

    //chave estrangeira para produto (toda venda eh composta por um produto e quantidade)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pro_id")
    private ModelProduto modelProduto;

    //chave estrangeira para forma de pagamento(toda venda eh composta por uma forma de pagamento)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fo_pa_id")
    private ModelFormaPagamento modelFormaPagamento;
    
    //quantidade de itens vendidos
    @Column(name = "ven_quantidade")
    private int quantidade;
    
    //numero do pedido da venda
    @Column(name = "ven_pedido")
    private long pedido;
    
    @Column(name = "ven_data")
    private Date dataVenda;
    
    @Transient //@Transient não mapeia o atributo no banco de dados
    private Double valorTotal;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public ModelCliente getModelCliente() {
        return modelCliente;
    }

    public void setModelCliente(ModelCliente modelCliente) {
        this.modelCliente = modelCliente;
    }

    public ModelProduto getModelProduto() {
        return modelProduto;
    }

    public void setModelProduto(ModelProduto modelProduto) {
        this.modelProduto = modelProduto;
    }

    public ModelFormaPagamento getModelFormaPagamento() {
        return modelFormaPagamento;
    }

    public void setModelFormaPagamento(ModelFormaPagamento modelFormaPagamento) {
        this.modelFormaPagamento = modelFormaPagamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public long getPedido() {
        return pedido;
    }

    public void setPedido(long pedido) {
        this.pedido = pedido;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.ID);
        hash = 61 * hash + Objects.hashCode(this.modelCliente);
        hash = 61 * hash + Objects.hashCode(this.modelProduto);
        hash = 61 * hash + Objects.hashCode(this.modelFormaPagamento);
        hash = 61 * hash + this.quantidade;
        hash = 61 * hash + (int) (this.pedido ^ (this.pedido >>> 32));
        hash = 61 * hash + Objects.hashCode(this.dataVenda);
        hash = 61 * hash + Objects.hashCode(this.valorTotal);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModelVenda other = (ModelVenda) obj;
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        if (!Objects.equals(this.modelCliente, other.modelCliente)) {
            return false;
        }
        if (!Objects.equals(this.modelProduto, other.modelProduto)) {
            return false;
        }
        if (!Objects.equals(this.modelFormaPagamento, other.modelFormaPagamento)) {
            return false;
        }
        if (this.quantidade != other.quantidade) {
            return false;
        }
        if (this.pedido != other.pedido) {
            return false;
        }
        if (!Objects.equals(this.dataVenda, other.dataVenda)) {
            return false;
        }
        if (!Objects.equals(this.valorTotal, other.valorTotal)) {
            return false;
        }
        return true;
    }

   

}
