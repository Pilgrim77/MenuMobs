package superbas11.menumobs.ASM.mixin;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import superbas11.menumobs.ASM.Interface;

@Mixin(EntityHorse.class)
public abstract class Test extends EntityAnimal implements Interface {
    public Test(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract void setHorseVariant(int variant);

    @Override
    public void ChangeMe() {
        int baseColor = rand.nextInt(7);
        int markings = rand.nextInt(5);
        this.setHorseVariant(baseColor | markings << 8);
    }
}
