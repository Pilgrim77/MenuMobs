package superbas11.menumobs.ASM;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import superbas11.menumobs.util.LogHelper;

import java.util.Map;

public class MMCallHook implements IFMLCallHook{
    /**
     * Injected with data from the FML environment:
     * "classLoader" : The FML Class Loader
     *
     * @param data
     */
    @Override
    public void injectData(Map<String, Object> data) {
        for (Map.Entry entry: data.entrySet()) {
            //System.out.println(String.format("[MenuMobs][CallHook] %s -> %s", entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Void call() throws Exception {
        //System.out.println("[MenuMobs][CallHook] Called!");
        return null;
    }
}
