package models.persistence;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerSingleton {
  private static EntityManager instancia = null;
  private EntityManagerSingleton(){}

  public static EntityManager getInstance(){
    if(instancia == null || !instancia.isOpen())
      instancia = Persistence.createEntityManagerFactory("simple-persistence-unit").createEntityManager();

    return instancia;
  }
}
