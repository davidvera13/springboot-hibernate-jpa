package eu.dreamlabs.hibernatejpa.dao.method03hibernate;

import eu.dreamlabs.hibernatejpa.entity.AuthorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    // we use named query here
    @Override
    public List<AuthorEntity> findAll() {
        try (EntityManager em = getEntityManager()) {
            TypedQuery<AuthorEntity> authors = em
                    .createNamedQuery(
                            "AuthorEntity.findAll",
                            AuthorEntity.class);
            return authors.getResultList();
        }
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
    public AuthorEntity getByNameNamedQuery(String firstName, String lastName) {
        TypedQuery<AuthorEntity> query = getEntityManager().createNamedQuery(
                "AuthorEntity.getByNameNamedQuery",
                AuthorEntity.class
        );
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);

        return query.getSingleResult();
    }

    @Override
    public AuthorEntity getByNameCriteria(String firstName, String lastName) {
        try (EntityManager em = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<AuthorEntity> query = criteriaBuilder
                    .createQuery(AuthorEntity.class);

            Root<AuthorEntity> root = query.from(AuthorEntity.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            //query.select(root)
            //        .where(criteriaBuilder.and(firstNamePred, lastNamePred));
            query.select(root)
                    .where(firstNamePred, lastNamePred);


            TypedQuery<AuthorEntity> typedQuery = em.createQuery(query);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);

            return  typedQuery.getSingleResult();
        }
    }

    @Override
    public AuthorEntity getByNameNativeQuery(String firstName, String lastName) {
        try (EntityManager em = getEntityManager()) {
            Query query = em.createNativeQuery(
                    "SELECT * FROM authors a " +
                            "WHERE " +
                            "a.first_name = ? AND " +
                            "a.last_name = ?",
                    AuthorEntity.class   // <-- tell JPA to map result to entity
            );
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);
            return (AuthorEntity) query.getSingleResult();
        }
    }

    @Override
    public List<AuthorEntity> listAuthorByLastNameLike(String lastName) {
        try (EntityManager em = getEntityManager()) {
            // Unchecked assignment: 'java. util. List' to 'java. util. List<eu. dreamlabs. hibernatejpa. entity. AuthorEntity>'
            // Query query = em.createQuery("SELECT a from AuthorEntity a where a.lastName like :last_name");
            TypedQuery<AuthorEntity> query = em.createQuery(
                    "SELECT a from AuthorEntity a where a.lastName like :last_name",
                    AuthorEntity.class);
            query.setParameter("last_name", lastName + "%");
            List<AuthorEntity> authors = query.getResultList();
            authors.forEach(author -> {
                System.out.println(author.getFirstName() + " " + author.getLastName());
            });
            return authors;
        }
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
