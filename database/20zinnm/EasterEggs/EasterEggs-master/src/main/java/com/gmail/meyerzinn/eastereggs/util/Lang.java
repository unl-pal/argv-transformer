package com.gmail.meyerzinn.eastereggs.util;
 
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
 
/**
* An enum for requesting strings from the language file.
* @author gomeow
*/
public enum Lang {
    TITLE("title-name", "&8[&e&lEasterEggs&8]: "),
    INVALID_ARGS("invalid-args", "&cInvalid args! Use like /eggs [allow/deny]"),
    TOGGLED_ALLOWED("toggled-allowed", "&eSuccessfully toggled: enabling easter eggs."),
    TOGGLED_DENIED("toggled-denied", "&eSuccessfully toggled: disabling easter eggs."),
    EGG_LAND("egg-land", "&bAn &aeaster egg &blanded at &a%l &bin world &a%w&b! It may drop items, or just blow up!"),
    EGG_LAUNCH("egg-launch", "&bYou launched an easter egg!"),
    NO_PERMS_EGG("no-permissions-egg", "&cYou don't have permission to launch an egg while Easter Eggs is active!"),
    MUST_HAVE_ITEM("must-have-item", "&cYou must have an item in your hand to use this command!"),
    PLAYER_ONLY("player-only", "&cYou must be a player to use this command!"),
    ADDED_DROP("added-drop", "&cYou have added a drop!"),
    REMOVED_DROP("removed-drop", "&cYou have removed a drop!"),
    NUMBER_ONLY("number-only", "&cNumbers only!"),
    NO_PERMS("no-permissions", "&cYou don't have permission for that!");
 
    private String path;
    private String def;
    private static YamlConfiguration LANG;
 
    /**
    * Lang enum constructor.
    * @param path The string path.
    * @param start The default string.
    */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
 
    /**
    * Set the {@code YamlConfiguration} to use.
    * @param config The config to set.
    */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }
 
    @Override
    public String toString() {
        if (this == TITLE)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }
 
    /**
    * Get the default value of the path.
    * @return The default value of the path.
    */
    public String getDefault() {
        return this.def;
    }
 
    /**
    * Get the path to the string.
    * @return The path to the string.
    */
    public String getPath() {
        return this.path;
    }
}