package as.DAO;

import java.util.List;

import as.model.ModelFornecedor;
import as.util.EntidadeUtil;

public class DAOFornecedor extends DAO {

    public DAOFornecedor() {
        if (entityManager == null) {
            //criando o entityManager
            entityManager = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();
        }
    }

    /**
     * Salva o fornecedor na base de dados
     *
     * @param pModelFornecedor
     * @return id do fornecedor
     */
    public long salvarFornecedor(ModelFornecedor pModelFornecedor) {

        try {
            //iniciado a transacao de inserir
            entityManager.getTransaction().begin();

            //salvando o objeto no banco
            entityManager.persist(pModelFornecedor);

            //commit na transacao (efetivando a operacao anterior
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //desfaco a operacao caso ocorra alguma excessao na operacao de insert
            entityManager.getTransaction().rollback();
            return 0;
        }

        //retorno o id do objeto inserido
        return pModelFornecedor.getId();
    }

    /**
     * Atualiza um fornecedor da base de dados
     *
     * @param pModelFornecedor
     * @return boolean
     */
    public boolean atualizarFornecedor(ModelFornecedor pModelFornecedor) {
        try {
            
            //inicio a transacao
            entityManager.getTransaction().begin();
            
            // e recupero o fornecedor a ser alterado
            pModelFornecedor = entityManager.find(ModelFornecedor.class, pModelFornecedor.getId());

            //se não for nulo (se existir mesmo aquele fornecedor) faco o update (também com metodo persist)
            if (pModelFornecedor != null) {
                entityManager.persist(pModelFornecedor);
                
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
     * Recupero todos os fornecedores do banco de dados
     *
     * @return lista de fornecedores
     */
    public List<ModelFornecedor> listarFornecedores() {
        return entityManager.createQuery("FROM " + ModelFornecedor.class.getName()).getResultList();
    }

    /**
     * Remove um fornecedor da base de dados
     *
     * @param pModelFornecedor
     * @return boolean
     */
    public boolean removerFornecedor(ModelFornecedor pModelFornecedor) {
        try {
            entityManager.getTransaction().begin();
            pModelFornecedor = entityManager.find(ModelFornecedor.class, pModelFornecedor.getId());
            entityManager.remove(pModelFornecedor);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;

    }

    /**
     * Recupera um fornecedor no banco pela sua id
     * @param pId
     * @return fornecedor
     */
    public ModelFornecedor recuperarFornecedor(long pId) {
        
        ModelFornecedor modelFornecedor = null;
        
        try {
            entityManager.getTransaction().begin();
            modelFornecedor = entityManager.find(ModelFornecedor.class, pId);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
        return modelFornecedor;
    }
}
