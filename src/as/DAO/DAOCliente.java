package as.DAO;

import java.util.List;

import as.model.ModelCliente;
import as.util.EntidadeUtil;

public class DAOCliente extends DAO {

    public DAOCliente() {
        if (entityManager == null) {
            entityManager = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();
        }
    }

    /**
     * Salva o cliente na base de dados
     *
     * @param pModelCliente
     * @return id do cliente
     */
    public long salvarCliente(ModelCliente pModelCliente) {

        try {
            //iniciado a transacao de inserir
            entityManager.getTransaction().begin();

            //salvando o objeto no banco
            entityManager.persist(pModelCliente);

            //commit na transacao (efetivando a operacao anterior
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //desfaco a operacao caso ocorra alguma excessao na operacao de insert
            entityManager.getTransaction().rollback();
            return 0;
        }

        //retorno o id do objeto inserido
        return pModelCliente.getId();
    }

    /**
     * Atualiza um cliente da base de dados
     *
     * @param pModelCliente
     * @return boolean
     */
    public boolean atualizarCliente(ModelCliente pModelCliente) {
        try {

            //inicio a transacao
            entityManager.getTransaction().begin();

            // e recupero o cliente a ser alterado
            pModelCliente = entityManager.find(ModelCliente.class, pModelCliente.getId());

            //se não for nulo (se existir mesmo aquele cliente) faco o update (também com metodo persist)
            if (pModelCliente != null) {
                entityManager.persist(pModelCliente);

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
     * Recupero todos os clientes do banco de dados
     *
     * @return lista de clientes
     */
    public List<ModelCliente> listarClientes() {
        return entityManager.createQuery("FROM " + ModelCliente.class.getName()).getResultList();
    }

    /**
     * Remove um cliente da base de dados
     *
     * @param pModelCliente
     * @return boolean
     */
    public boolean removerCliente(ModelCliente pModelCliente) {
        try {
            entityManager.getTransaction().begin();
            pModelCliente = entityManager.find(ModelCliente.class, pModelCliente.getId());
            entityManager.remove(pModelCliente);
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
    public ModelCliente recuperarCliente(long pId) {

        ModelCliente modelCliente = null;

        try {
            entityManager.getTransaction().begin();
            modelCliente = entityManager.find(ModelCliente.class, pId);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
        return modelCliente;
    }
}
