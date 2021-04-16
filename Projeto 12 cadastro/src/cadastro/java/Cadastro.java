package cadastro.java;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

class Operacao {
	int id;
	String tipo;
	int valor;
	int saldo;
	
	Operacao (int indice, String tipo, int valor, int saldo){
		this.id = indice;
		this.tipo = tipo;
		this.valor = valor;
		this.saldo = saldo;
	}
	public String toString() {
		return this.id + ": " + this.tipo + ": " + this.valor + ": " + this.saldo;
	}
	}
class Conta {
	String nome;
	String id;
	int saldo;
	ArrayList<Operacao> extrato;
	ArrayList<String> tarifas;
	Conta(String nome, String id){
		this.nome = nome;
		this.id = id;
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
		if(tipo.equals("taxa")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, -valor, this.saldo));
			return;
		}
		if(tipo.equals("redimento")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, valor, this.saldo));
			return;
		}
		if(tipo.equals("transferenciaFeita")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, -valor, this.saldo));
			return;
		}
		if(tipo.equals("transferenciaRecebida")) {
			this.extrato.add(new Operacao(this.extrato.size(), tipo, valor, this.saldo));
			return;
		}
	}
	void virarMes() {
		
	}
	void transferir(Conta conta01, Conta conta02, int valor) {
		if(conta01.saldo < valor) {
			throw new RuntimeException("erro: saldo insuficiente");
		}
		conta01.saldo -= valor;
		conta01.criarOperacao("transferenciaFeita", valor);
		conta02.saldo += valor;
		conta02.criarOperacao("transferenciaRecebida", valor);
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
			throw new RuntimeException("erro: valor inválido");
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
				throw new RuntimeException("erro: indice " + indice + " é invalido");
			}
			if(verificarExtorno(index)) {
				throw new RuntimeException("erro: indice " + indice + " ja estornado");
			}
			if(this.extrato.get(indice).tipo.equals("tarifa")) {
				int valor = -this.extrato.get(indice).valor;
				this.saldo += valor;
				this.tarifas.add(index);
				criarOperacao("extorno", valor);
				System.out.println("Extrono de R$" + valor + " realizado");
			} else {
				throw new RuntimeException("erro: indice " + indice + " não é tarifa");
			}
		}
	public String toString(){
		return id + ":" + this.nome + ":" + this.saldo; 	
	}
}
class ContaPoupanca extends Conta{
	public ContaPoupanca(String nome, String id) {
		super(nome, id);
	}
	void virarMes() {
		this.saldo += this.saldo/100;
		this.criarOperacao("redimento", this.saldo/100);
	}
	public String toString(){
		return id + ":" + this.nome + ":" + this.saldo + ":CP"; 	
	}
}
class ContaCorrente extends Conta{
	public ContaCorrente(String nome, String id) {
		super(nome, id);
	}
	void virarMes(){
		this.saldo -= 20;
		this.criarOperacao("taxa", 20);
	}
	public String toString(){
		return id + ":" + this.nome + ":" + this.saldo + ":CC"; 	
	}
}
public class Cadastro {
	Map<String, Conta> contas;
	String id;
	Cadastro(){
		this.contas = new TreeMap<>();
		this.id = "0";
	}
	void criarId() {
		this.id = "" + (Integer.parseInt(this.id) + 1);
	}
	void adicionarCliente (String nome) {
		Conta conta01 = new ContaCorrente(nome, this.id);
		conta01.criarOperacao("abertura", 0);
		contas.put(id, conta01);
		criarId();
		Conta conta02 =  new ContaPoupanca(nome, this.id);
		conta02.criarOperacao("abertura", 0);
		contas.put(id, conta02);
		criarId();
		
	}
	Conta getConta(String id) {
		return contas.get(id);
	}
	boolean verificarConta(String id) {
		if(getConta(id) != null) {
			return true;
		}
		return false;
	}
	void deposito(String id, int valor) {
		if(!verificarConta(id)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta = getConta(id);
		conta.deposito(valor);
	}
	void saque(String id, int valor) {
		if(!verificarConta(id)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta = getConta(id);
		conta.saque(valor);
	}
	void extrato(String id) {
		if(!verificarConta(id)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta = getConta(id);
		conta.extrato();
	}
	void extratoParcial(String id, int numero) {
		if(!verificarConta(id)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta = getConta(id);
		conta.extratoParcial(numero);
	}
	void tarifa(String id, int valor) {
		if(!verificarConta(id)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta = getConta(id);
		conta.tarifa(valor);
	}
	void extornar(String id, String index) {
		if(!verificarConta(id)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta = getConta(id);
		conta.extornar(index);
	}
	void transferir(String id01, String id02, int valor) {
		if(!verificarConta(id01)) {
			throw new RuntimeException("Conta inválida");
		}
		if(!verificarConta(id02)) {
			throw new RuntimeException("Conta inválida");
		}
		Conta conta01 = getConta(id01);
		Conta conta02 = getConta(id02);
		conta01.transferir(conta01, conta02, valor);
	}
	void update() {
		for(Map.Entry<String, Conta> entry : this.contas.entrySet()) {
			entry.getValue().virarMes();
		}
	}
	void verClientes() {
		String aux = "";
		int contador = 0;
		String cliente = "";
		for(Map.Entry<String, Conta> entry : this.contas.entrySet()) {
			if(contador == 2) {
				contador = 0;
				cliente = "";
			}
			if(contador == 0) {
			cliente += "- " + entry.getValue().nome + "[" + entry.getKey() + ", ";
			}
			if(contador == 1) {
				cliente += entry.getKey() + "]\n";
				aux += cliente;
			}
			contador++;
		}
		System.out.println(aux);
	}
	public String toString () {
		String aux = "";
		for(Map.Entry<String, Conta> entry : this.contas.entrySet()) {
			aux += entry.getValue() + "\n";
		}
		return aux;
	}
	public static void main (String[] args) {
		Cadastro sistema = new Cadastro();
		sistema.adicionarCliente("Jesk");
		sistema.adicionarCliente("joão");
		sistema.deposito("0", 50);
		sistema.tarifa("0", 40);
		sistema.deposito("2", 200);
		sistema.transferir("2", "0", 199);
		sistema.deposito("1", 300000);
		sistema.update();
		sistema.update();
		sistema.extrato("0");
		sistema.extrato("2");
		ArrayList<String> array;
		array = new ArrayList<>();
		//array.add("3");
		array.add("2");
		//array.add("2");
		for (String index : array) {
			sistema.extornar("0", index);
		}
		sistema.extrato("0");
		//System.out.println(sistema);
	}
}
