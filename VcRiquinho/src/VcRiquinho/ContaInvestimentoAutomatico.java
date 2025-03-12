package VcRiquinho;

//mexer aq 
import java.util.ArrayList;
import java.util.List;

public class ContaInvestimentoAutomatico extends Conta {
    private List<ProdutoInvestimento> produtos = new ArrayList<>();

    public ContaInvestimentoAutomatico(double saldoInicial) {
        super(saldoInicial);
    }

    public void adicionarProduto(ProdutoInvestimento produto) {
        produto.setConta(this); // Vincula a conta ao produto
        produtos.add(produto);
    }

    @Override
    public double calcularRendimentoBruto(int dias) {
        if (produtos.isEmpty()) return 0;
        
        double saldoPorProduto = saldo / produtos.size(); // Divide o saldo igualmente entre os produtos
        double rendimentoTotal = 0;
        
        for (ProdutoInvestimento p : produtos) {
            double rendimentoProduto = p.calcularRendimento(dias, saldoPorProduto);
            
            // Debug
            System.out.println("Produto: " + p + " | Rendimento: " + rendimentoProduto + " | Saldo Alocado: " + saldoPorProduto);
            
            // debug p verificar se a carência está sendo verificada corretamente
            if (p instanceof RendaFixa) {
                RendaFixa rendaFixa = (RendaFixa) p;
                if (dias < rendaFixa.getPeriodoDeCarencia()) {
                    System.out.println("Produto: " + p + " está dentro da carência e será ignorado.");
                    continue; // Ignora o produto se estiver dentro da carência
                }
            }
            
            rendimentoTotal += rendimentoProduto;
        }
        
        return rendimentoTotal;
    }

}