package br.com.sisnema.financeiroweb.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import br.com.sisnema.financeiroweb.domain.UsuarioPermissao;
import br.com.sisnema.financeiroweb.model.Conta;
import br.com.sisnema.financeiroweb.model.Usuario;
import br.com.sisnema.financeiroweb.negocio.ContaRN;

/**
 * Objetivo desta classe � conter os dados do usuario logado armazendo em um
 * �nico objeto tudo que possa ser necessario, o qual estar� em escopo de
 * sess�o.
 */
@ManagedBean
@SessionScoped
public class ContextoBean implements Serializable {

	private static final long serialVersionUID = 1685484721761615411L;

	private List<String> landscapes;

	/** Contem a instancia do usuario logado */
	private Usuario usuarioLogado = null;

	private Conta contaAtiva;

	public ContextoBean() {
		landscapes = new ArrayList<String>();
		landscapes.add("n1.jpg");
		landscapes.add("n2.jpg");
		landscapes.add("n3.jpg");
		landscapes.add("n4.jpg");
		landscapes.add("n5.jpg");
		landscapes.add("n6.jpg");
		landscapes.add("n7.jpg");
		landscapes.add("n8.jpg");
	}

	public Usuario getUsuarioLogado() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		usuarioLogado = (Usuario) session.getAttribute(LoginBean.USUARIO_LOGADO);
		return usuarioLogado;
	}

	/**
	 * M�todo que recebe uma determinada permiss�o e verifica se o usu�rio a
	 * possui
	 * 
	 * @param role
	 *            - permiss�o a ser verificada
	 * @return valor boleano informando se usu�rio possui a permiss�o
	 *         parametrizada
	 */
	public boolean hasRole(String role) {
		Usuario user = getUsuarioLogado();
		return user != null && user.getPermissao().contains(UsuarioPermissao.valueOf(role));
	}

	public boolean hasRole(Usuario us, String role) {
		return us.getPermissao().contains(UsuarioPermissao.valueOf(role));
	}

	public Conta getContaAtiva() {
		// primeiro acesso a conta esta nula
		if (contaAtiva == null) {
			Usuario usuario = getUsuarioLogado();

			if (usuario == null) {
				return null;
			}

			ContaRN contaRN = new ContaRN();
			contaAtiva = contaRN.buscarFavorita(usuario);

			// Caso usuario nao possua conta favorita
			if (contaAtiva == null) {
				// busca todas as contas do usuario
				List<Conta> contas = contaRN.pesquisar(new Conta(usuario));
				if (contas != null) {

					// sai do loop apos encontrar primeira conta
					for (Conta conta : contas) {
						contaAtiva = conta;
						break;
					}
				}
			}
		}
		return contaAtiva;
	}

	/**
	 * Mais a frente teremos o programa lancamento que ira fazer um lancamento
	 * em uma CONTA_ATIVA logo toda a vez que alteramos uma conta na combo
	 * devemos alterar-la na sessao.
	 */
	public void setContaAtiva(ValueChangeEvent event) {
		Integer codigo = (Integer) event.getNewValue();
		ContaRN contaRN = new ContaRN();

		contaAtiva = contaRN.obterPorId(new Conta(codigo));
	}

	public List<String> getLandscapes() {
		return landscapes;
	}

}
