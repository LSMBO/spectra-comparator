package fr.lsmbo.msda.spectra.comp;

import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import fr.lsmbo.msda.spectra.comp.db.DBAccess;
import fr.lsmbo.msda.spectra.comp.db.DBConfig;
import fr.lsmbo.msda.spectra.comp.db.DBSpectraHandler;
import fr.lsmbo.msda.spectra.comp.settings.UserParams;
import fr.lsmbo.msda.spectra.comp.settings.Version;

/**
 * Initialize spectra-comparator configurations
 * 
 * @author Aromdhani
 *
 */

public class Config {

	public static File spectraCompFile = new File(
			Main.class.getClassLoader().getResource("spectra-comp.json").getPath());
	public static File DefaultParamsFile = new File(
			Main.class.getClassLoader().getResource("default-params.json").getPath());
	public static Properties properties = null;

	/**
	 * Return object value. Example: Config.get("max.file.size")
	 * 
	 * @param the
	 *            specified key to retrieve the property value as an object.
	 */
	private static Object _get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Return a property value as String
	 * 
	 * @param key
	 *            the specified key to get the property value
	 */
	public static String get(String key) {
		Object value = _get(key);
		if (value == null)
			return null;
		return value.toString();
	}

	public static void initialize() {
		// Load spectra-comp version
		loadSpectraCompProps(spectraCompFile);
		// Load Spectra-comp default parameters
		loadUserParams(DefaultParamsFile);
		// Load database connection properties
		DBConfig.getInstance();
		// Test Database connection
		DBAccess.createUdsDBConnection();
		DBAccess.createMsiDBConnection("msi_db_project_1");

	}

	/**
	 * Load spectra-comp version properties
	 * 
	 * @param versionFile
	 *            the file used to load spectra-comp version
	 */
	private synchronized static void loadSpectraCompProps(File versionFile) {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(versionFile));
			Session.SPECTRACOMP_VERSION = gson.fromJson(reader, Version.class);
			System.out.println(Session.SPECTRACOMP_VERSION.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Load user parameters. Read and parse parameters file.
	 * 
	 * @param paramsFile
	 *            the file used to load user parameters
	 */
	public static void loadUserParams(File paramsFile) {
		try {
			Gson gson = new Gson();
			JsonReader reader = new JsonReader(new FileReader(paramsFile));
			Session.userParams = gson.fromJson(reader, UserParams.class);
			System.out.println(Session.userParams.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
