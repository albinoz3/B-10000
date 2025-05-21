
package Classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UsuarioDAO {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public UsuarioDAO() {
        this.entityManagerFactory = jakarta.persistence.Persistence.createEntityManagerFactory("b10000PU");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public Usuario buscarUsuario(String login, String senha) {
    String senhaCriptografada = Criptografia.criptografarMD5(senha);

    String jpql = "SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha";
    TypedQuery<Usuario> query = entityManager.createQuery(jpql, Usuario.class);
    query.setParameter("login", login);
    query.setParameter("senha", senhaCriptografada);

    try {
        // Usando getResultList ao invés de getSingleResult
        List<Usuario> usuarios = query.getResultList();
        if (usuarios.isEmpty()) {
            return null;
        }
        return usuarios.get(0); // Retorna o primeiro resultado encontrado
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    public void fechar() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
