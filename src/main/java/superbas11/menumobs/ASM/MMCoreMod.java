package superbas11.menumobs.ASM;

import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Arrays;

public class MMCoreMod extends DummyModContainer {
    public MMCoreMod() {
        super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "menumobscore";
        meta.name = "MenuMobsCore";
        meta.version = "1.0";
        meta.credits = "Roll Credits ...";
        meta.authorList = Arrays.asList("superbas11");
        meta.description = "mijn coremod";
        meta.url = "https://github.com/superbas11";
        meta.parent = "menumobs";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }

    @Override
    public Disableable canBeDisabled() {
        return Disableable.NEVER;
    }
}
