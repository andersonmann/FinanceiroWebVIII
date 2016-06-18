package br.com.sisnema.financeiroweb.negocio;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

import br.com.sisnema.financeiroweb.dao.LancamentoDAO;
import br.com.sisnema.financeiroweb.exception.DAOException;
import br.com.sisnema.financeiroweb.exception.RNException;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Lancamento;
import br.com.sisnema.financeiroweb.vo.LancamentoVO;

public class LancamentoRN extends RN<Lancamento> {

	public LancamentoRN() {
		super(new LancamentoDAO());
	}

	public List<LancamentoVO> pesquisar(Conta conta, Date dataInicio, Date dataFim) {
		return ((LancamentoDAO) dao).pesquisar(conta, dataInicio, dataFim);
	}
	
	public List<LancamentoVO> pesquisarListaPrincipal(Conta conta, Date dataInicio, Date dataFim) {
		List<LancamentoVO> lancamentos = pesquisar(conta, dataInicio, dataFim);
		float saldo = saldo(conta, DateUtils.addDays(dataInicio, -1));
		LancamentoVO lancInicial = new LancamentoVO(null, null, null, null, saldo, 1);
		
		for (LancamentoVO lancamentoVO : lancamentos) {
				  // passa para negativo caso a categoria do lancamento seja uma DESPESA
			saldo += (lancamentoVO.getValor().floatValue() * lancamentoVO.getFatorCategoria());
			lancamentoVO.setSaldoNaData(saldo);
		}
		
		lancamentos.add(0, lancInicial);
		
		return lancamentos;
	}

	public float saldo(Conta conta, Date date){
		float saldoInicial = conta.getSaldoInicial();
		float saldoNaData = ((LancamentoDAO) dao).saldo(conta, date);
		return saldoInicial + saldoNaData;
	}

	public void salvar(Lancamento model) throws RNException {
		try {
			
			if(model.getCategoria().getPai() == null){
				throw new RNException("Categoria inválida. Selecione uma Categoria menos genérica.");
			}
			
			dao.salvar(model);
		} catch (DAOException e) {
			throw new RNException("Não foi possivel salvar. Erro: "+e.getMessage());
		}
	}
}
