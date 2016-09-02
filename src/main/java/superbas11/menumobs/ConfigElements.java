package superbas11.menumobs;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import superbas11.menumobs.gui.FixedMobEntry;
import superbas11.menumobs.gui.VolumeSliderEntry;

import static net.minecraftforge.common.config.Property.Type.*;

public enum ConfigElements {
    SHOW_MAIN_MENU_MOBS("showMainMenuMobs", "superbas11.configgui.showMainMenuMobs", "true", BOOLEAN),
    SHOW_ONLY_PLAYER_MODELS("showOnlyPlayerModels", "superbas11.configgui.showOnlyPlayerModels", "false", BOOLEAN),
    MOB_SOUNDS_VOLUME("mobSoundVolume", "superbas11.configgui.mobSoundVolume", "0.5", DOUBLE, 0.0F, 1.0F, VolumeSliderEntry.class),
    FIXED_MOB("fixedMob", "superbas11.configgui.fixedMob", new String[]{}, STRING, FixedMobEntry.class),
    BLACKLIST("blacklist", "superbas11.configgui.blacklist",new String[]{}, STRING),
    ALLOW_DEBUG_OUTPUT("allowDebugOutput", "superbas11.configgui.allowDebugOutput", "false", BOOLEAN);

    private String key;
    private String langKey;
    private Property.Type propertyType;
    private Boolean hasMinMax = false;
    private double min, max;
    private Property Setting;
    private Class<? extends GuiConfigEntries.IConfigEntry> EntryClass;

    ConfigElements(String key, String langKey, String defaultString, Property.Type propertyType) {
        this.key = key;
        this.langKey = langKey;
        this.propertyType = propertyType;
        this.Setting = Reference.config.get(Configuration.CATEGORY_GENERAL, key, defaultString, I18n.format(langKey + ".tooltip"), propertyType).setLanguageKey(langKey);
    }

    ConfigElements(String key, String langKey, String[] defaultString, Property.Type propertyType) {
        this.key = key;
        this.langKey = langKey;
        this.propertyType = propertyType;
        this.Setting = Reference.config.get(Configuration.CATEGORY_GENERAL, key, defaultString, I18n.format(langKey + ".tooltip"), propertyType).setLanguageKey(langKey);
    }

    ConfigElements(String key, String langKey, String[] defaultString, Property.Type propertyType, Class<? extends GuiConfigEntries.IConfigEntry> clazz) {
        this.key = key;
        this.langKey = langKey;
        this.propertyType = propertyType;
        this.EntryClass = clazz;
        this.Setting = Reference.config.get(Configuration.CATEGORY_GENERAL, key, defaultString, I18n.format(langKey + ".tooltip"), STRING);
    }

    ConfigElements(String key, String langKey, String defaultString, Property.Type propertyType, double min, double max, Class<? extends GuiConfigEntries.IConfigEntry> clazz) {
        this(key, langKey, defaultString, propertyType);
        this.min = min;
        this.max = max;
        this.hasMinMax = true;
        this.EntryClass = clazz;
    }

    public String getKey() {
        return key;
    }

    public Property.Type propertyType() {
        return propertyType;
    }

    public Property getSetting() {
        return this.Setting;
    }

    public void syncConfig() {

        this.Setting = Reference.config.get(Configuration.CATEGORY_GENERAL, this.key, this.Setting.getDefault(), I18n.format(langKey + ".tooltip"), this.propertyType).setLanguageKey(this.langKey);
        if (this.EntryClass != null)
            this.Setting.setConfigEntryClass(this.EntryClass);
        if (hasMinMax)
            this.Setting.setMinValue(min).setMaxValue(max);
    }
}