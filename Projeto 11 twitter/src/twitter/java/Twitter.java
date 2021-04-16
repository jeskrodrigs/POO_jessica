package twitter.java;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
class Tweet {
	String id;
	String nome;
	String tweet;
	ArrayList<User> amei;
	Tweet(String id, String nome, String tweet){
		this.id = id;
		this.nome = nome;
		this.tweet = tweet;
		this.amei = new ArrayList<>();
	}boolean verificaramei (String nome) {
		for(User user : amei) {
			if(user.nome.equals(nome)) {
				return true;
			}
		}
		return false;
	}
	void daramei(User user) {
		if(verificaramei(user.nome)) {
			throw new RuntimeException("erro: " + user.nome +" ja deu amei");
		}
		this.amei.add(user);
	}
	public String toString() {
		String aux = this.id + ":" + this.nome + " (" + this.tweet + ") [ ";
		for (User user : this.amei) {
			aux += user.nome + " ";
		}
		aux += "]";
		return aux;
	}
}
class User {
	String nome;
	Map<String, User> seguidores;
	Map<String, User> seguidos;
	Map<String, Tweet> timeline;
	Map<String, Tweet> meustweets;
	Map<String, Tweet> todosOsTweets;
	
	User(String nome){
		this.nome = nome;
		this.seguidores = new TreeMap<>();
		this.seguidos = new TreeMap<>();
		this.timeline = new TreeMap<>();
		this.meustweets = new TreeMap<>();
		this.todosOsTweets = new TreeMap<>();
	}
	User getuser (String nome, Map<String, User> map) {
		return map.get(nome);
	}
	boolean verificaruser (String nome, Map<String, User> map) {
		if (map.get(nome) != null) {
			return true;
		}
		return false;
	}
	void seguir (User userSeguir, User user){
		if(verificaruser(userSeguir.nome, this.seguidos)) {
			throw new RuntimeException("erro: ja segue o usuario");
		}
		this.seguidos.put(userSeguir.nome, userSeguir);
		userSeguir.seguidores.put(this.nome, user);
	}
	void deixarDeSeguir(User userDeixarDeSeguir){
		if(!verificaruser(userDeixarDeSeguir.nome, this.seguidos)) {
			throw new RuntimeException("erro: você não segue o usuario");
		}
		this.seguidos.remove(userDeixarDeSeguir.nome);
		userDeixarDeSeguir.seguidores.remove(this.nome);
	}
	void fazerTweet (Tweet tweet) {
		this.meustweets.put(tweet.id, tweet);
		this.timeline.put(tweet.id, tweet);
		this.todosOsTweets.put(tweet.id, tweet);
		for(Map.Entry<String, User> entry : seguidores.entrySet()) {
			entry.getValue().timeline.put(tweet.id, tweet);
			entry.getValue().todosOsTweets.put(tweet.id, tweet);
		}
	}
	void daramei(User user, Tweet tweet) {
		tweet.daramei(user);
	}
	void limpartimeline() {
		this.timeline.clear();
	}
	void vertimeline(){
		String aux = "";
		for(Map.Entry<String, Tweet> entry : this.timeline.entrySet()) {
			aux += entry.getValue() + "\n";
		}
		limpartimeline();
		System.out.println(aux);
	}
	void vermeustweets(){
		String aux = "";
		for(Map.Entry<String, Tweet> entry : this.meustweets.entrySet()) {
			aux += entry.getValue() + "\n";
		}
		limpartimeline();
		System.out.println(aux);
	}
	void vertodosOsTweets(){
		String aux = "";
		for(Map.Entry<String, Tweet> entry : this.todosOsTweets.entrySet()) {
			aux += entry.getValue() + "\n";
		}
		limpartimeline();
		System.out.println(aux);
	}
}
public class Twitter {
	Map<String, User> usuarios;
	Map<String, Tweet> tweets;
	String id;
	Twitter(){
		this.usuarios = new TreeMap<>();
		this.tweets = new TreeMap<>();
		this.id = "0";
	}
	User getuser (String nome) {
		return this.usuarios.get(nome);
	}
	Tweet gettweet (String id) {
		return this.tweets.get(id);
	}
	boolean verificartweet (String id){
		if (this.tweets.get(id) != null) {
			return true;
		}
		return false;
	}
	boolean verificaruser (String nome){
		if (this.usuarios.get(nome) != null) {
			return true;
		}
		return false;
	}
	void addusuario (String nome) {
		if (verificaruser(nome)) {
			throw new RuntimeException("erro: usuario já exite no sistema");
		}
		this.usuarios.put(nome, new User(nome));
	}
	void seguiruser (String nomeuser, String nomeseguir) {
		if(!verificaruser(nomeuser) || !verificaruser(nomeseguir) ) {
			throw new RuntimeException("erro: usuário já é seguido por você");
		}
		User user = getuser(nomeuser);
		User seguir = getuser(nomeseguir);
		user.seguir(seguir, user);
	}
	void proxId() {
		int num = Integer.parseInt(this.id);
		num += 1;
		this.id = "" + num;
	}
	void fazerTweet (String nome, String msg) {
		if(!verificaruser(nome)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		User user = getuser(nome);
		Tweet tweet = new Tweet(this.id, nome, msg);
		proxId();
		this.tweets.put(tweet.id, tweet);
		user.fazerTweet(tweet);
	}
	void darAmei (String nome, String id) {
		if(!verificaruser(nome)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		if(!verificartweet(id)) {
			throw new RuntimeException("erro: tweet não encontrado");
		}
		User user = getuser(nome);
		Tweet tweet = gettweet(id);
		tweet.daramei(user);
	}
	void vertimeline(String nome) {
		if(!verificaruser(nome)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		User user = getuser(nome);
		user.vertimeline();
	}
	void vermeustweets(String nome) {
		if(!verificaruser(nome)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		User user = getuser(nome);
		user.vermeustweets();
	}
	void vertodosOsTweets(String nome) {
		if(!verificaruser(nome)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		User user = getuser(nome);
		user.vertodosOsTweets();
	}
	void deixarDeSeguir(String user, String nome) {
		if(!verificaruser(user)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		if(!verificaruser(nome)) {
			throw new RuntimeException("erro: usuário não encontrado");
		}
		User usuario = getuser(user);
		User deixarDeSeguir = getuser(nome);
		usuario.deixarDeSeguir(deixarDeSeguir);
	}
	public String toString() {
		String aux = "";
		for(Map.Entry<String, User> entry : usuarios.entrySet()) {
			aux += entry.getKey() + "\nSeguidores:\n[ ";
			for(Map.Entry<String, User> entrada : entry.getValue().seguidores.entrySet()) {
				aux += entrada.getKey() + " ";
			}
			aux += " ]\nSeguidos:\n[ ";
			for(Map.Entry<String, User> entrada01 : entry.getValue().seguidos.entrySet()) {
				aux += entrada01.getKey() + " ";
			}
			aux += " ]\n";
		}
		return aux;
	}
	public static void main (String[] args) {
		Twitter sistema = new Twitter();
		sistema.addusuario("wermyn");
		sistema.addusuario("jesk");
		sistema.addusuario("huguin");
		sistema.seguiruser("jesk", "wermyn");
		sistema.seguiruser("wermyn", "jesk");
		sistema.seguiruser("wermyn", "huguin");
		sistema.fazerTweet("wermyn", "vou buscar água");
		sistema.fazerTweet("wermyn", "tava geladinha");
		sistema.fazerTweet("jesk", "quero passar em POO");
		sistema.darAmei("jesk", "0"); //osh?
		sistema.darAmei("huguin", "0");
		//sistema.darAmei("huguin", "10");
		//sistema.vertimeline("jesk");
		sistema.deixarDeSeguir("wermyn", "huguin");
		sistema.vertodosOsTweets("wermyn");
		System.out.println(sistema);
	}
}
