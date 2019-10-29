package as.util;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntidadeUtil  {
    
    private static EntidadeUtil INSTANCIA;
    private static EntityManagerFactory entityManagerFactory;
    
    public static EntidadeUtil getInstancia(){
        if(INSTANCIA == null){
            
            entityManagerFactory = Persistence.createEntityManagerFactory("ASVendas");
            INSTANCIA = new EntidadeUtil();
        }
        return INSTANCIA;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
}
