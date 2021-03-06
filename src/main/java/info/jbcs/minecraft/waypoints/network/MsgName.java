package info.jbcs.minecraft.waypoints.network;

import info.jbcs.minecraft.waypoints.Waypoint;
import info.jbcs.minecraft.waypoints.Waypoints;
import info.jbcs.minecraft.waypoints.block.BlockWaypoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;

import java.io.IOException;

public class MsgName extends AbstractMessage.AbstractServerMessage<MsgName> {
    private BlockPos pos;
    private int waypointId;
    private String name;

    public MsgName() {
    }

    public MsgName(BlockPos pos, int waypointId, String name) {
        this.pos = pos;
        this.waypointId = waypointId;
        this.name = name;
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        int x = buffer.readInt();
        int y = buffer.readInt();
        int z = buffer.readInt();
        pos = new BlockPos(x, y, z);
        waypointId = buffer.readInt();
        name = ByteBufUtils.readUTF8String(buffer);

    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeInt(pos.getX());
        buffer.writeInt(pos.getY());
        buffer.writeInt(pos.getZ());
        buffer.writeInt(waypointId);
        ByteBufUtils.writeUTF8String(buffer, name);

    }

    @Override
    public void process(EntityPlayer player, Side side) {
        if (!BlockWaypoint.isEntityOnWaypoint(player.world, pos, player))
            return;
        Waypoint w = Waypoint.getWaypoint(player.world, pos);
        if(w==null) return;
        w.name = name;
        w.changed = true;
        Waypoints.log("Waypoint :" + w.name + ": (" + w.pos.toString() + ") named  '" + w.name + "'");
    }

}