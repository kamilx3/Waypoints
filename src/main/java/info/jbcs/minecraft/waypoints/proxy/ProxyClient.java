package info.jbcs.minecraft.waypoints.proxy;

import info.jbcs.minecraft.waypoints.Waypoints;
import info.jbcs.minecraft.waypoints.init.WaypointsBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ProxyClient extends Proxy {
    private Minecraft mc;

    public static void reg(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register
                (item, 0, new ModelResourceLocation(Waypoints.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));

    }

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        mc = Minecraft.getMinecraft();
        reg(Item.getItemFromBlock(WaypointsBlocks.WAYPOINT));
    }

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return (ctx.side.isClient() ? (EntityPlayer) mc.player : super.getPlayerEntity(ctx));
    }

    @Override
    public IThreadListener getThreadFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? mc : super.getThreadFromContext(ctx));
    }
}
