package as.DAO;

import java.util.List;

import javax.persistence.NoResultException;

import as.model.ModelUsuario;
import as.util.EntidadeUtil;

public class DAOUsuario extends DAO {

    public DAOUsuario() {
        if (entityManager == null) {
            //criando o entityManager
            entityManager = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();
        }
    }

    /**
     * Salva o usuario na base de dados
     *
     * @param pUsuario
     * @return id do usuario
     */
    public long salvarUsuario(ModelUsuario pUsuario) {

        try {
            //iniciado a transacao de inserir
            entityManager.getTransaction().begin();

            //salvando o objeto no banco
            entityManager.persist(pUsuario);

            //commit na transacao (efetivando a operacao anterior
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            //desfaco a operacao caso ocorra alguma excessao na operacao de insert
            entityManager.getTransaction().rollback();
            return 0;
        }

        //retorno o id do objeto inserido
        return pUsuario.getID();
    }

    /**
     * Atualiza um usuario da base de dados
     *
     * @param pUsuario
     * @return boolean
     */
    public boolean atualizarUsuario(ModelUsuario pUsuario) {
        try {

            //inicio a transacao
            entityManager.getTransaction().begin();

            // e recupero o usuario a ser alterado
            pUsuario = entityManager.find(ModelUsuario.class, pUsuario.getID());

            //se não for nulo (se existir mesmo aquele usuario) faco o update (também com metodo persist)
            if (pUsuario != null) {
                entityManager.persist(pUsuario);

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
     * Recupero todos os usuarios do banco de dados
     *
     * @return lista de usuarios
     */
    public List<ModelUsuario> listarUsuarios() {
        return entityManager.createQuery("FROM " + ModelUsuario.class.getName()).getResultList();
    }

    /**
     * Remove um usuario da base de dados
     *
     * @param pUsuario
     * @return boolean
     */
    public boolean removerUsuario(ModelUsuario pUsuario) {
        try {
            entityManager.getTransaction().begin();
            pUsuario = entityManager.find(ModelUsuario.class, pUsuario.getID());
            entityManager.remove(pUsuario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;

    }

    /**
     * Recupera um usuario no banco pela sua id
     *
     * @param pID
     * @return usuario
     */
    public ModelUsuario recuperarUsuario(long pID) {

        ModelUsuario modelUsuario = null;

        try {
            entityManager.getTransaction().begin();
            modelUsuario = entityManager.find(ModelUsuario.class, pID);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
        return modelUsuario;
    }

    /**
     * * VAlida o usuario informado
     *
     * @param pUsuario
     * @return ModelUsuario
     */
    public ModelUsuario validarLogin(ModelUsuario pUsuario) {

        try {
            Object resultado = entityManager.createQuery("FROM ModelUsuario WHERE usu_login = :login AND usu_senha =:senha")
                .setParameter("login", pUsuario.getLogin())
                .setParameter("senha", pUsuario.getSenha())
                .getSingleResult();
            ModelUsuario usuarioBanco = (ModelUsuario) resultado;
            return usuarioBanco;
        } catch (NoResultException e) {
            return null;
        }

    }
    
    /**
     * Verifica se ja existe um outro usuario com o login informado
     *
     * @param pLogin 
     * @return boolean
     */
    public boolean existeUsuario(String pLogin) {

        try {
            entityManager.createQuery("FROM ModelUsuario WHERE usu_login = :login")
                .setParameter("login", pLogin)
                .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }

    }
}
