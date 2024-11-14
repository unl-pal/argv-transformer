package com.gmail.meyerzinn.eastereggs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.meyerzinn.eastereggs.commands.AddDropCommand;
import com.gmail.meyerzinn.eastereggs.commands.EggsCommand;
import com.gmail.meyerzinn.eastereggs.commands.ListDropsCommand;
import com.gmail.meyerzinn.eastereggs.commands.RemoveDropCommand;
import com.gmail.meyerzinn.eastereggs.listeners.EggListener;
import com.gmail.meyerzinn.eastereggs.util.Lang;

public class EasterEggs extends JavaPlugin {

	public static Boolean allowEggs;
	public static Boolean broadcast;
	public static Double explode;
	public static HashMap<Long, ItemStack> drops = new HashMap<Long, ItemStack>();

	Logger log = getLogger();

	public static YamlConfiguration LANG;
	public static File LANG_FILE;
}
