package org.seed419.founddiamonds.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.seed419.founddiamonds.FoundDiamonds;

/**
 * Attribute Only (Public) License
 * Version 0.a3, July 11, 2011
 * <p/>
 * Copyright (C) 2012 Blake Bartenbach <seed419@gmail.com> (@seed419)
 * <p/>
 * Anyone is allowed to copy and distribute verbatim or modified
 * copies of this license document and altering is allowed as long
 * as you attribute the author(s) of this license document / files.
 * <p/>
 * ATTRIBUTE ONLY PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 * <p/>
 * 1. Attribute anyone attached to the license document.
 * Do not remove pre-existing attributes.
 * <p/>
 * Plausible attribution methods:
 * 1. Through comment blocks.
 * 2. Referencing on a site, wiki, or about page.
 * <p/>
 * 2. Do whatever you want as long as you don't invalidate 1.
 *
 * @license AOL v.a3 <http://aol.nexua.org>
 */
public class PlayerDamageListener implements Listener {


    private FoundDiamonds fd;


    public PlayerDamageListener(FoundDiamonds fd) {
        this.fd = fd;
    }

    @EventHandler
    public void onPlayerDamage(final EntityDamageEvent event) {
        if ((event.getCause() == DamageCause.FALL) && (event.getEntity() instanceof Player)) {
            final Player player = (Player) event.getEntity();
            if (fd.getPotionHandler().playerHasJumpPotion(player)) {
                event.setCancelled(true);
            }
        }
    }
}
