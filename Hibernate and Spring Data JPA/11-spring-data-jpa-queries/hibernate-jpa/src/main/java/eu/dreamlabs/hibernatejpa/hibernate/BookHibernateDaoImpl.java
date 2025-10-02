package eu.dreamlabs.hibernatejpa.hibernate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import eu.dreamlabs.hibernatejpa.entity.BookEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookHibernateDaoImpl implements BookHibernateDao {
    private final EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public List<BookEntity> findAll() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<BookEntity> bookEntities = em.createNamedQuery("BookEntity.findAll", BookEntity.class);
            return bookEntities.getResultList();
        }

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
    public BookEntity getByTitleCriteria(String title) {
        try(EntityManager em = getEntityManager()) {
            // create cb
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            // create query
            CriteriaQuery<BookEntity> query = criteriaBuilder.createQuery(BookEntity.class);
            // set root element : equivalent to select from authors..
            Root<BookEntity> root = query.from(BookEntity.class);
            // set parameter expression
            ParameterExpression<String> titleParameter = criteriaBuilder.parameter(String.class);
            // set predicate
            Predicate titlePredicate = criteriaBuilder.equal(root.get("title"), titleParameter);
            // set query
            query.select(root).where(titlePredicate);
            // create typed query and bind param
            TypedQuery<BookEntity> typedQuery = em.createQuery(query);
            typedQuery.setParameter(titleParameter, title);
            return typedQuery.getSingleResult();
        }
    }

    @Override
    public BookEntity getByTitleNativeQuery(String title) {
        try (EntityManager em = getEntityManager()) {
            Query query = em.createNativeQuery(
                    "SELECT * FROM books b " +
                            "WHERE " +
                            "b.title = ?",
                    BookEntity.class);
            query.setParameter(1, title);
            return (BookEntity) query.getSingleResult();
        }
    }

    @Override
    public BookEntity findByTitleNamedQuery(String title) {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<BookEntity> query = getEntityManager().createNamedQuery(
                    "BookEntity.findByTitleNamedQuery", BookEntity.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        }
    }




    @Override
    public BookEntity findByISBN(String isbn) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<BookEntity> query = em.createQuery(
                    "SELECT b FROM BookEntity b WHERE b.isbn = :isbn",
                    BookEntity.class);
            query.setParameter("isbn", isbn);

            BookEntity book = query.getSingleResult();
            return book;
        } finally {
            em.close();
        }
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
