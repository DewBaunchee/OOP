package sample.commands;

import sample.Hierarchy.Legion;

public class AddingRankCommand implements ICommand {

    private final Legion legion;
    private final int index;

    public AddingRankCommand(Legion legion, int index) {
        this.legion = legion;
        this.index = index;
    }

    @Override
    public void execute() {
        legion.addRankTo(index);
    }

    @Override
    public void undo() {
        legion.removeRankTo(index);
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public String purpose() {
        return "Adding rank to #" + index;
    }
}
