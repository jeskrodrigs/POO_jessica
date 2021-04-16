package agenda02.java;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

class Telefone {
	String label;
	String numero;
	Telefone (String label, String numero){
		this.label = label;
		this.numero = numero;
	}
	public String toString(){
	    return this.label + ":" + this.numero;
	}
}
class Contato {
	String nome;
	ArrayList<Telefone> telefones;
	Contato(String nome){
	    this.nome = nome;
	    this.telefones = new ArrayList<>();
}
boolean validar(String numero){
	boolean result = false;
    String nvalidos = "0123456789().";
    for(int i = 0; i < numero.length(); i++) {
    	int charNumber  = numero.charAt(i);
    	int contador = 0;
    	for(int y = 0; y < nvalidos.length(); y++) {
    		if(charNumber != nvalidos.charAt(y)) {
    			contador += 1;
    		}
    	}
    	if(contador < nvalidos.length()) {
    		result = true;
    	} else {
    		result = false;
    		break;
    	}
    }
	return result;
}
void addTelefone(String label, String numero){
	if (validar(numero) == false) {
		System.out.println("número inválido");
		return;
	}
	this.telefones.add(new Telefone(label, numero));
}
void remover (String id) {
	int index = Integer.parseInt(id);
	if (index >= this.telefones.size() || index < 0) {
		System.out.println("erro");
		return;
	}
	this.telefones.remove(index);
}
public String toString (){
	String print = "" ;
		for(int i = 0; this.telefones.size() > i; i++) {
			print += "\n[" + i + ":" + this.telefones.get(i) + "]";
		}
	return print;
	}
}
public class Agenda {
	Map<String, Contato> contatos;
	Agenda (){
		this.contatos = new TreeMap<>();
	}
	boolean verificarContato(String nome) {
		if(contatos.get(nome) != null){
			return true;
		}
		return false;
	}
	Contato getContato(String nome) {
		return this.contatos.get(nome);
	}
	void criarContato(String nome) {
		if(verificarContato(nome)) {
			System.out.println("erro: o contato ja existe");
			return;
		}
		this.contatos.put(nome, new Contato(nome));
	}
	void removerContato(String nome) {
		if(!verificarContato(nome)) {
			System.out.println("erro: contato não encontrado");
			return;
		}
		this.contatos.remove(nome);
	}
	void adicionarNumero(String nome, String label, String numero) {
		if(!verificarContato(nome)) {
			System.out.println("erro: contato não encontrado");
			return;
		}
		Contato contato = getContato(nome);
		contato.addTelefone(label, numero);
	}
	void removerNumero(String nome, String id) {
		if(!verificarContato(nome)) {
			System.out.println("erro: contato não encontrado");
			return;
		}
		Contato contato = getContato(nome);
		contato.remover(id);
	}
	void busca (String busca) {
		String result = "";
		ArrayList<Contato> achados = new ArrayList<>();
		for(Map.Entry<String, Contato> entry : this.contatos.entrySet()) {
			if(entry.getValue().nome.contains(busca)) {
				achados.add(entry.getValue());
			} else {
				for(Telefone telefone : entry.getValue().telefones) {
					if(telefone.label.contains(busca)) {
						achados.add(entry.getValue());
					} else if(telefone.numero.contains(busca)) {
								achados.add(entry.getValue());
							}
				}
			}
		}
		for (Contato contato : achados) {
			result += "- " + contato.nome + "\n" + contato.telefones;
		}
		System.out.println(result);
	}
	public String toString() {
		String aux = "";
		for(Map.Entry<String, Contato> entry : this.contatos.entrySet()) {
			aux += "- " + entry.getKey() + entry.getValue() + "\n";
		}
		
		return aux;
	}
	public static void main(String[] args) {
	        //Scanner scanner = new Scanner(System.in);
	        //Contato contato = new Contato("Mamãe <3");
	        //while(true){
		 	Agenda agenda = new Agenda();
		 	agenda.criarContato("wermyn");
		 	agenda.adicionarNumero("wermyn", "tim", "(88)998743387");// não dá p adc numero ou eu tô colocando errado?
		 	agenda.adicionarNumero("wermyn", "vivo", "3874387");
		 	agenda.criarContato("priscila");
		 	agenda.adicionarNumero("priscila", "tim", "(88)93243387");
		 	//System.out.println(agenda);
		 	System.out.println(agenda);
		 	agenda.busca("w");
	}
}
