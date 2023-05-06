package fr.stevie;


import fr.stevie.commands.CommandStaff;
import fr.stevie.listeners.InventoryClickListener;
import fr.stevie.listeners.PlayerDamageListener;
import fr.stevie.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.IOException;

public final class StaffGui extends JavaPlugin {

    private static StaffGui instance;

    public static String new_version = null;

    private File l = null;
    private final YamlConfiguration lang = new YamlConfiguration();

    @Override
    public void onEnable() {

        instance = this;
        this.l = new File(getDataFolder(), "language.yml");
        mkdir();
        loadYamls();

        new InventoryClickListener(this);
        new PlayerDamageListener(this);

        this.getCommand("staffmode").setExecutor(new CommandStaff());
    }
    @Override
    public void onDisable() {
        System.out.println("Plugin désactivé");
    }

    private void mkdir(){
        if (!this.l.exists()) {
            saveResource("language.yml", false);
        }
    }


    private void loadYamls(){
        try {
            this.lang.load(this.l);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getLang() { return this.lang; }

    public static StaffGui getInstance(){
        return instance;
    }
}



