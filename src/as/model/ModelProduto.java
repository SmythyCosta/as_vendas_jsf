
package as.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class ModelProduto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6496296430260742977L;

	@Id
    @Column(name = "pro_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Column(name = "pro_nome")
    private String nome;

    @Column(name = "pro_codigo_barras")
    private String codigoDeBarras;

    @Column(name = "pro_valor")
    private Double valor;

    @Column(name = "pro_estoque")
    private Double estoque;

    //chave estrangeira para fornecedor(todo produto tem um fornecedor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "for_id")
    private ModelFornecedor modelFornecedor;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getEstoque() {
        return estoque;
    }

    public void setEstoque(Double estoque) {
        this.estoque = estoque;
    }

    public ModelFornecedor getModelFornecedor() {
        return modelFornecedor;
    }

    public void setModelFornecedor(ModelFornecedor modelFornecedor) {
        this.modelFornecedor = modelFornecedor;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.ID);
        hash = 79 * hash + Objects.hashCode(this.nome);
        hash = 79 * hash + Objects.hashCode(this.codigoDeBarras);
        hash = 79 * hash + Objects.hashCode(this.valor);
        hash = 79 * hash + Objects.hashCode(this.estoque);
        hash = 79 * hash + Objects.hashCode(this.modelFornecedor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModelProduto other = (ModelProduto) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        
        return true;
    }

    
}
