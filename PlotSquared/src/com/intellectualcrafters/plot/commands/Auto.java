/*
 * Copyright (c) IntellectualCrafters - 2014.
 * You are not allowed to distribute and/or monetize any of our intellectual property.
 * IntellectualCrafters is not affiliated with Mojang AB. Minecraft is a trademark of Mojang AB.
 *
 * >> File = Auto.java
 * >> Generated by: Citymonstret at 2014-08-09 01:40
 */

package com.intellectualcrafters.plot.commands;

import com.intellectualcrafters.plot.C;
import com.intellectualcrafters.plot.PlayerFunctions;
import com.intellectualcrafters.plot.Plot;
import com.intellectualcrafters.plot.PlotHelper;
import com.intellectualcrafters.plot.PlotId;
import com.intellectualcrafters.plot.PlotMain;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class Auto extends SubCommand {

    public Auto() {
        super("auto", "plots.auto", "Claim the nearest plot", "auto", "a", CommandCategory.CLAIMING);
    }

    public boolean execute(Player plr, String ... args) {
        World world;
        if (PlotMain.getPlotWorlds().length==1)
            world = Bukkit.getWorld(PlotMain.getPlotWorlds()[0]);
        else {
            if (PlotMain.isPlotWorld(plr.getWorld()))
                world = plr.getWorld();
            else {
                if (args.length==1) {
                    world = Bukkit.getWorld(args[0]);
                    if (world!=null) {
                        if (!PlotMain.isPlotWorld(world)) {
                            PlayerFunctions.sendMessage(plr, C.NOT_VALID_PLOT_WORLD);
                            return true;
                        }
                            
                    }
                    else {
                        PlayerFunctions.sendMessage(plr, C.NOT_VALID_WORLD);
                        return true;
                    }
                }
                else {
                    PlayerFunctions.sendMessage(plr, C.NOT_IN_PLOT_WORLD);
                    return true;
                }
            }
                
        }
        if(PlayerFunctions.getPlayerPlotCount(world, plr) >= PlayerFunctions.getAllowedPlots(plr)) {
            PlayerFunctions.sendMessage(plr, C.CANT_CLAIM_MORE_PLOTS);
            return true;
        }
        boolean br = false;
        int x = 0, z = 0, q = 100;
        PlotId id;
        while(!br) {
           id = new PlotId(x,z);
           if(PlotHelper.getPlot(world, id).owner == null) {
                Plot plot = PlotHelper.getPlot(world, id);
                Claim.claimPlot(plr, plot, true);
                br = true;
           }
           if(z < q && (z - x) < q) {
               z++;
           } else if(x < q) {
               x++;
               z = q - 100;
           } else {
               q += 100;
               x = q;
               z = q;
           }
        }
        return true;
    }

}
