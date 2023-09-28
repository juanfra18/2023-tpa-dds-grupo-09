package models.persistence;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerSingleton {
  private static final EntityManager instancia = Persistence.createEntityManagerFactory("simple-persistence-unit").createEntityManager();

  private EntityManagerSingleton(){}

  public static EntityManager getInstance(){
    return instancia;
  }
}
