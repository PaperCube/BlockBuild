package studio.papercube.blockbuild.edgesupport.binlevel;

/**
 * Created by imzhy on 2016/12/4.
 */
public class BlockEvent {
    byte type;
    short blockId;

    /**
     * type == 0:<br/>
     * affects moving_platforms[block_id]<br/>
     * payload == 0:<br/>
     * traverse all waypoints<br/>
     * payload != 0:<br/>
     * traverse `payload` waypoints.<br/>
     * type == 1:<br/>
     * affects bumpers[block_id]<br/>
     * payload == 0:<br/>
     * if bumper is running, stop it<br/>
     * else fire it once<br/>
     * payload == 1:<br/>
     * start the bumper and enable looping<br/>
     * type == 2:<br/>
     * triggers achievements.<br/>
     * block_id is the achievement ID<br/>
     * payload is additional metadata that varies between different achievements.<br/>
     * type == 3:<br/>
     * affects buttons[block_id]<br/>
     * payload == 0:<br/>
     * enable the button (pop it up)<br/>
     * payload == 1:<br/>
     * <p>
     * disable the button<br/>
     */
    short payload;

    public void setType(byte type) {
        this.type = type;
    }

    public void setBlockId(short blockId) {
        this.blockId = blockId;
    }

    public void setPayload(short payload) {
        this.payload = payload;
    }

    public byte getType() {
        return type;
    }

    public short getBlockId() {
        return blockId;
    }

    public short getPayload() {
        return payload;
    }
}
