package main;

import java.util.ArrayList;
import java.util.Scanner;

import model.BSTConta;
import model.Conta;
import model.Pessoa;

public class AgenciaBancaria {

    static Scanner input = new Scanner(System.in);

    static ArrayList<Conta> contasBancarias;

    static BSTConta arvoreSaldos;

    public static void main(String[] args) {

        contasBancarias = new ArrayList<>();
        arvoreSaldos = new BSTConta();

        boolean rodando = true;

        while (rodando) {
            rodando = operacoes();
        }
    }

    public static boolean operacoes() {

        System.out.println("------------------------------------------------------");
        System.out.println("-------------Bem vindos a nossa Agência---------------");
        System.out.println("------------------------------------------------------");
        System.out.println("***** Selecione uma operação que deseja realizar *****");
        System.out.println("------------------------------------------------------");
        System.out.println("|   Opção 1 - Criar conta        |");
        System.out.println("|   Opção 2 - Depositar          |");
        System.out.println("|   Opção 3 - Sacar              |");
        System.out.println("|   Opção 4 - Transferir         |");
        System.out.println("|   Opção 5 - Listar contas      |");
        System.out.println("|   Opção 6 - Relatório BST      |");
        System.out.println("|   Opção 7 - Sair               |");

        int operacao = input.nextInt();

        switch (operacao) {

            case 1:
                criarConta();
                break;

            case 2:
                depositar();
                break;

            case 3:
                sacar();
                break;

            case 4:
                transferir();
                break;

            case 5:
                listarContas();
                break;

            case 6:
                mostrarBST();
                break;

            case 7:
                System.out.println("Flw é nóis!");
                return false;

            default:
                System.out.println("Opção inválida!");
                break;
        }

        return true;
    }

    public static void criarConta() {

        input.nextLine();

        System.out.println("\nNome:");
        String nome = input.nextLine();

        System.out.println("CPF:");
        String cpf = input.nextLine();

        System.out.println("Email:");
        String email = input.nextLine();

        Pessoa cliente = new Pessoa(nome, cpf, email);

        Conta conta = new Conta(cliente);

        contasBancarias.add(conta);

        arvoreSaldos.inserir(conta);

        System.out.println("--- Sua conta foi criada com sucesso! ---");

        System.out.println("Número da conta gerado: "
                + conta.getNumeroConta());
    }

    private static Conta encontrarConta(int numeroConta) {

        int esquerda = 0;

        int direita = contasBancarias.size() - 1;

        while (esquerda <= direita) {

            int meio = (esquerda + direita) / 2;

            Conta conta = contasBancarias.get(meio);

            if (conta.getNumeroConta() == numeroConta) {
                return conta;
            }

            if (conta.getNumeroConta() < numeroConta) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }

        return null;
    }

    public static void depositar() {

        System.out.println("Número da conta:");

        int numeroConta = input.nextInt();

        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {

            System.out.println("Qual valor deseja depositar?");

            Double valorDeposito = input.nextDouble();

            conta.depositar(valorDeposito);

        } else {

            System.out.println("--- Conta não encontrada ---");
        }
    }

    public static void sacar() {

        System.out.println("Número da conta:");

        int numeroConta = input.nextInt();

        Conta conta = encontrarConta(numeroConta);

        if (conta != null) {

            System.out.println("Qual valor deseja sacar?");

            Double valorSaque = input.nextDouble();

            conta.sacar(valorSaque);

        } else {

            System.out.println("--- Conta não encontrada ---");
        }
    }

    public static void transferir() {

        System.out.println("Número da conta que vai enviar a transferência:");

        int numeroContaRemetente = input.nextInt();

        Conta contaRemetente = encontrarConta(numeroContaRemetente);

        if (contaRemetente != null) {

            System.out.println("Número da conta do destinatário:");

            int numeroContaDestinatario = input.nextInt();

            Conta contaDestinatario =
                    encontrarConta(numeroContaDestinatario);

            if (contaDestinatario != null) {

                System.out.println("Valor da transferência:");

                Double valor = input.nextDouble();

                contaRemetente.transferencia(
                        contaDestinatario,
                        valor
                );

            } else {

                System.out.println("--- Conta destino não encontrada ---");
            }

        } else {

            System.out.println("--- Conta remetente não encontrada ---");
        }
    }

    public static void listarContas() {

        if (contasBancarias.size() > 0) {

            for (Conta conta : contasBancarias) {

                System.out.println(conta);
            }

        } else {

            System.out.println("--- Não há contas cadastradas ---");
        }
    }

    public static void mostrarBST() {

        arvoreSaldos = new BSTConta();

        for (Conta conta : contasBancarias) {
            arvoreSaldos.inserir(conta);
        }

        System.out.println(
                "\n=== CONTAS ORDENADAS POR SALDO ==="
        );

        arvoreSaldos.listarOrdenado();
    }
}
