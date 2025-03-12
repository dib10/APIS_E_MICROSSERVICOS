package VcRiquinho;

import java.util.List;

public class Main {

    public static void main(String[] args) {
    	
        // Carregando dados mockados
        List<Cliente> clientes = Mock.criarClientes();
        List<ProdutoInvestimento> produtos = Mock.criarProdutos();
        
        Mock.vincularContasAClientes(clientes, produtos);

        // ==================== CRUD DE CLIENTES ==================== //
        // READ (Listar clientes)
        System.out.println("=== CLIENTES CADASTRADOS ===");
        clientes.forEach(c -> System.out.println("- " + c.getNome()));
        System.out.println();

        // ==================== CRUD DE PRODUTOS ==================== //
        // READ (Listar produtos)
        System.out.println("=== PRODUTOS CADASTRADOS ===");
        produtos.forEach(p -> System.out.println("- " + p.getClass().getSimpleName() + ": " + p));
        System.out.println();

        // ==================== SIMULAÇÕES ==================== //
        //30 dias (dentro da carência do CDB)
        System.out.println("=== SIMULAÇÃO 30 DIAS (João Silva - PF) ===");
        simularRendimentos(clientes.get(0), 30); // João Silva (PF)

        //  90(fora da carência do CDB)
        System.out.println("\n=== SIMULAÇÃO 90 DIAS (João Silva - PF) ===");
        simularRendimentos(clientes.get(0), 90); // João Silva (PF)

        // 30 dias (PJ)
        System.out.println("\n=== SIMULAÇÃO 30 DIAS (Empresa LAUTON - PJ) ===");
        simularRendimentos(clientes.get(1), 30); // Empresa LAUTON (PJ)

        // 90 dias (PJ)
        System.out.println("\n=== SIMULAÇÃO 90 DIAS (Empresa LAUTON - PJ) ===");
        simularRendimentos(clientes.get(1), 90); // Empresa LAUTON (PJ)
    }

    // Método para simular todas as contas de um cliente
    private static void simularRendimentos(Cliente cliente, int dias) {
        for (Conta conta : cliente.getContas()) {
            double rendimentoBruto = conta.calcularRendimentoBruto(dias);
            double taxaServico;

            if (conta instanceof ContaCDI) {
                // taxa fixa de 0,07% para CDI
                taxaServico = ((ContaCDI) conta).calcularTaxaCDI(dias);
            } else {
                // se não usar a taxa padrão do cliente (PF/PJ)
                taxaServico = cliente.calcularTaxaDeServico(rendimentoBruto);
            }

            double rendimentoLiquido = rendimentoBruto - taxaServico;
            
            System.out.printf(
                "[Conta: %s] Rendimento Bruto: R$%.2f | Taxa: R$%.2f | Líquido: R$%.2f%n",
                conta.getClass().getSimpleName(), rendimentoBruto, taxaServico, rendimentoLiquido
            );
        }
    }
}
