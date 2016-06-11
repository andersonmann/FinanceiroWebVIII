package br.com.sisnema.financeiroweb.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

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

	/*
	 * Criar um método que pesquise todos os lancamentos de uma determinada
	 * conta em um determinado periodo de tempo ordenadas por data ascendente
	 */
	public List<LancamentoVO> pesquisar(Conta conta, Date dataInicio, Date dataFim) {

		// definimos sobre qual tabela sera a consulta
		Criteria criteria = getSession().createCriteria(Lancamento.class, "lanc");

		criteria.createAlias("lanc." + Lancamento.Fields.CATEGORIA.toString(), "categoria", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq(Lancamento.Fields.CONTA.toString(), conta));

		if (dataInicio != null && dataFim != null) {
			criteria.add(Restrictions.between(Lancamento.Fields.DATA.toString(), dataInicio, dataFim));

		} else if (dataInicio != null) {
			criteria.add(Restrictions.ge(Lancamento.Fields.DATA.toString(), dataInicio));

		} else if (dataFim != null) {
			criteria.add(Restrictions.le(Lancamento.Fields.DATA.toString(), dataFim));
		}

		criteria.addOrder(Order.asc(Lancamento.Fields.DATA.toString()));

		// projeção == campos que se deseja retornar
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property(Lancamento.Fields.CODIGO.toString()), LancamentoVO.Fields.CODIGO.toString())
				.add(Projections.property(Lancamento.Fields.DATA.toString()), LancamentoVO.Fields.DATA.toString())
				.add(Projections.property(Lancamento.Fields.DESCRICAO.toString()),
						LancamentoVO.Fields.DESCRICAO.toString())
				.add(Projections.property(Lancamento.Fields.VALOR.toString()), LancamentoVO.Fields.VALOR.toString())
				.add(Projections.property(Lancamento.Fields.FATOR_CATEGORIA.toString()),
						LancamentoVO.Fields.FATOR_CATEGORIA.toString()));

		// alteramos o resultado da consulta para um VO
		criteria.setResultTransformer(Transformers.aliasToBean(LancamentoVO.class));

		return criteria.list();
	}

}
