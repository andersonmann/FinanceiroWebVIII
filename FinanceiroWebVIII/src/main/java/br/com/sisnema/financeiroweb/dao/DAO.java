package br.com.sisnema.financeiroweb.dao;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import org.hibernate.Session;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.LockException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.util.JPAUtil;

/**
 * Classe abstrata que herdara os comportamentos de {@link IDAO} e conter� atributos 
 * e funcionalidades gen�ricas a todas as filhas 
 */
public abstract class DAO<T> implements IDAO<T> {

	/**
	 * Como todas as DAOS ir�o possuir uma sessao, criaremos a mesma
	 * na classe pai, sendo ela HERDADA pelas filhas....
	 */
    protected EntityManager em;
	
	/**
	 * M�todo construtor de DAO para INICIALIZAR a sessao
	 * do hibernate
	 */
	public DAO() {
		em = JPAUtil.getEntityManager();
	}
	
	public void salvar(T model) throws DAOException {
		try {
			getSession().saveOrUpdate(model);
			commit();
			beginTransaction();
			
		} catch (OptimisticLockException ole){
			rollback();
			beginTransaction();
			
			throw new LockException("Este registro acaba de ser atualizado por outro usu�rio. "
					+ "Refa�a a pesquisa", ole);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
	public void excluir(T model) throws DAOException {
		try {
			getSession().delete(model);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
	
    protected final Session getSession() {
    	if(!em.isOpen()){
    		em = JPAUtil.getEntityManager();
    		    	}
    	return (Session) em.unwrap(Session.class);
    }
    
    protected final void commit() {
    	getSession().getTransaction().commit();
	}
    
    protected final void beginTransaction() {
    	JPAUtil.getEntityManager().getTransaction().begin();
    }
    
    protected final void rollback() {
    	JPAUtil.getEntityManager().getTransaction().rollback();
    }
}






