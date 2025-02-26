package cadastro;

public class TestaCadastro {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CadastroPessoas cadastro = new CadastroPessoas();
		
		Funcionario fun1 = new Funcionario("Sara Santos", new Data(17,06,2002),3000);
		Gerente ger1 = new Gerente("Caio Dib", new Data(11,12,2001),10000, "Gerente de Tecnologia");
		Cliente cli1 = new Cliente("Alexandre", new Data(24,01,2000),100);
		
		cadastro.cadastraPessoa(fun1);
		cadastro.cadastraPessoa(ger1);
		cadastro.cadastraPessoa(cli1);
		cadastro.imprimeCadastro(); // chama a impressão

	}

}
