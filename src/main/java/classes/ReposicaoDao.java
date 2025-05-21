package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ReposicaoDao {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ReposicaoDao() {
        this.entityManagerFactory = jakarta.persistence.Persistence.createEntityManagerFactory("B10000PU"); // Certifique-se de usar o nome correto da unidade de persistência
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    // Salvar reposição
    public void salvar(Reposicao reposicao) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(reposicao);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao salvar reposição: " + e.getMessage());
        }
    }

    // Buscar reposição por ID
    public Reposicao buscarPorId(int id) {
        return entityManager.find(Reposicao.class, id);
    }

    // Listar todas as reposições
    public List<Reposicao> listar() {
        String jpql = "SELECT r FROM Reposicao r";
        TypedQuery<Reposicao> query = entityManager.createQuery(jpql, Reposicao.class);
        return query.getResultList();
    }

    // Atualizar reposição
    public void atualizar(Reposicao reposicao) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(reposicao);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao atualizar reposição: " + e.getMessage());
        }
    }

    // Excluir reposição
    public void excluir(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Reposicao reposicao = entityManager.find(Reposicao.class, id);
            if (reposicao != null) {
                entityManager.remove(reposicao);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erro ao excluir reposição: " + e.getMessage());
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
