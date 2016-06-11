package br.com.sisnema.financeiroweb.vo;

import java.math.BigDecimal;
import java.util.Date;

public class LancamentoVO {

	private Integer codigo;
	private Date data;
	private String descricao;
	private BigDecimal valor;
	private float saldoNaData;
	private int fatorCategoria;

	public LancamentoVO() {
	}

	public LancamentoVO(Integer codigo, Date data, String descricao, BigDecimal valor, float saldoNaData,
			int fatorCategoria) {
		super();
		this.codigo = codigo;
		this.data = data;
		this.descricao = descricao;
		this.valor = valor;
		this.saldoNaData = saldoNaData;
		this.fatorCategoria = fatorCategoria;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public float getSaldoNaData() {
		return saldoNaData;
	}

	public void setSaldoNaData(float saldoNaData) {
		this.saldoNaData = saldoNaData;
	}

	public int getFatorCategoria() {
		return fatorCategoria;
	}

	public void setFatorCategoria(int fatorCategoria) {
		this.fatorCategoria = fatorCategoria;
	}

	public enum Fields {
		CODIGO("codigo"), DESCRICAO("descricao"), VALOR("valor"), SALDO_NA_DATA("saldoNaData"), FATOR_CATEGORIA(
				"fatorCategoria"), DATA("data"),;

		private String property;

		private Fields(String property) {
			this.property = property;
		}

		@Override
		public String toString() {
			return property;
		}
	}

	@Override
	public String toString() {
		return "LancamentoVO [codigo=" + codigo + ", data=" + data + ", descricao=" + descricao + ", valor=" + valor
				+ ", saldoNaData=" + saldoNaData + ", fatorCategoria=" + fatorCategoria + "]";
	}

}
