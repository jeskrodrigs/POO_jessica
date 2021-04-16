package pintinho.java;
import java.util.Scanner;

class Jogo {
	int energia;
	int saciedade;
	int limpeza;
	int moedas;
	int idade;
	Jogo(){
		this.energia = 0;
		this.saciedade = 0;
		this.limpeza = 0;
		this.moedas = 0;
		this.idade = 0;
	}
	void seguirAfamilia(){
		this.energia -= 2;
		this.saciedade -= 2;
		this.limpeza -= 2;
		this.moedas = 5;
		this.idade = 2;
	}
	void ciscar () {
		this.energia -= 4;
		this.saciedade -= 1;
		this.limpeza -= 3;
		this.moedas = 8;
		this.idade = 4;
	}
	void limpar () {
		this.energia = 0;
		this.saciedade = 0;
		this.limpeza = 0;
		this.moedas = 0;
		this.idade = 0;
	}
}

public class Pintinho {
	int energia;
	int saciedade;
	int limpeza;
	int energiamax;
	int saciedademax;
	int limpezamax;
	int moedas;
	int idade;
	boolean vivo;
	Jogo jogos;
	
	
	Pintinho(int energia, int saciedade, int limpeza){
		this.energia = energia;
		this.saciedade = saciedade;
		this.limpeza = limpeza;
		this.energiamax = energia;
		this.saciedademax = saciedade;
		this.limpezamax = limpeza;
		this.moedas = 0;
		this.idade = 0;
		this.vivo = true;
		this.jogos = new Jogo();
	}
	void setEnergia(int valor) {
		this.energia += valor;
		if (this.energia <= 0) {
			this.vivo = false;
			this.energia = 0;
			System.out.println("Infelizmente o pintinho faleceu");
		}
		if (this.energia > this.energiamax) {
			this.energia = this.energiamax;
			System.out.println("O pintinho já tem energia suficiente");
		}
	}
	void setSaciedade(int valor){
		this.saciedade += valor;
		if (this.saciedade <= 0) {
			this.vivo = false;
			this.saciedade = 0;
			throw new RuntimeException("O pintinho está morto!!!");
		}
		if (this.saciedade > this.saciedademax) {
			this.saciedade = this.saciedademax;
			System.out.println("O pintinho já está satisfeito!");
		}
	}
	void setLimpeza(int valor){
		this.limpeza += valor;
		if (this.limpeza <= 0) {
			this.vivo = false;
			this.limpeza = 0;
			System.out.println("O pintinho morreu de sujeira");
		}
		if (this.limpeza > this.limpezamax) {
			this.limpeza = this.limpezamax;
			System.out.println("O pintinho está muito cheiroso!");
		}
	}
	void comer() {
		if(this.vivo == false) {
			System.out.println("O pintinho está morto!");
			return;
		}
		setEnergia(2);
		setSaciedade(2);
		setLimpeza(-2);
		this.idade += 2;
		this.moedas += 6;
		System.out.println("O pintinho acabou de almoçar");
	}

	void banho() {
		if(this.vivo == false) {
			System.out.println("O pintinho está morto!");
			return;
		}
		setEnergia(1);
		setLimpeza(this.limpezamax);
		this.idade += 2;
		this.moedas += 5;
		System.out.println("O pintinho tomou banho");
	}
	void dormir() {
		if(this.vivo == false) {
			System.out.println("O pintinho está morto!");
			return;
		}
		setEnergia(this.energiamax);
		setSaciedade(-4);
		this.idade += 4;
		this.moedas += 8;
		System.out.println("A mimir");
	}
	void jogar(String jogo) {
		if(this.vivo == false) {
			System.out.println("O pintinho está morto!");
			return;
		}
		if(jogo.equals("ciscar")) {
			this.jogos.ciscar();
			setEnergia(jogos.energia);
			setSaciedade(jogos.saciedade);
			setLimpeza(jogos.limpeza);
			this.moedas += jogos.moedas;
			this.idade += jogos.idade;
			jogos.limpar();
		}
		if(jogo.equals("seguirAfamilia")) {
			this.jogos.seguirAfamilia();
			setEnergia(jogos.energia);
			setSaciedade(jogos.saciedade);
			setLimpeza(jogos.limpeza);
			this.moedas += jogos.moedas;
			this.idade += jogos.idade;
			jogos.limpar();
		}
		System.out.println("Começou o jogo!");
	}
	public String toString(){
		return "Pintinho:" + "\nenergia: " + energia + "\nsaciedade: " + saciedade + "\nlimpeza: " + limpeza + "\nmoedas: " + moedas + "\nidade: " + idade;
	}
	public static void main (String[] args){
		Scanner scanner  = new Scanner(System.in);
		Pintinho pintinho = new Pintinho(0,0,0);
        while(true) {
        	try {
            String line = scanner.nextLine();
            String[] ui = line.split(" ");
            if(ui[0].equals("chocar")) {
            	pintinho = new Pintinho(Integer.parseInt(ui[1]),Integer.parseInt(ui[2]), Integer.parseInt(ui[3]));
            } else if (ui[0].equals("comer")) {
            	pintinho.comer();
            } else if (ui[0].equals("banho")) {
            	pintinho.banho();
            } else if (ui[0].equals("dormir")) {
            	pintinho.dormir();
            } else if (ui[0].equals("jogar")) {
            	pintinho.jogar(ui[1]);
            } else if(ui[0].equals("parar")){
            	break;
            } else if (ui[0].equals("ver")){
            	System.out.println(pintinho);
            } else {
            	System.out.println("fail: comando invalido");
            }
        } catch (RuntimeException e){
        	System.out.println(e.getLocalizedMessage());
        }
        }
        scanner.close();
	}
}