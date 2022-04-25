package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.Category;
import entities.Product;


public class ProductDao implements DAO<Product> {

	EntityManager em;
	public ProductDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<Product> getAll() {
		String jqpl="select obj from Product obj";
		TypedQuery<Product> query=em.createQuery(jqpl,Product.class);
		return query.getResultList();
	}
	@Override
	public List<Product> getByPage(int page) {
		return null;
	}
	@Override
	public Product findById(int id) {
		return em.find(Product.class, id);
	}
	@Override
	public Product create(Product entity) throws Exception {
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
	public Product update(Product entity) throws Exception {
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
	public Product delete(Product entity) throws Exception {
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
	public List<Product> next4(int page,Category category){
		String jqpl="select obj from Product obj where obj.category=:category and obj.status=0 and obj.amount>0 order by obj.id desc";
		TypedQuery<Product> query=em.createQuery(jqpl,Product.class);
		query.setParameter("category", category);
		query.setFirstResult(page*4);
		query.setMaxResults(4);
		return query.getResultList();
	}
	public List<Product> All(Category category){
		String jqpl="select obj from Product obj where obj.category=:category order by obj.id desc";
		TypedQuery<Product> query=em.createQuery(jqpl,Product.class);
		query.setParameter("category", category);
		return query.getResultList();
	}
	public List<Product> All(int tag,Category category){
		String jqpl="select obj from Product obj where obj.category=:category and obj.tag=:tag order by obj.id desc";
		TypedQuery<Product> query=em.createQuery(jqpl,Product.class);
		query.setParameter("category", category);
		query.setParameter("tag", tag);
		return query.getResultList();
	}
}
