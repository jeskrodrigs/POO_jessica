package agencia.java;

import java.util.ArrayList;
class Operacao {
	int indice;
	String tipo;
	int valor;
	int saldo;
	
	Operacao (int indice, String tipo, int valor, int saldo){
		this.indice = indice;
		this.tipo = tipo;
		this.valor = valor;
		this.saldo = saldo;
	}
	public String toString() {
		return this.indice + ": " + this.tipo + ": " + this.valor + ": " + this.saldo;
	}
	}
class Conta {
	int nconta;
	int saldo;
	ArrayList<Operacao> extrato;
	ArrayList<String> tarifas;
	Conta(int nconta){
		this.nconta = nconta;
		this.saldo = 0;
		this.extrato = new ArrayList<>();
		this.tarifas = new ArrayList<>();
	}
	void criarOperacao(String tipo, int valor) {
		if(tipo.equals("abertura")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, valor, this.saldo));
			return;
		}
		if(tipo.equals("saque")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, -valor, this.saldo));
			return;
		}
		if(tipo.equals("deposito")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, valor, this.saldo));
			return;
		}
		if(tipo.equals("tarifa")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, -valor, this.saldo));
			return;
		}
		if(tipo.equals("extorno")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, valor, this.saldo));
			return;
		}
	}
	void extrato () {
		for(int i = 0; this.extrato.size() > i; i++) {
			System.out.println(this.extrato.get(i));
		}
	}
	void extratoParcial(int numero) {
		if (numero >= this.extrato.size()) {
			extrato();
			return;
		}
		if (numero < 1) {
			System.out.println("Erro: numero invalido");
			return;
		}
		for (int i = this.extrato.size() - numero; this.extrato.size() > i; i++) {
			System.out.println(this.extrato.get(i));
		}
	}
	void saque(int valor) {
		if (valor <=0) {
			System.out.println("erro: valor invalido");
			return;
		}
		if (this.saldo < valor) {
			System.out.println("erro: valor invalido");
			return;
		}
		this.saldo -= valor;
		criarOperacao("saque", valor);
		System.out.println("Saque de R$" + valor + " realizado");
	}
	void deposito(int valor){
		if (valor <= 0) {
			System.out.println("erro: valor invalido");
			return;
		}
		this.saldo += valor;
		criarOperacao("deposito", valor);
		System.out.println("Depósito de R$" + valor + " realizado");
	}
	void tarifa(int valor) {
		if (valor <=0) {
			System.out.println("erro: valor inválido");
			return;
		}
		this.saldo -= valor;
		criarOperacao("tarifa", valor);
		System.out.println("Tarifa de R$" + valor + " realizada");
	}
	boolean verificarExtorno(String indice) {
		for(int i = 0; this.tarifas.size() > i; i++) {
			if(indice.equals(tarifas.get(i))) {
				return true;
			}
		}
		return false;
	}
	boolean verificarIndice(int indice) {
			if(this.extrato.size() < indice) {
				return true;
			}
			return false;
	}
	void extornar(String index) {
			int indice = Integer.parseInt(index);
			if(verificarIndice(indice)) {
				System.out.println("erro: indice " + indice + " é invalido");
				return;
			}
			if(verificarExtorno(index)) {
				System.out.println("erro: indice " + indice + " ja estornado");
				return;
			}
			if(this.extrato.get(indice).tipo.equals("tarifa")) {
				int valor = -this.extrato.get(indice).valor;
				this.saldo += valor;
				this.tarifas.add(index);
				criarOperacao("extorno", valor);
				System.out.println("Extrono de R$" + valor + " realizado");
			} else {
			System.out.println("erro: indice " + indice + " não é tarifa");
			}
		}
	public String toString(){
		return "Conta: " + this.nconta + "\nSaldo: " + this.saldo; 	
	}
}
public class Agencia {
	Agencia(){

	}
	public static void main (String[] args) {
		Conta conta = new Conta(4002);
		conta.criarOperacao("abertura", 0);
		System.out.println(conta);
		conta.deposito(100);
		conta.saque(25);
		conta.deposito(10);
		conta.deposito(1000);
		conta.saque(600);
		conta.saque(40);
		conta.deposito(30);
		conta.tarifa(400);
		ArrayList<String> array = new ArrayList<>();
		array.add("8");
		array.add("40");
		array.add("2");
		array.add("8");
		array.add("20");
		for (int i = 0; array.size() > i; i++) {
			conta.extornar(array.get(i));
		}
		System.out.println(conta);
		conta.extrato();
	}
}
