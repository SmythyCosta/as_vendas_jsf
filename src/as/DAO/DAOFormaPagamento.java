package as.DAO;

import java.util.List;

import as.model.ModelFormaPagamento;
import as.util.EntidadeUtil;

public class DAOFormaPagamento extends DAO {

    public DAOFormaPagamento() {
        if (entityManager == null) {
            //criando o entityManager
            entityManager = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();
        }
    }

    /**
     * Salva o formaPagamento na base de dados
     *
     * @param pModelFormaPagamento
     * @return id do formaPagamento
     */
    public long salvarFormaPagamento(ModelFormaPagamento pModelFormaPagamento) {

        try {
            //iniciado a transacao de inserir
            entityManager.getTransaction().begin();

            //salvando o objeto no banco
            entityManager.persist(pModelFormaPagamento);

            //commit na transacao (efetivando a operacao anterior
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //desfaco a operacao caso ocorra alguma excessao na operacao de insert
            entityManager.getTransaction().rollback();
            return 0;
        }

        //retorno o id do objeto inserido
        return pModelFormaPagamento.getID();
    }

    /**
     * Atualiza um formaPagamento da base de dados
     *
     * @param pModelFormaPagamento
     * @return boolean
     */
    public boolean atualizarFormaPagamento(ModelFormaPagamento pModelFormaPagamento) {
        try {
            
            //inicio a transacao
            entityManager.getTransaction().begin();
            
            // e recupero o formaPagamento a ser alterado
            pModelFormaPagamento = entityManager.find(ModelFormaPagamento.class, pModelFormaPagamento.getID());

            //se não for nulo (se existir mesmo aquele formaPagamento) faco o update (também com metodo persist)
            if (pModelFormaPagamento != null) {
                entityManager.persist(pModelFormaPagamento);
                
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
     * Recupero todos os formaPagamentos do banco de dados
     *
     * @return lista de formaPagamentos
     */
    public List<ModelFormaPagamento> listarFormaPagamentos() {
        return entityManager.createQuery("FROM " + ModelFormaPagamento.class.getName()).getResultList();
    }

    /**
     * Remove um formaPagamento da base de dados
     *
     * @param pModelFormaPagamento
     * @return boolean
     */
    public boolean removerFormaPagamento(ModelFormaPagamento pModelFormaPagamento) {
        try {
            entityManager.getTransaction().begin();
            pModelFormaPagamento = entityManager.find(ModelFormaPagamento.class, pModelFormaPagamento.getID());
            entityManager.remove(pModelFormaPagamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;
    }

    /**
     * Recupera um cliente no banco pela sua id
     *
     * @param pId
     * @return cliente
     */
    public ModelFormaPagamento recuperarFormaPagamento(long pId) {

        ModelFormaPagamento modelFormaPagamento = null;

        try {
            entityManager.getTransaction().begin();
            modelFormaPagamento = entityManager.find(ModelFormaPagamento.class, pId);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
        return modelFormaPagamento;
    }
}
