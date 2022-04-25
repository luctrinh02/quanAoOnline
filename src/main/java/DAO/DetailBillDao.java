package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.Bill;
import entities.Detailbill;


public class DetailBillDao implements DAO<Detailbill> {

	EntityManager em;
	public DetailBillDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<Detailbill> getAll() {
		String jqpl="select obj from Detailbill obj";
		TypedQuery<Detailbill> query=em.createQuery(jqpl,Detailbill.class);
		return query.getResultList();
	}
	@Override
	public List<Detailbill> getByPage(int page) {
		return null;
	}
	@Override
	public Detailbill findById(int id) {
		return em.find(Detailbill.class, id);
	}
	@Override
	public Detailbill create(Detailbill entity) throws Exception {
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
	public Detailbill update(Detailbill entity) throws Exception {
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
	public Detailbill delete(Detailbill entity) throws Exception {
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
	public List<Detailbill> getAll(Bill bill) {
		String jqpl="select obj from Detailbill obj where obj.bill=:bill";
		TypedQuery<Detailbill> query=em.createQuery(jqpl,Detailbill.class);
		query.setParameter("bill", bill);
		return query.getResultList();
	}
}
