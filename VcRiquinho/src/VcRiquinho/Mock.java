package VcRiquinho;

import java.util.ArrayList;
import java.util.List;

public class Mock {

    // Método para criar clientes mockados
    public static List<Cliente> criarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        
        Cliente clientePF = new PessoaFisica("João Silva", "joao@email.com", "123.456.789-00");
        
        Cliente clientePJ = new PessoaJuridica("Empresa LAUTON", "contato@xyz.com", "12.345.678/0001-99");

        clientes.add(clientePF);
        clientes.add(clientePJ);

        return clientes;
    }

    public static List<ProdutoInvestimento> criarProdutos() {
        List<ProdutoInvestimento> produtos = new ArrayList<>();
        
        // Criando produto de Renda Fixa
        ProdutoInvestimento cdb = new RendaFixa("CDB Banco XPTO", "CDB 120% CDI", 0.05, 60);
        // Criando produto de Renda Variável
        ProdutoInvestimento acoes = new RendaVariavel("Ações Tech", "Carteira de ações de tecnologia", 0.08);

        produtos.add(cdb);
        produtos.add(acoes);

        return produtos;
    }

    // vincular contas aos clientes 
    public static void vincularContasAClientes(List<Cliente> clientes, List<ProdutoInvestimento> produtos) {
        // JOÃO SILVA

        // Conta Corrente 
        Conta contaCorrente = new ContaCorrente(5000);
        clientes.get(0).adicionarConta(contaCorrente); 
        
        // Conta CDI 
        Conta contaCDI = new ContaCDI(10000);
        clientes.get(0).adicionarConta(contaCDI); 
        
        // Conta Investimento Automático (com produtos)
        ContaInvestimentoAutomatico contaInvestimento = new ContaInvestimentoAutomatico(20000);
        contaInvestimento.adicionarProduto(produtos.get(0)); // Vincula o CDB
        contaInvestimento.adicionarProduto(produtos.get(1)); // Vincula as Ações
        clientes.get(0).adicionarConta(contaInvestimento); // João Silva

        //EMPRESA LAUTON

        // Conta Corrente 
        Conta contaCorrentePJ = new ContaCorrente(10000);
        clientes.get(1).adicionarConta(contaCorrentePJ); 
        
        // Conta CDI 
        Conta contaCDIPJ = new ContaCDI(15000);
        clientes.get(1).adicionarConta(contaCDIPJ); // Empresa LAUTON
        
        // Conta Investimento Automático 
        ContaInvestimentoAutomatico contaInvestimentoPJ = new ContaInvestimentoAutomatico(30000);
        contaInvestimentoPJ.adicionarProduto(produtos.get(0));  
        contaInvestimentoPJ.adicionarProduto(produtos.get(1));  
        clientes.get(1).adicionarConta(contaInvestimentoPJ); 
    }
}
