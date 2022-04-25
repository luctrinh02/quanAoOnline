package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.Cart;
import entities.CartPK;
import entities.User;


public class CartDao implements DAO<Cart> {

	EntityManager em;
	public CartDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<Cart> getAll() {
		String jqpl="select obj from Cart obj where";
		TypedQuery<Cart> query=em.createQuery(jqpl,Cart.class);
		return query.getResultList();
	}
	@Override
	public List<Cart> getByPage(int page) {
		return null;
	}
	@Override
	public Cart findById(int id) {
		return em.find(Cart.class, id);
	}
	@Override
	public Cart create(Cart entity) throws Exception {
		try {
			this.em.getTransaction().begin();
			this.em.persist(entity);
			this.em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			System.out.println("id: "+entity.getId().getProductId());
			this.em.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public Cart update(Cart entity) throws Exception {
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
	public Cart delete(Cart entity) throws Exception {
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
	public Cart findById(CartPK id) {
		return em.find(Cart.class, id);
	}
	public List<Cart> getAll(User user) {
		String jqpl="select obj from Cart obj where obj.user=:user";
		TypedQuery<Cart> query=em.createQuery(jqpl,Cart.class);
		query.setParameter("user", user);
		return query.getResultList();
	}
}
