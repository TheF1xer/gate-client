package com.thef1xer.gateclient.managers;

import com.google.gson.*;
import com.thef1xer.gateclient.GateClient;
import com.thef1xer.gateclient.preset.Preset;
import com.thef1xer.gateclient.util.DirectoryUtil;

import java.io.*;
import java.util.Map;

public class ConfigManager {
    public File configFile = new File(DirectoryUtil.GATE_FOLDER, "config.json");

    public void init() {
        DirectoryUtil.GATE_FOLDER.mkdir();
        DirectoryUtil.PRESET_FOLDER.mkdir();
        this.load();
    }

    public void save() {
        Preset activePreset = GateClient.getGate().presetManager.activePreset;
        JsonObject config = new JsonObject();
        config.addProperty("Active Config", activePreset.getFile() != null ? activePreset.getFile().toString() : new File(DirectoryUtil.PRESET_FOLDER, "default.json").toString());
        config.addProperty("Prefix", GateClient.getGate().commandManager.getPrefix());
        try {
            FileWriter writer = new FileWriter(configFile);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(config));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        GateClient.getGate().presetManager.updatePresetList();
        JsonParser parser = new JsonParser();

        if (!configFile.exists()) {
            this.save();
        }

        try {
            JsonObject config = parser.parse(new FileReader(configFile)).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
                String key = entry.getKey();
                JsonElement val = entry.getValue();

                if (key.equals("Active Config")) {
                    GateClient.getGate().presetManager.activePreset.setFile(new File(val.getAsString()));
                    continue;
                }

                if (key.equals("Prefix")) {
                    GateClient.getGate().commandManager.setPrefix(val.getAsString());
                }
            }

            this.save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.save();
    }
}
