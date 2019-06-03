package fr.lsmbo.msda.spectra.comp.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import fr.lsmbo.msda.spectra.comp.Main;

/**
 * Handle database connection for spectra-comp
 * 
 * @author Aromdhani
 *
 */
public class DBConfig {
	private static final Object CONFIGURATION_LOCK = new Object();
	private static String application = "application.conf";
	private static DBConfig instance;
	private Integer maxPoolConnection = null;
	private String user = null;
	private String password = null;
	private Integer port = null;
	private String host = null;
	private String dbName = null;
	DriverType driverType = null;
	private String dataSetOwner = null;
	private Properties connectionProperties = null;

	private DBConfig() {
		loadProperties();
	}

	/**
	 * 
	 * @return an instance of DBConfig
	 */
	public static DBConfig getInstance() {
		if (instance == null) {
			instance = new DBConfig();
		}
		return instance;
	}

	/**
	 * Load application.conf properties
	 */
	private void loadProperties() {
		try (InputStream input = Main.class.getClassLoader().getResourceAsStream(application)) {
			if (input == null) {
				System.err.println("Error - error while trying to read spectra-comp file: '" + application + "'!");
			} else {
				connectionProperties = new Properties();
				connectionProperties.load(input);
				maxPoolConnection = Integer.valueOf(connectionProperties.getProperty("db-config.max-pool-connection"));
				user = connectionProperties.getProperty("auth-config.user");
				password = connectionProperties.getProperty("auth-config.password");
				host = connectionProperties.getProperty("host-config.host");
				port = Integer.valueOf(connectionProperties.getProperty("host-config.port"));
				dbName = connectionProperties.getProperty("db-name");
				dataSetOwner = connectionProperties.getProperty("ds-owner");
				if (connectionProperties.getProperty("db-config.driver-type").equals("postgresql")) {
					driverType = DriverType.POSTGRESQL;
				} else if (connectionProperties.getProperty("db-config.driver-type").equals("sqlite")) {
					driverType = DriverType.SQLITE;
				} else {
					driverType = DriverType.H2;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void forcePropertiesFileReload() {
		synchronized (CONFIGURATION_LOCK) {
			if (instance != null) {
				instance = null;
			}
		}
	}

	/**
	 * @return the maxPoolConnection
	 */
	public Integer getMaxPoolConnection() {
		return maxPoolConnection;
	}

	/**
	 * @param maxPoolConnection
	 *            the maxPoolConnection to set
	 */
	public void setMaxPoolConnection(Integer maxPoolConnection) {
		this.maxPoolConnection = maxPoolConnection;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName
	 *            the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the driverType
	 */
	public final DriverType getDriverType() {
		return driverType;
	}

	/**
	 * @param driverType
	 *            the driverType to set
	 */
	public final void setDriverType(DriverType driverType) {
		this.driverType = driverType;
	}

	/**
	 * @return the dataSetOwner
	 */
	public final String getDataSetOwner() {
		return dataSetOwner;
	}

	/**
	 * @param dataSetOwner
	 *            the dataSetOwner to set
	 */
	public final void setDataSetOwner(String dataSetOwner) {
		this.dataSetOwner = dataSetOwner;
	}

}
