package Classes;

import jakarta.persistence.*;
import java.util.List;

public class PecaDAO {

    private EntityManagerFactory entityManagerFactory;

    public PecaDAO() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("b10000PU");
    }

    public void salvar(Peca peca) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(peca);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erro ao salvar a peça: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    public Peca buscarPorId(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.find(Peca.class, id);
        } finally {
            em.close();
        }
    }

    public List<Peca> listar() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Peca> query = em.createQuery("SELECT p FROM Peca p", Peca.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void atualizar(Peca peca) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(peca);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erro ao atualizar a peça: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }

    public void excluir(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            Peca peca = em.find(Peca.class, id);
            if (peca != null) {
                em.remove(peca);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Erro ao excluir a peça: " + e.getMessage());
            throw e;
        } finally {
            em.close();
        }
    }
    public boolean excluirPorNumeroSerie(String numeroSerie) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        boolean sucesso = false;

        try {
            tx.begin();
            TypedQuery<Peca> query = em.createQuery("SELECT p FROM Peca p WHERE p.numeroSerie = :numeroSerie", Peca.class);
            query.setParameter("numeroSerie", numeroSerie);           
            List<Peca> pecasEncontradas = query.getResultList();
            if (!pecasEncontradas.isEmpty()) {
                Peca pecaParaExcluir = pecasEncontradas.get(0);
                em.remove(pecaParaExcluir);
                tx.commit();
                sucesso = true;
            } else {
                if (tx.isActive()) tx.rollback();
                System.out.println("Nenhuma peça encontrada com o número de série: " + numeroSerie + " para exclusão.");
            }
        } catch (NoResultException e) {
            if (tx.isActive()) tx.rollback();
            System.err.println("Nenhuma peça encontrada com o número de série (NoResultException): " + numeroSerie);
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            System.err.println("Erro ao excluir a peça por número de série '" + numeroSerie + "': " + e.getMessage());
            throw e;
        } finally {
            if (em != null) em.close();
        }
        return sucesso;
    }

    public void fechar() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
