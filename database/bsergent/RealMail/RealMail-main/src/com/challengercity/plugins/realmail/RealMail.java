/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.challengercity.plugins.realmail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
/**
 * 
 *
 * @author Ben Sergent V
 */
public class RealMail extends JavaPlugin {

    // TODO Add letter delivery queue to deliver at a specific time option
    
    private final String version = "0.3.2";
    private org.bukkit.configuration.file.FileConfiguration mailboxesConfig = null;
    private java.io.File mailboxesFile = null;
    private org.bukkit.configuration.file.FileConfiguration packagesConfig = null;
    private java.io.File packagesFile = null;
    private org.bukkit.configuration.file.FileConfiguration languageConfig = null;
    private java.io.File languageFile = null;
    private ItemMeta mailboxRecipeMeta = null;
    private org.bukkit.inventory.meta.BookMeta stationeryMeta = null;
    private String prefix = ChatColor.WHITE+"["+ChatColor.GOLD+"Mail"+ChatColor.WHITE+"]";
    
    /* Mailbox Textures */
    private final String mailboxTextureBlue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjZhNDllZmFhYWI1MzI1NTlmZmY5YWY3NWRhNmFjNGRkNzlkMTk5ZGNmMmZkNDk3Yzg1NDM4MDM4NTY0In19fQ==";
    private final String mailboxIdBlue = "48614330-6c44-47be-85ec-33ed037cf48c";
    private final String mailboxTextureWhite = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM5ZTE5NzFjYmMzYzZmZWFhYjlkMWY4NWZjOWQ5YmYwODY3NjgzZjQxMjk1NWI5NjExMTdmZTY2ZTIifX19";
    private final String mailboxIdWhite = "480bff09-ed89-4214-a2bd-dab19fa5177d";
    private final String mailboxTextureRed = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGZhODljZTg1OTMyYmVjMWExYzNmMzFjYjdjMDg1YTViZmIyYWM3ZTQwNDA5NDIwOGMzYWQxMjM4NzlkYTZkYSJ9fX0=";
    private final String mailboxIdRed = "6a71ad04-2422-41f3-a501-6ea5707aaef3";
    private final String mailboxTextureGreen = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJiY2NiNTI0MDg4NWNhNjRlNDI0YTBjMTY4YTc4YzY3NmI4Yzg0N2QxODdmNmZiZjYwMjdhMWZlODZlZSJ9fX0=";
    private final String mailboxIdGreen = "60621c0e-cb3e-471b-a237-4dec155f4889";
    
    public final class MailListener implements org.bukkit.event.Listener {
    }
    
    //<editor-fold defaultstate="collapsed" desc="Login notifications">
    public final class LoginListener implements org.bukkit.event.Listener {
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Login runnable">
    public final class LoginRunnable implements Runnable {
        
        private org.bukkit.event.player.PlayerJoinEvent event;
        
    }
    //</editor-fold>
    
}
