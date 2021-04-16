package matricula.java;

import java.util.Map;
import java.util.TreeMap;

class Aluno{
	String nome;
	Map<String, Disciplina> disciplinas;
	Aluno (String nome){
		this.nome = nome;
		this.disciplinas = new TreeMap<>();
	}
	Disciplina getDisciplina(String disciplina) {
		if(this.disciplinas.get(disciplina) == null) {
			throw new RuntimeException("erro: disciplina não encontrada");
		}
		return this.disciplinas.get(disciplina);
	}
	boolean verificarDisciplina(String disciplina) {
			if(this.disciplinas.get(disciplina) != null) {
				return true;
			}
			return false;
	}
	void addDisciplina(Aluno aluno, Disciplina disciplina){
		if(verificarDisciplina(disciplina.nome)) {
			throw new RuntimeException("erro: aluno ja matriculado");
		}
		disciplina.alunos.put(aluno.nome, aluno);
		this.disciplinas.put(disciplina.nome, disciplina);
	}
	void removeDisciplina(Disciplina disciplina) {
		if(!verificarDisciplina(disciplina.nome)) {
			throw new RuntimeException("erro: aluno não matriculado");
		}
		disciplina.alunos.remove(this.nome);
		this.disciplinas.remove(disciplina.nome);
	}
	public String toString() {
		String aux = this.nome + "[ ";
		for(Map.Entry<String, Disciplina> entry : this.disciplinas.entrySet()) {
			aux += entry.getKey() + " ";
		}
		aux += "]";
		return aux;
	}
}
class Disciplina {
	String nome;
	Map<String, Aluno> alunos;
	Disciplina (String nome){
		this.nome = nome;
		this.alunos = new TreeMap<>();
	}
	public String toString() {
		String aux = this.nome + "[ ";
		for(Map.Entry<String, Aluno> entry : this.alunos.entrySet()) {
			aux += entry.getKey() + " ";
		}
		aux += "]";
		return aux;
	}
}
public class Matricula {
	Map<String, Aluno> alunos;
	Map<String, Disciplina> disciplinas;
	Matricula(){
		this.alunos = new TreeMap<>();
		this.disciplinas = new TreeMap<>();
	}
	Disciplina getDisciplina(String disciplina) {
		if(this.disciplinas.get(disciplina) == null) {
			throw new RuntimeException("erro: disciplina não cadastrado");
		}
		return this.disciplinas.get(disciplina);
	}
	Aluno getAluno(String aluno) {
		if(this.alunos.get(aluno) == null) {
			throw new RuntimeException("erro: aluno não cadastrado");
		}
		return this.alunos.get(aluno);
	}
	boolean verificarDisciplina(String disciplina) {
		if(this.disciplinas.get(disciplina) != null) {
			return true;
		}
		return false;
	}
	boolean verificarAluno(String aluno) {
		if(this.alunos.get(aluno) != null) {
			return true;
		}
		return false;
	}
	void novoAluno(String nome) {
		if(verificarAluno(nome)){
			throw new RuntimeException("erro: aluno ja adicionado ao sistema");
		}
		this.alunos.put(nome, new Aluno(nome));
	}
	void novaDisciplina(String nome) {
		if(verificarDisciplina(nome)){
			throw new RuntimeException("erro: disciplina ja adicionada ao sistema");
		}
		this.disciplinas.put(nome, new Disciplina(nome));
	}
	void addAlunoDisciplina(String nomeAluno, String nomeDisciplina) {
		if(!verificarDisciplina(nomeDisciplina)){
			throw new RuntimeException("erro: disciplina não encontrada no sistema");
		}
		if(!verificarAluno(nomeAluno)){
			throw new RuntimeException("erro: aluno não encontrado no sistema");
		}
		Aluno aluno = getAluno(nomeAluno);
		Disciplina disciplina = getDisciplina(nomeDisciplina);
		aluno.addDisciplina(aluno, disciplina);
	}
	void rmAlunoDisciplina(String nomeAluno, String nomeDisciplina) {
		if(!verificarDisciplina(nomeDisciplina)){
			throw new RuntimeException("erro: disciplina não encontrada no sistema");
		}
		if(!verificarAluno(nomeAluno)){
			throw new RuntimeException("erro: aluno não encontrado no sistema");
		}
		Aluno aluno = getAluno(nomeAluno);
		Disciplina disciplina = getDisciplina(nomeDisciplina);
		aluno.removeDisciplina(disciplina);
	}
	void rmAlunoSistema(String nome) {
		if(!verificarAluno(nome)){
			throw new RuntimeException("erro: aluno não encontrado no sistema");
		}
		Aluno aluno = getAluno(nome);
		for(Map.Entry<String, Disciplina> entry : aluno.disciplinas.entrySet()) {
			entry.getValue().alunos.remove(nome);
		}
		this.alunos.remove(nome);
	}
	void rmDisciplinaSistema(String nome) {
		if(!verificarDisciplina(nome)){
			throw new RuntimeException("erro: disciplina não encontrado no sistema");
		}
		Disciplina disciplina = getDisciplina(nome);
		for(Map.Entry<String, Aluno> entry : disciplina.alunos.entrySet()) {
			entry.getValue().disciplinas.remove(nome);
		}
		this.disciplinas.remove(nome);
	}
	public String toString() {
		String aux = "alunos:\n";
		for(Map.Entry<String, Aluno> entry : this.alunos.entrySet()) {
			aux += entry.getValue() + "\n";
		}
		aux += "disciplinas:\n";
		for(Map.Entry<String, Disciplina> entry : this.disciplinas.entrySet()) {
			aux += entry.getValue() + "\n";
		}
		return aux;
	}
	public static void main (String[] args){
		Matricula matricula = new Matricula();
		matricula.novoAluno("wermyn");
		matricula.novoAluno("jesk");
		matricula.novaDisciplina("POO");
		matricula.novaDisciplina("MT");
		matricula.addAlunoDisciplina("wermyn", "POO");
		matricula.addAlunoDisciplina("wermyn", "MT");
		matricula.addAlunoDisciplina("jesk", "POO");
		System.out.println(matricula);
		matricula.rmAlunoDisciplina("jesk", "POO");
		//matricula.rmAlunoSistema("wermyn");
		matricula.rmDisciplinaSistema("POO");
		System.out.println(matricula);
	}
}
