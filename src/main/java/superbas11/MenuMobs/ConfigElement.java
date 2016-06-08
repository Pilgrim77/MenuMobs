package superbas11.MenuMobs;

import net.minecraftforge.common.config.Property;

import static net.minecraftforge.common.config.Property.Type.BOOLEAN;

public enum ConfigElement {
    ALLOW_DEBUG_OUTPUT("allowDebugOutput", "superbas11.configgui.allowDebugOutput", "", BOOLEAN),
    SHOW_MAIN_MENU_MOBS("showMainMenuMobs", "superbas11.configgui.showMainMenuMobs",
            "Set to true to show your logged-in player and a random mob on the main menu, false to disable.", BOOLEAN);

    private String key;
    private String langKey;
    private String desc;
    private Property.Type propertyType;
    private String[] validStrings;

    ConfigElement(String key, String langKey, String desc, Property.Type propertyType, String[] validStrings) {
        this.key = key;
        this.langKey = langKey;
        this.desc = desc;
        this.propertyType = propertyType;
        this.validStrings = validStrings;
    }

    ConfigElement(String key, String langKey, String desc, Property.Type propertyType) {
        this(key, langKey, desc, propertyType, new String[0]);
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

    public String[] validStrings() {
        return validStrings;
    }
}