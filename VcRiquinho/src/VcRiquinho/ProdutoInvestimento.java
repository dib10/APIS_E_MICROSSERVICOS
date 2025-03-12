package VcRiquinho;

public abstract class ProdutoInvestimento {
	
	protected String nome;
	protected String descricao;
    protected Conta conta; 

	public ProdutoInvestimento(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	};
	
	public abstract double calcularRendimento(int dias, double saldoPorProduto);//saldoporproduto é novo
	
	
	public void setConta(Conta conta) {
        this.conta = conta;
    }
    
    public Conta getConta() {
        return conta;
    }
}