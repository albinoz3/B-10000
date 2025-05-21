package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RetiradaDao {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public RetiradaDao() {
        this.entityManagerFactory = jakarta.persistence.Persistence.createEntityManagerFactory("B10000PU"); // Certifique-se de usar o nome correto da unidade de persistência
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    // Salvar retirada
    public void salvar(Retirada retirada) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(retirada);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao salvar retirada: " + e.getMessage());
        }
    }

    // Buscar retirada por ID
    public Retirada buscarPorId(int id) {
        return entityManager.find(Retirada.class, id);
    }

    // Listar todas as retiradas
    public List<Retirada> listar() {
        String jpql = "SELECT r FROM Retirada r";
        TypedQuery<Retirada> query = entityManager.createQuery(jpql, Retirada.class);
        return query.getResultList();
    }

    // Atualizar retirada
    public void atualizar(Retirada retirada) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(retirada);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao atualizar retirada: " + e.getMessage());
        }
    }

    // Excluir retirada
    public void excluir(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Retirada retirada = entityManager.find(Retirada.class, id);
            if (retirada != null) {
                entityManager.remove(retirada);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao excluir retirada: " + e.getMessage());
        }
    }

    // Fechar a conexão
    public void fechar() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
