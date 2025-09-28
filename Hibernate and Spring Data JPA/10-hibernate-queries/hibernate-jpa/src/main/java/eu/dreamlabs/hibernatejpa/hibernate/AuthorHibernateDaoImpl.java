package eu.dreamlabs.hibernatejpa.hibernate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorHibernateDaoImpl implements AuthorHibernateDao {
    private final EntityManagerFactory emf;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    @Override
    public AuthorEntity createAuthor(AuthorEntity entity) {
        EntityManager em = getEntityManager();
        //em.joinTransaction();
        em.getTransaction().begin();
        em.persist(entity);
        em.flush();
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public AuthorEntity getById(Long id) {
        return getEntityManager().find(AuthorEntity.class, id);
    }

    @Override
    public AuthorEntity getByName(String firstName, String lastName) {
        TypedQuery<AuthorEntity> query = getEntityManager().createQuery(
                "SELECT auth FROM AuthorEntity auth " +
                        "WHERE " +
                        "auth.firstName = :firstName AND " +
                        "auth.lastName = :lastName",
                AuthorEntity.class
        );
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);

        return query.getSingleResult();
    }

    @Override
    public AuthorEntity updateAuthor(AuthorEntity entity) {
        // we force hibernate not to use 1st level caching (lazy operations) and we force transactions
        EntityManager em = getEntityManager();
        em.joinTransaction();
        em.merge(entity);
        em.flush();
        em.clear();
        return em.find(AuthorEntity.class, entity.getId());
    }

    @Override
    public void deleteAuthor(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        AuthorEntity author = em.find(AuthorEntity.class, id);
        em.remove(author);
        em.flush();
        em.getTransaction().commit();
    }
}
