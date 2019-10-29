package as.DAO;

import java.util.List;

import javax.persistence.Query;

import as.model.ModelVenda;
import as.util.EntidadeUtil;

public class DAOVenda extends DAO {

    public DAOVenda() {
        if (entityManager == null) {
            //criando o entityManager
            entityManager = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();
        }
    }

    /**
     * Salva a venda na base de dados
     *
     * @param pModelVenda
     * @return id da venda
     */
    public long salvarVenda(ModelVenda pModelVenda) {

        try {
            //iniciado a transacao de inserir
            entityManager.getTransaction().begin();

            //salvando o objeto no banco
            entityManager.persist(pModelVenda);

            //commit na transacao (efetivando a operacao anterior
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //desfaco a operacao caso ocorra alguma excessao na operacao de insert
            entityManager.getTransaction().rollback();
            return 0;
        }

        //retorno o id do objeto inserido
        return pModelVenda.getID();
    }

    /**
     * Atualiza um venda da base de dados
     *
     * @param pModelVenda
     * @return boolean
     */
    public boolean atualizarVenda(ModelVenda pModelVenda) {
        try {

            //inicio a transacao
            entityManager.getTransaction().begin();

            // e recupero a venda a ser alterado
            pModelVenda = entityManager.find(ModelVenda.class, pModelVenda.getID());

            //se não for nulo (se existir mesmo aquele venda) faco o update (também com metodo persist)
            if (pModelVenda != null) {
                entityManager.persist(pModelVenda);

                //finalizo a transacao
                entityManager.getTransaction().commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;
    }

    /**
     * Recupero todos os vendas do banco de dados
     *
     * @return lista de vendas
     */
    public List<ModelVenda> listarVendas() {
        return entityManager.createQuery(" FROM " + ModelVenda.class.getName() + " as v GROUP BY v.pedido")
        		.getResultList();
    }

    /**
     * Remove um venda da base de dados
     *
     * @param pModelVenda
     * @return boolean
     */
    public boolean removerVenda(ModelVenda pModelVenda) {
        try {
            entityManager.getTransaction().begin();
            pModelVenda = entityManager.find(ModelVenda.class, pModelVenda.getID());
            entityManager.remove(pModelVenda);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;

    }

    /**
     * Recupera um venda no banco pela sua id
     *
     * @param pId
     * @return venda
     */
    public ModelVenda recuperarVenda(long pId) {

        ModelVenda modelVenda = null;

        try {
            entityManager.getTransaction().begin();
            modelVenda = entityManager.find(ModelVenda.class, pId);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
        return modelVenda;
    }

    /**
     * Retorna o numero do ultimo pedido do banco
     *
     * @return ModelVenda
     */
    public long getUltimoPedido() {
        try {
            Query qry = entityManager.createQuery("SELECT MAX(o.pedido) FROM " + ModelVenda.class.getName() + " o");

            Object pedidoUltimaVenda = qry.getSingleResult();
            if (pedidoUltimaVenda != null) {
                return (Long) pedidoUltimaVenda;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
