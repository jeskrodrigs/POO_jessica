package topic.java;
import java.util.ArrayList;

class Pessoa {
	int idade;
	String nome; 
	
	Pessoa (String nome, int idade){
		this.idade = idade;
		this.nome = nome;
	}
}
public class Topic {
	ArrayList<Pessoa> cdPreferencial;
	ArrayList<Pessoa> cdNormal;
	int qTotal;
	int qPreferencial;
	Topic (int qTotal, int qPreferencial){
		this.cdPreferencial = new ArrayList<>();
		this.cdNormal = new ArrayList<>();
		this.qTotal = qTotal;
		this.qPreferencial = qPreferencial;
		criarVagas(qTotal-qPreferencial, cdNormal);
		criarVagas(qPreferencial, cdPreferencial);
	}
	void criarVagas (int vagas, ArrayList<Pessoa> array) {
		for (int i = 0; i < vagas; i ++) {
			array.add(null);
		}
	}
	boolean verificarPessoa(String nome) {
		for (Pessoa pessoa : this.cdNormal) {
			if(pessoa != null && pessoa.nome.equals(nome)) {
				return true;
			}
		}
		for (Pessoa pessoa : this.cdPreferencial) {
			if(pessoa != null && pessoa.nome.equals(nome)) {
				return true;
			}
		}
		return false;
	}
	int buscarVaga(ArrayList<Pessoa> array) {
		int i = 0;
		for (Pessoa pessoa : array) {
			if(pessoa == null) {
				return i;
			}
			i++;
		}
		return -1;
	}
	void addPessoa(String nome, int idade) {
		if(verificarPessoa(nome)) {
			System.out.println("erro: ja comrpou passagem");
			return;
		}
		int vaga = buscarVaga(this.cdPreferencial);
		if(idade >= 60){
			if(vaga >= 0) {
				this.cdPreferencial.set(vaga, new Pessoa(nome, idade));
			} else {
				vaga = buscarVaga(this.cdNormal);
				if(vaga >= 0) {
					this.cdNormal.set(vaga, new Pessoa(nome, idade));
				} else {
					System.out.println("erro: sem vagas");
				}
			}
		} else {
			vaga = buscarVaga(this.cdNormal);
			if(vaga >= 0) {
				this.cdNormal.set(vaga, new Pessoa(nome, idade));
			} else {
				vaga = buscarVaga(this.cdPreferencial);
				if(vaga >= 0) {
					this.cdPreferencial.set(vaga, new Pessoa(nome, idade));
				} else {
					System.out.println("erro: sem vagas");
				}
			}
		}
	}
	int getId(String nome, ArrayList<Pessoa> array) {
		int i = 0;
		for (Pessoa pessoa : array) {
			if(pessoa.nome.equals(nome)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	void removePassageiro (String nome){
		for (Pessoa pessoa : this.cdNormal) {
			int local = getId(nome, this.cdPreferencial);
			if(local >= 0){
				this.cdPreferencial.remove(local);
				return;
			}
			local = getId(nome, this.cdNormal);
			if (local >= 0){
				this.cdNormal.remove(local);
				return;
			}
			System.out.println("erro: não comprou uma passagem");
		}
	}
	public String toString() {
		String aux = "Preferenciais:\n [";
		for (Pessoa pessoa : this.cdPreferencial) {
			if (pessoa == null) {
				aux += "@ ";
			} else {
				aux += pessoa.nome + ":" + pessoa.idade + " ";
			}
		}
		aux += "]\nNormais:\n[ ";
		for (Pessoa pessoa : this.cdNormal) {
			if (pessoa == null) {
				aux += "= ";
			} else {
				aux += pessoa.nome + ":" + pessoa.idade + " ";
			}
		}
		return aux + "]";
	}
	public static void main(String[] args) {
		Topic topic = new Topic(6, 2);
		topic.addPessoa("geovani", 19);
		topic.addPessoa("jesk", 20);
		topic.addPessoa("zara", 74);
		topic.addPessoa("priscila", 19);
		topic.addPessoa("chico", 18);
		topic.addPessoa("joão", 19);
		topic.addPessoa("lucas", 3);
		System.out.println(topic);
	}
}
