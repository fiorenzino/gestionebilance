package it.comear.jmx;

public interface ComManager {

	void create();

	void start();

	void stop();

	void destroy();

	public void startSystem();

	public void stopSystem();

	public String leggiDispositivo(String nome);

	public String getStatusPorta(String porta);

	public String getStatusPorte();
}
