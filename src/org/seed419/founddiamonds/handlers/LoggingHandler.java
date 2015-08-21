package org.seed419.founddiamonds.handlers;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.seed419.founddiamonds.FoundDiamonds;
import org.seed419.founddiamonds.file.Config;
import org.seed419.founddiamonds.util.Format;
import org.seed419.founddiamonds.util.Prefix;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Attribute Only (Public) License
 * Version 0.a3, July 11, 2011
 *
 * Copyright (C) 2012 Blake Bartenbach <seed419@gmail.com> (@seed419)
 *
 * Anyone is allowed to copy and distribute verbatim or modified
 * copies of this license document and altering is allowed as long
 * as you attribute the author(s) of this license document / files.
 *
 * ATTRIBUTE ONLY PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 1. Attribute anyone attached to the license document.
 * Do not remove pre-existing attributes.
 *
 * Plausible attribution methods:
 * 1. Through comment blocks.
 * 2. Referencing on a site, wiki, or about page.
 *
 * 2. Do whatever you want as long as you don't invalidate 1.
 *
 * @license AOL v.a3 <http://aol.nexua.org>
 */
public class LoggingHandler {


    private FoundDiamonds fd;


    public LoggingHandler(FoundDiamonds fd) {
        this.fd = fd;
    }

    public void handleLogging(Player player, Block block, boolean trapBlock, boolean kicked, boolean banned) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fd.getFileHandler().getLogFile(), true)));
            pw.print("[" + getFormattedDate() + "]");
            if (trapBlock) {
                pw.print(" [TRAP BLOCK]");
            }
            pw.println(" " + block.getType().name().toLowerCase().replace("_", " ") + " broken by "
                    + player.getName() + " at (x: " + block.getX() + ", y: " + block.getY() + ", z: " + block.getZ()
                    + ") in " + player.getWorld().getName());
            if (trapBlock) {
                pw.print("[" + getFormattedDate() + "]" + " [ACTION TAKEN] ");
                if (kicked && !banned) {
                    pw.println(player.getName() + " was kicked from the sever per the configuration.");
                } else if (banned && !kicked) {
                    pw.println(player.getName() + " was banned from the sever per the configuration.");
                } else if (banned && kicked) {
                    pw.println(player.getName() + " was kicked and banned from the sever per the configuration.");
                } else {
                    pw.println(player.getName() + " was neither kicked nor banned per the configuration.");
                }
            }
            pw.flush();
            fd.getFileUtils().close(pw);
        } catch (IOException ex) {
            fd.getLog().severe(MessageFormat.format("Unable to write to log file {0}", ex));
        }
    }

    public void logLightLevelViolation(final Material mat, final Player player) {
        String lightLogMsg = "[" + getFormattedDate() + "]" + " " + player.getName() + " mined "
                + Format.getFormattedName(mat, 1) + " below " +  fd.getConfig().getString(Config.percentOfLightRequired) +
                " light";
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fd.getFileHandler().getLogFile(), true)));
            pw.println(lightLogMsg);
            pw.flush();
            fd.getFileUtils().close(pw);
        } catch (IOException ex) {
            fd.getLog().severe(MessageFormat.format("Unable to write to light level violation to log.txt file {0}", ex));
        }
    }

    public void writeToCleanLog(final String matName, final int blockTotal, final String playerName) {
        String formattedDate = getFormattedDate();
        String message = fd.getConfig().getString(Config.bcMessage).replace("@Prefix@", Prefix.getChatPrefix()).replace("@Player@",
                playerName).replace("@Number@",
                (blockTotal) == 500 ? "over 500" :String.valueOf(blockTotal)).replace("@BlockName@", matName);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fd.getFileHandler().getCleanLog(), true)));
            pw.println("[" + formattedDate + "] " + message);
            pw.flush();
            fd.getFileUtils().close(pw);
        } catch (IOException ex) {
            fd.getLog().severe(MessageFormat.format("Unable to write to clean log {0}", ex));
        }
    }

    private String getFormattedDate() {
        Date todaysDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss");
        return formatter.format(todaysDate);
    }

}
