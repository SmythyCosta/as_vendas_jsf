package as.test;

import javax.persistence.EntityManager;

import as.model.ModelCliente;
import as.util.EntidadeUtil;

public class TesteInserirCliente {

	public static void main(String[] args) {

		EntityManager em = EntidadeUtil.getInstancia().getEntityManagerFactory().createEntityManager();

		ModelCliente c = new ModelCliente();
		c.setNome("Joao lucas");
		c.setBairro("Mandaccau");
		c.setCep("12315");
		c.setCidade("Joao Pessoa");
		c.setLogradouro("rua ddaass fflloress");
		c.setNumero("44545");
		c.setTelefone("834444-3333");
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		System.out.println("OK insert");
	}

}
