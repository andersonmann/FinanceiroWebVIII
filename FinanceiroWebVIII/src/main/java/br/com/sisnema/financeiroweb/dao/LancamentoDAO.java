package br.com.sisnema.financeiroweb.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.vo.LancamentoVO;

public class LancamentoDAO extends DAO<Lancamento> {

	public Lancamento obterPorId(Lancamento filtro) {
		return getSession().get(Lancamento.class, filtro.getCodigo());
	}

	public List<Lancamento> pesquisar(Lancamento filtros) {
		return null;
	}

	public List<LancamentoVO> pesquisar(Conta conta, Date dataInicio, Date dataFim){
		Criteria criteria = getSession().createCriteria(Lancamento.class);
		criteria.add(Restrictions.eq("conta", conta));
		if(dataInicio != null && dataFim != null){
			criteria.add(Restrictions.between("data", dataInicio, dataFim));
		}
		else if(dataInicio != null){
			criteria.add(Restrictions.ge("data", dataInicio));
		} else {
			criteria.add(Restrictions.le("data", dataFim));
		}
		criteria.addOrder(Order.asc("data"));
		return null;
		
	}

}
