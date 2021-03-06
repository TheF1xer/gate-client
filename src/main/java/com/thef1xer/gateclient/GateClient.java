package com.thef1xer.gateclient;

import com.thef1xer.gateclient.gui.hud.HUD;
import com.thef1xer.gateclient.managers.*;
import com.thef1xer.gateclient.util.Reference;
import com.thef1xer.gateclient.handlers.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class GateClient {
    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public ConfigManager configManager;
    public PresetManager presetManager;
    public GuiManager guiManager;

    public HUD hud;
    public EventHandler eventHandler;

    @Mod.Instance
    private static GateClient gate;

    public static GateClient getGate() {
        return GateClient.gate;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        presetManager = new PresetManager();
        guiManager = new GuiManager();

        hud = new HUD();
        eventHandler = new EventHandler();

        MinecraftForge.EVENT_BUS.register(eventHandler);
        MinecraftForge.EVENT_BUS.register(hud);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        moduleManager.init();
        commandManager.init();
        hud.init();
        guiManager.init();
        configManager.init();
        presetManager.init();
    }
}
