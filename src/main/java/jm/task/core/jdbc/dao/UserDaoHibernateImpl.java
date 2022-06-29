package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                String sql = "CREATE TABLE if not exists `users` ( `id` bigint NOT NULL AUTO_INCREMENT," +
                        "`name` varchar(255) NOT NULL," +
                        "`lastName` varchar(255) NOT NULL," +
                        "`age` smallint NOT NULL, PRIMARY KEY (`id`))";
                session.createSQLQuery(sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                String sql = "DROP table IF EXISTS users";
                session.createSQLQuery(sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                User userFromTable = session.get(User.class, 1L);
                session.remove(userFromTable);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> resultList = null;
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                resultList = session.createQuery("SELECT i from User i", User.class).getResultList();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {
            try {
                session.beginTransaction();
                String sql = "delete from users";
                session.createSQLQuery(sql).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    //jpql
}
