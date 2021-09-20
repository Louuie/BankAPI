
package bank.bank.Config;

public class ConfigSetter {
    String configName = DatabaseConfig.get().getString("Database Config");

    public void configSetup() {
        if (configName == null) {
            DatabaseConfig.get().set("Database Config" + "." + "Username", "");
            DatabaseConfig.get().set("Database Config" + "." + "Password", "");
            DatabaseConfig.get().set("Database Config" + "." + "IP", "");
            DatabaseConfig.get().set("Database Config" + "." + "Table", "");
            DatabaseConfig.get().set("Database Config" + "." + "UUID-Column", "");
            DatabaseConfig.get().set("Database Config" + "." + "Balance-Column", "");
            DatabaseConfig.save();
        }
    }
}