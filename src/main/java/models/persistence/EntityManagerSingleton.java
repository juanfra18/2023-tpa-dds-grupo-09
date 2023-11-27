package models.persistence;


import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class EntityManagerSingleton {
  private static EntityManager instancia = null;
  private EntityManagerSingleton(){}

  public static EntityManager getInstance(){
    if(instancia == null || !instancia.isOpen()) {
      // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
      Map<String, String> env = System.getenv();
      Map<String, Object> configOverrides = new HashMap<String, Object>();

      String[] keys = new String[] {
              "DATABASE_URL",
              "javax__persistence__jdbc__driver",
              "javax__persistence__jdbc__password",
              "javax__persistence__jdbc__url",
              "javax__persistence__jdbc__user",
              "hibernate__hbm2ddl__auto",
              "hibernate__connection__pool_size",
              "hibernate__show_sql" };

      for (String key : keys) {

        try{
          if (key.equals("DATABASE_URL")) {
            String value = env.get(key);
            URI dbUri = new URI(value);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            configOverrides.put("javax.persistence.jdbc.url", value);
            configOverrides.put("javax.persistence.jdbc.user", username);
            configOverrides.put("javax.persistence.jdbc.password", password);
            configOverrides.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
          }
          String key2 = key.replace("__",".");
          if (env.containsKey(key)) {
            String value = env.get(key);
            configOverrides.put(key2, value);
          }
        } catch(Exception ex){
          System.out.println("Error configurando " + key);
        }
      }
      System.out.println("Config overrides ----------------------");
      for (String key : configOverrides.keySet()) {
        System.out.println(key + ": " + configOverrides.get(key));
      }

      String persistenceUnit = "simple-persistence-unit";
      /*
      if (env.containsKey("PersistenceUnit")) {
        persistenceUnit = env.get("PersistenceUnit");
      }*/

      instancia = Persistence.createEntityManagerFactory(persistenceUnit, configOverrides).createEntityManager();
    }
    return instancia;
  }
}
