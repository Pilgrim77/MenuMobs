package superbas11.menumobs.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;

import javax.annotation.Nullable;
import java.util.UUID;

public class FakeNetHandlerPlayClient extends NetHandlerPlayClient {
    private NetworkPlayerInfo playerInfo;

    public FakeNetHandlerPlayClient(Minecraft mcIn) {
        super(mcIn, mcIn.currentScreen, new FakeNetworkManager(EnumPacketDirection.CLIENTBOUND), mcIn.getSession().getProfile());
        this.playerInfo = new NetworkPlayerInfo(mcIn.getSession().getProfile());
    }

    @Override
    public NetworkPlayerInfo getPlayerInfo(UUID uniqueId) {
        return this.playerInfo;
    }

    @Nullable
    @Override
    public NetworkPlayerInfo getPlayerInfo(String name) {
        return this.playerInfo;
    }
}
