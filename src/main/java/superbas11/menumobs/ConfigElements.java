package superbas11.menumobs;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Property;

import static net.minecraftforge.common.config.Property.Type.*;

public enum ConfigElements {
    SHOW_MAIN_MENU_MOBS("showMainMenuMobs", "superbas11.configgui.showMainMenuMobs", "false", BOOLEAN),
    SHOW_ONLY_PLAYER_MODELS("showOnlyPlayerModels", "superbas11.configgui.showOnlyPlayerModels", "false", BOOLEAN),
    MOB_SOUNDS_VOLUME("mobSoundVolume", "superbas11.configgui.mobSoundVolume", "0.5", DOUBLE),
    FIXED_MOB("fixedMob", "superbas11.configgui.fixedMob", new String[]{}, STRING),
    BLACKLIST("blacklist", "superbas11.configgui.blacklist", new String[]{}, STRING),
    ALLOW_DEBUG_OUTPUT("allowDebugOutput", "superbas11.configgui.allowDebugOutput", "false", BOOLEAN);

    private String key;
    private String langKey;
    private String desc;
    private Object Default;
    private Object[] Defaults;
    private Property.Type propertyType;

    private ConfigElements(String key, String langKey, Object Default, Property.Type propertyType) {
        this.key = key;
        this.langKey = langKey;
        this.desc = I18n.format(langKey + ".tooltip");
        this.propertyType = propertyType;
    }

    private ConfigElements(String key, String langKey, Object[] Defaults, Property.Type propertyType) {
        this(key, langKey, "", propertyType);
        this.Defaults = Defaults;
    }

    public String key() {
        return key;
    }

    public String languageKey() {
        return langKey;
    }

    public String desc() {
        return desc;
    }

    public Property.Type propertyType() {
        return propertyType;
    }

}