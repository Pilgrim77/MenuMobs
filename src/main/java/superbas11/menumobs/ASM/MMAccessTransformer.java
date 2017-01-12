package superbas11.menumobs.ASM;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;
import java.util.Map;

public class MMAccessTransformer extends AccessTransformer{
    public MMAccessTransformer() throws IOException {
        //super("META-INF/MenuMobs_at.cfg");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        //System.out.println(String.format("[MenuMobs][AccessTransformer] name: %s, transformedName: %s", name, transformedName));
        //byte[] newClass = super.transform(name,transformedName,basicClass);
        //if (!basicClass.equals(newClass))
            //System.out.println("File Changed.");
        return basicClass;
    }
}
