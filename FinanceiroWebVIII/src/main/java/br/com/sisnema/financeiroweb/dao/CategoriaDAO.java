package br.com.sisnema.financeiroweb.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.dialect.lock.OptimisticEntityLockException;

import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.LockException;
import br.com.sisnema.financeiroweb.model.Categoria;

public class CategoriaDAO extends DAO<Categoria> {

	public Categoria obterPorId(Categoria filtro) {
		return getSession().get(Categoria.class, filtro.getCodigo());
	}

	public Categoria salvarCategoria(Categoria categoriaDaTela) throws DAOException {
		try {
			Categoria c = (Categoria) getSession().merge(categoriaDaTela);
			getSession().flush();
			getSession().clear();
			return c;
		} catch (OptimisticEntityLockException e) {
			throw new LockException("Registro alterado por outro usuário, refaça a pesquisa", e);
		}
	}

	@Override
	public void excluir(Categoria model) throws DAOException {
		model = obterPorId(model);
		commit();
		beginTransaction();
	}

	public List<Categoria> pesquisar(Categoria filtros) {
		String hql = "select c from Categoria c where c.pai is null and c.usuario = :usuario";
		Query consulta = getSession().createQuery(hql);
		consulta.setParameter("usuario", filtros.getUsuario());
		return consulta.list();
	}

}
