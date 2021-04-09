package sample.commands;

import sample.Hierarchy.Legion;
import sample.Hierarchy.Unit;

public class RemovingCommand implements ICommand {

    private final Legion legion;
    private final int index;
    private Unit removed;

    public RemovingCommand(Legion legion, int index) {
        this.legion = legion;
        this.index = index;
    }

    @Override
    public void execute() {
        legion.removeUnit(index);
    }

    @Override
    public void undo() {
        legion.insertUnit(index, removed);
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public String purpose() {
        return "Removing unit #" + index;
    }
}
