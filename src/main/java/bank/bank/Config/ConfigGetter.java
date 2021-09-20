package bank.bank.Config;

public class ConfigGetter {
    private String username;
    private String password;
    private String ip;
    private String table;
    private String UUIDColumnName;
    private String BalanceColumnName;
    private String url;

    public String getUsername(){
        return this.username = DatabaseConfig.get().getString("Database Config" + "." + "Username");
    }
    public String getPassword(){
        return this.password = DatabaseConfig.get().getString("Database Config" + "." + "Password");
    }
    public String getIp(){
        return this.ip = DatabaseConfig.get().getString("Database Config" + "." + "IP");
    }
    public String getTable(){
        return this.table = DatabaseConfig.get().getString("Database Config" + "." + "Table");
    }
    public String getUUIDColumnName(){
        return this.UUIDColumnName = DatabaseConfig.get().getString("Database Config" + "." + "UUID-Column");
    }
    public String getBalanceColumnName(){
        return this.BalanceColumnName = DatabaseConfig.get().getString("Database Config" + "." + "Balance-Column");
    }
    public String getUrl(){
        return this.url = "jdbc:mysql://" + this.getIp() + "?allowPublicKeyRetrieval=true&useSSL=false";
    }

}
