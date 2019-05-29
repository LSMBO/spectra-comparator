package fr.lsmbo.msda.spectra.comp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Initialize spectra-comparator configurations
 * 
 * @author Aromdhani
 *
 */

public class Config {
	public static String applicationFile = "application.conf";
	public static String spectraCompFile = "spectra-comp.properties";
	public static Properties properties = null;

	/**
	 * Load application.conf parameters
	 */
	private synchronized static void loadApplicationConf() {

	}

	/**
	 * Load spectra-comp parameters
	 */
	private synchronized static void loadSpectraCompProps() {
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(spectraCompFile)) {
			if (input == null) {
				System.err.println(
						"Error - Spectra-comp properties file: '" + spectraCompFile + "' does not exist!");
			} else {
				Properties recoverProperties = new Properties();
				recoverProperties.load(input);
				Session.SPECTRACOMP_RELEASE_NAME = recoverProperties.getProperty("name");
				Session.SPECTRACOMP_RELEASE_DESCRIPTION = recoverProperties.getProperty("description");
				Session.SPECTRACOMP_RELEASE_VERSION = recoverProperties.getProperty("version");
				Session.SPECTRACOMP_RELEASE_DATE = recoverProperties.getProperty("build-date");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void initialize() {

	}
}