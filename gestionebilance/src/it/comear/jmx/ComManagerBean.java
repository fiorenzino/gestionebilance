package it.comear.jmx;

import it.comear.jmx.threads.ComReaderThread;
import it.comear.jmx.utils.ComUtils;
import it.comear.jmx.utils.SerialParameters;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.ejb.Local;

import org.jboss.annotation.ejb.Management;
import org.jboss.annotation.ejb.Service;
import org.jboss.logging.Logger;

@Service(objectName = "ComManager:service=ComManagerBean")
@Local(ComManagerLocal.class)
@Management(ComManager.class)
public class ComManagerBean implements ComManager, ComManagerLocal {

	Logger log = Logger.getLogger("it.comear.jmx");
	private Map<String, Boolean> porteCom;

	private Map<String, ComReaderThread> porteDispositivi;

	public void create() {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void start() {
		startSystem();

	}

	public void stop() {
		stopSystem();

	}

	public String getStatusPorta(String porta) {
		if (getPorteCom().keySet().contains(porta)) {
			return getPorteCom().get(porta).toString();
		}
		return "ERROR";
	}

	public String getStatusPorte() {
		StringBuffer str = new StringBuffer();
		for (String com : getPorteCom().keySet()) {
			str.append(com + ": " + getPorteCom().get(com));
		}
		return str.toString();
	}

	public void initPorteComAttive() {
		log.info("initPorteComAttive");
		if (getPorteCom().keySet().contains("COM1")) {
			log.info("COM1: SET TRUE ");
			getPorteCom().put("COM1", Boolean.TRUE);
		}
	}

	public void accendiDispositivi() {
		log.info("accendiDispositivi");
		CommPortIdentifier portId;
		SerialParameters parameters;
		SerialPort serialPort = null;
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier
				.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = portList.nextElement();
			log.info("PORT: " + portId.getName());
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (getPorteCom().keySet().contains(portId.getName())) {
					if (getPorteCom().get(portId.getName())) {
						log.info("ACCESO: " + portId.getName());
						try {
							serialPort = (SerialPort) portId.open(portId
									.getName(), 2000);
							ComReaderThread comp = new ComReaderThread(
									serialPort, true);
							getPorteDispositivi().put(portId.getName(), comp);
						} catch (PortInUseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						log.info("SPENTO: " + portId.getName());
					}
				}
			}
		}
	}

	public void spegniDispositivi() {
		log.info("spegniDispositivi: ");
		for (String disp : getPorteDispositivi().keySet()) {
			log.info("spegniDispositivi: " + disp);
			ComReaderThread dispLoc = getPorteDispositivi().get(disp);
			dispLoc.stop();
			dispLoc = null;
			log.info("spegniDispositivi: " + disp);
		}
	}

	public void discoveryPorteCom() {
		porteCom = new HashMap<String, Boolean>();
		SerialParameters parameters;
		SerialPort serialPort = null;
		InputStream inputStream;
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier
				.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = portList.nextElement();
			log.info("discoveryPorteCom PORT: " + portId.getName());
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				getPorteCom().put(portId.getName(), Boolean.FALSE);
			}
		}
	}

	public void startSystem() {
		discoveryPorteCom();
		initPorteComAttive();
		accendiDispositivi();
	}

	public void stopSystem() {
		spegniDispositivi();

	}

	public String leggiDispositivo(String nome) {
		String peso = null;
		try {
			if (getPorteDispositivi().keySet().contains(nome)) {
				ComReaderThread local = getPorteDispositivi().get(nome);
				if (local.getPila().size() > 1)
					peso = local.getPila().pop();
				log.info(")POP: " + ComUtils.getPeso(peso) + " (ric: " + peso
						+ ")");
				return ComUtils.getPeso(peso);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}

	public Map<String, ComReaderThread> getPorteDispositivi() {
		if (porteDispositivi == null)
			porteDispositivi = new HashMap<String, ComReaderThread>();
		return porteDispositivi;
	}

	public void setPorteDispositivi(
			Map<String, ComReaderThread> porteDispositivi) {
		this.porteDispositivi = porteDispositivi;
	}

	public Map<String, Boolean> getPorteCom() {
		if (porteCom == null) {
			porteCom = new HashMap<String, Boolean>();
		}
		return porteCom;
	}

	public void setPorteCom(Map<String, Boolean> porteCom) {
		this.porteCom = porteCom;
	}
}
