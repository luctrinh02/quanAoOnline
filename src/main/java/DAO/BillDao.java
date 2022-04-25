package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.Bill;
import entities.User;


public class BillDao implements DAO<Bill> {

	EntityManager em;
	public BillDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<Bill> getAll() {
		String jqpl="select obj from Bill obj order by obj.id desc";
		TypedQuery<Bill> query=em.createQuery(jqpl,Bill.class);
		return query.getResultList();
	}
	@Override
	public List<Bill> getByPage(int page) {
		return null;
	}
	@Override
	public Bill findById(int id) {
		return em.find(Bill.class, id);
	}
	@Override
	public Bill create(Bill entity) throws Exception {
		try {
			this.em.getTransaction().begin();
			this.em.persist(entity);
			System.out.println(entity.getUser().getId()+"-"+entity.getUserId());
			this.em.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			this.em.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public Bill update(Bill entity) throws Exception {
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
	public Bill delete(Bill entity) throws Exception {
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
	public List<Bill> getAll(User user) {
		String jqpl="select obj from Bill obj where obj.user=:user order by obj.id desc";
		TypedQuery<Bill> query=em.createQuery(jqpl,Bill.class);
		query.setParameter("user", user);
		return query.getResultList();
	}
}
