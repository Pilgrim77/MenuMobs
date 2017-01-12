package superbas11.menumobs.ASM;

import net.minecraft.launchwrapper.IClassTransformer;

public class MMClassTransformer implements IClassTransformer{
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        //System.out.println(String.format("[MenuMobs][ClassTransformer] name: %s, transformedName: %s", name, transformedName));
        return basicClass;
    }
}
