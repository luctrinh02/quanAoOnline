package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.Category;


public class CategoryDao implements DAO<Category> {

	EntityManager em;
	public CategoryDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<Category> getAll() {
		String jqpl="select obj from Category obj";
		TypedQuery<Category> query=em.createQuery(jqpl,Category.class);
		return query.getResultList();
	}
	@Override
	public List<Category> getByPage(int page) {
		return null;
	}
	@Override
	public Category findById(int id) {
		return em.find(Category.class, id);
	}
	@Override
	public Category create(Category entity) throws Exception {
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
	public Category update(Category entity) throws Exception {
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
	public Category delete(Category entity) throws Exception {
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

}
