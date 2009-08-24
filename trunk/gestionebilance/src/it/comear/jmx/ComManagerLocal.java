package it.comear.jmx;

public interface ComManagerLocal {

	public void startSystem();

	public void stopSystem();

	public String getStatusPorte();

	public String getStatusPorta(String porta);

	public String leggiDispositivo(String porta);

}
