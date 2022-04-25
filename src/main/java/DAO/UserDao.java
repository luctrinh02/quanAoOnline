package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.User;


public class UserDao implements DAO<User> {

	EntityManager em;
	public UserDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<User> getAll() {
		String jqpl="select obj from User obj";
		TypedQuery<User> query=em.createQuery(jqpl,User.class);
		return query.getResultList();
	}
	@Override
	public List<User> getByPage(int page) {
		return null;
	}
	@Override
	public User findById(int id) {
		return em.find(User.class, id);
	}
	@Override
	public User create(User entity) throws Exception {
		try {
			this.em.getTransaction().begin();
			this.em.persist(entity);
			this.em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public User update(User entity) throws Exception {
		try {
			this.em.getTransaction().begin();
			this.em.merge(entity);
			this.em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public User delete(User entity) throws Exception {
		try {
			this.em.getTransaction().begin();
			this.em.remove(entity);
			this.em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	public User findByEmail(String email) {
		String jqpl="select o from User o where o.email=:email";
		TypedQuery<User> query=this.em.createQuery(jqpl,User.class);
		query.setParameter("email", email);
		return query.getSingleResult();
	}
}
