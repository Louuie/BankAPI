package bank.bank.Config;

/**
 * Class that gets the database information from the plugin config
 */
public class ConfigGetter {
    private String username;
    private String password;
    private String ip;
    private String url;
    private String table;

    /**
     * Returns the username of the database
     * @return
     */
    public String getUsername(){
        return this.username = DatabaseConfig.get().getString("Database Config" + "." + "Username");
    }

    /**
     * Returns the password of the database
     * @return
     */
    public String getPassword(){
        return this.password = DatabaseConfig.get().getString("Database Config" + "." + "Password");
    }

    /**
     * Returns the IP Address of the database
     * @return
     */
    public String getIp(){
        return this.ip = DatabaseConfig.get().getString("Database Config" + "." + "IP");
    }

    /**
     * Returns the URL of the database
     * @return
     */
    public String getUrl(){
        return this.url = "jdbc:mysql://" + this.getIp() + "?allowPublicKeyRetrieval=true&useSSL=false";
    }

    /**
     * Returns the Table of the database
     * @return
     */
    public String getTable(){
        return this.table = DatabaseConfig.get().getString("Database Config" + "." + "Table");
    }

}
