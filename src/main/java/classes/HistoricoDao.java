package classes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.ArrayList;

public class HistoricoDao {

    private EntityManagerFactory entityManagerFactory;

    public HistoricoDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("b10000PU");
    }

    public void salvar(Historico historico) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(historico);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Erro ao salvar o registro de histórico: " + e.getMessage());
            throw e;
        } finally {
            if (em != null) em.close();
        }
    }

    public List<Historico> listarTodos() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Historico> query = em.createQuery("SELECT h FROM Historico h ORDER BY h.dataOperacao DESC, h.id DESC", Historico.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os registros de histórico: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (em != null) em.close();
        }
    }
    public List<Historico> listarPorTipoOperacao(TipoOperacao tipoOperacao) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Historico> query = em.createQuery("SELECT h FROM Historico h WHERE h.tipoOperacao = :tipo ORDER BY h.dataOperacao DESC, h.id DESC", Historico.class);
            query.setParameter("tipo", tipoOperacao);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar registros de histórico por tipo: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (em != null) em.close();
        }
    }
    public void fechar() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
