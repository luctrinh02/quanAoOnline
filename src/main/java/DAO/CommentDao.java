package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import Utils.JpaUtil;
import entities.Comment;


public class CommentDao implements DAO<Comment> {

	EntityManager em;
	public CommentDao(){
		em= JpaUtil.getEntityManager();
	}
	@Override
	public List<Comment> getAll() {
		String jqpl="select obj from Comment obj";
		TypedQuery<Comment> query=em.createQuery(jqpl,Comment.class);
		return query.getResultList();
	}
	@Override
	public List<Comment> getByPage(int page) {
		return null;
	}
	@Override
	public Comment findById(int id) {
		return em.find(Comment.class, id);
	}
	@Override
	public Comment create(Comment entity) throws Exception {
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
	public Comment update(Comment entity) throws Exception {
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
	public Comment delete(Comment entity) throws Exception {
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
