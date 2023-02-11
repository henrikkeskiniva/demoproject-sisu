package fi.tuni.prog3.sisu;

import java.util.ArrayList;

public class ModuleExtended extends Module {

    public int depth = 0;
    public String belongsTo = null;
    public ArrayList<String> owns = new ArrayList<>();
    

    public ArrayList<String> getOwns() {
        return owns;
    }

    public void setOwns(ArrayList<String> owns) {
        this.owns = owns;
    }

    public ModuleExtended() {
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public int getIndex() {
        return moduleIndex;
    }

    public void setIndex(int index) {
        this.moduleIndex = index;
    }
}
