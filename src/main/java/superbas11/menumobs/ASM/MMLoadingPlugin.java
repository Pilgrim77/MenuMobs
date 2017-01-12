package superbas11.menumobs.ASM;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import superbas11.menumobs.MenuMobs;

import java.util.Map;
import java.util.Objects;

public class MMLoadingPlugin implements IFMLLoadingPlugin{
    /**
     * Return a list of classes that implements the IClassTransformer interface
     *
     * @return a list of classes that implements the IClassTransformer interface
     */
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{MMClassTransformer.class.getName()};
    }

    /**
     * Return a class name that implements "ModContainer" for injection into the mod list
     * The "getName" function should return a name that other mods can, if need be,
     * depend on.
     * Trivially, this modcontainer will be loaded before all regular mod containers,
     * which means it will be forced to be "immutable" - not susceptible to normal
     * sorting behaviour.
     * All other mod behaviours are available however- this container can receive and handle
     * normal loading events
     */
    @Override
    public String getModContainerClass() {
        return MMCoreMod.class.getName();
    }

    /**
     * Return the class name of an implementor of "IFMLCallHook", that will be run, in the
     * main thread, to perform any additional setup this coremod may require. It will be
     * run <strong>prior</strong> to Minecraft starting, so it CANNOT operate on minecraft
     * itself. The game will deliberately crash if this code is detected to trigger a
     * minecraft class loading
     * TODO: implement crash ;)
     */
    @Override
    public String getSetupClass() {
        return MMCallHook.class.getName();
    }

    /**
     * Inject coremod data into this coremod
     * This data includes:
     * "mcLocation" : the location of the minecraft directory,
     * "coremodList" : the list of coremods
     * "coremodLocation" : the file this coremod loaded from,
     *
     * @param data
     */
    @Override
    public void injectData(Map<String, Object> data) {
        for (Map.Entry<String,Object> entry: data.entrySet()) {
            //System.out.println(String.format("[MenuMobs][LoadingPlugin][injectData] %s -> %s", entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Return an optional access transformer class for this coremod. It will be injected post-deobf
     * so ensure your ATs conform to the new srgnames scheme.
     *
     * @return the name of an access transformer class or null if none is provided
     */
    @Override
    public String getAccessTransformerClass() {
        return MMAccessTransformer.class.getName();
    }
}
