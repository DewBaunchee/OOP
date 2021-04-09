package sample.commands;

import sample.Hierarchy.Legion;
import sample.Hierarchy.Unit;

public class AddingCommand implements ICommand {

    private final Legion legion;
    private final Unit unit;

    public AddingCommand(Legion legion, Unit unit) {
        this.legion = legion;
        this.unit = unit;
    }

    @Override
    public void execute() {
        legion.addUnit(unit);
    }

    @Override
    public void undo() {
        legion.removeUnit(unit);
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public String purpose() {
        return "Command adding unit: " + unit.toString();
    }
}
