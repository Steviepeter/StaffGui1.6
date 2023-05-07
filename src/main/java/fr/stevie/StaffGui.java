package fr.stevie;


import fr.stevie.commands.CommandStaff;
import fr.stevie.listeners.InventoryClickListener;
import fr.stevie.listeners.PlayerDamageListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class StaffGui extends JavaPlugin {

    private static StaffGui instance;

    private File l = null;
    private final YamlConfiguration lang = new YamlConfiguration();

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("&aV1 faite par Stevie have fun");

        instance = this;
        this.l = new File(getDataFolder(), "language.yml");
        mkdir();
        loadYamls();

        new InventoryClickListener(this);
        new PlayerDamageListener(this);

        Objects.requireNonNull(this.getCommand("staffmode")).setExecutor(new CommandStaff());
    }
    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("&aN'hésite pas a me dire si jamais il y a des bugs");}
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