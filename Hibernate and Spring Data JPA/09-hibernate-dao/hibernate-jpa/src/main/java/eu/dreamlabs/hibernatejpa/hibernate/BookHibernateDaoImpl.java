package eu.dreamlabs.hibernatejpa.hibernate;

import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookHibernateDaoImpl implements BookHibernateDao {
    private final EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public BookEntity getById(Long id) {
        EntityManager em = getEntityManager();
        BookEntity book = getEntityManager().find(BookEntity.class, id);
        em.close();
        return book;
    }

    @Override
    public BookEntity getByTitle(String title) {
        EntityManager em = getEntityManager();
        TypedQuery<BookEntity> query = em.createQuery(
                "SELECT b FROM BookEntity b WHERE b.title = :title",
                        BookEntity.class);
        query.setParameter("title", title);
        BookEntity book = query.getSingleResult();
        em.close();
        return book;
    }

    @Override
    public BookEntity createBook(BookEntity book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;

    }

    @Override
    public BookEntity updateBook(BookEntity book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        BookEntity savedBook = em.find(BookEntity.class, book.getId());
        em.getTransaction().commit();
        em.close();
        return savedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        BookEntity book = em.find(BookEntity.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }
}
