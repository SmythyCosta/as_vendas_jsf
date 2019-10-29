package as.DAO;

import java.util.List;

import as.model.ModelProduto;
import as.util.EntidadeUtil;

public class DAOProduto extends DAO {

    public DAOProduto() {
        if (entityManager == null) {
            //criando o entityManager
            entityManager = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();
        }
    }

    /**
     * Salva o produto na base de dados
     *
     * @param pModelProduto
     * @return id do produto
     */
    public long salvarProduto(ModelProduto pModelProduto) {

        try {
            //iniciado a transacao de inserir
            entityManager.getTransaction().begin();

            //salvando o objeto no banco
            entityManager.persist(pModelProduto);

            //commit na transacao (efetivando a operacao anterior
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //desfaco a operacao caso ocorra alguma excessao na operacao de insert
            entityManager.getTransaction().rollback();
            return 0;
        }

        //retorno o id do objeto inserido
        return pModelProduto.getID();
    }

    /**
     * Atualiza um produto da base de dados
     *
     * @param pModelProduto
     * @return boolean
     */
    public boolean atualizarProduto(ModelProduto pModelProduto) {
        try {
            
            //inicio a transacao
            entityManager.getTransaction().begin();
            
            // e recupero o produto a ser alterado
            pModelProduto = entityManager.find(ModelProduto.class, pModelProduto.getID());

            //se não for nulo (se existir mesmo aquele produto) faco o update (também com metodo persist)
            if (pModelProduto != null) {
                entityManager.persist(pModelProduto);
                
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
     * Recupero todos os produtos do banco de dados
     *
     * @return lista de produtos
     */
    public List<ModelProduto> listarProdutos() {
        return entityManager.createQuery("FROM " + ModelProduto.class.getName()).getResultList();
    }

    /**
     * Remove um produto da base de dados
     *
     * @param pModelProduto
     * @return boolean
     */
    public boolean removerProduto(ModelProduto pModelProduto) {
        try {
            entityManager.getTransaction().begin();
            pModelProduto = entityManager.find(ModelProduto.class, pModelProduto.getID());
            entityManager.remove(pModelProduto);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;

    }

    public ModelProduto recuperarProduto(long pId) {
        ModelProduto modelProduto = null;

        try {
            entityManager.getTransaction().begin();
            modelProduto = entityManager.find(ModelProduto.class, pId);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
        return modelProduto;
    }
}
