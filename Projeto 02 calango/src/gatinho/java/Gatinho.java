package gatinho.java;

import java.util.Scanner;

public class Gatinho {
		// TODO Auto-generated method stub
		String nome;
		int energia;
	    int agilidade;
	    int felicidade;
	    
	    Gatinho(String nome, int energia, int agilidade, int felicidade){
	        this.nome = nome;
	        this.energia=energia;
	        this.agilidade=agilidade;
	        this.felicidade=felicidade;
	    }
	    void caçar() {
	    	if(energia>2 && agilidade>2) {
	    		 System.out.println("Caçando um calango");
	             this.energia-=1;
	             this.agilidade-=1;
	             return;
	    		}
	    	System.out.println("Vou deitar pra um cochilo");
	    }
	    
        void comer(){
	        if(energia<2 && felicidade<2){
	            System.out.println("absurdo! vou ter que comer a ração de ontem");
	            this.energia+=2;
	            this.felicidade+=1;
	            return;
	        }
	        System.out.println("eca! meu dono ainda não encheu meu potinho com ração nova");
	    }
	    
	    void brincar(){
	        
	        if(energia>1 && agilidade>1){
	            
	            System.out.println("Seguindo a bolinha de brinquedo");
	            this.energia-=1;
	            this.agilidade-=1;
	            this.felicidade+=1;
	            return;
	        }      
	        System.out.println("esnobando meu dono me chamando p/ brincar");
	    }
	    
        void arranhar(){
	        if(energia>1 && agilidade>1){
	            System.out.println("Hora de destruir a casa!");
	            this.energia-=1;
	            this.agilidade-=1;
	            this.felicidade+=1;
	            return;
	        }
	        System.out.println("Não tô a fim de estressar meu dono hoje");
        }
        
        public String toString() {
            return "Gatinho: " + nome + "\nenergia:" + energia + "\nagilidade:" + agilidade + " \nfelicidade:" + felicidade;
        }
        public static void main (String[] args){
            Scanner scanner  = new Scanner(System.in);
            Gatinho gatinho = new Gatinho("bob",0,0,0);
           while(true) {
                String line = scanner.nextLine();
                String[] ui = line.split(" ");
                if(ui[0].equals("domesticar")) {
                	gatinho = new Gatinho(ui[1], Integer.parseInt(ui[2]), Integer.parseInt(ui[3]), Integer.parseInt(ui[4]));
                } else if(ui[0].equals("caçar")) {
                	gatinho.caçar();
                } else if (ui[0].equals("comer")) {
                	gatinho.comer();
                } else if (ui[0].equals("brincar")) {
                	gatinho.brincar();
                } else if (ui[0].equals("arranhar")) {
                	gatinho.arranhar();
                } else if (ui[0].equals("stop")) {
                	break;
                } else if (ui[0].equals("gatinho")){
                		System.out.println(gatinho);
           		} else {
                	System.out.println("fail: comando invalido");
                }
            }
           scanner.close();
        }
} 
