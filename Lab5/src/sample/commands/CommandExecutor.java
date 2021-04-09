package sample.commands;

import java.util.Stack;

public class CommandExecutor {
    private final Stack<ICommand> undoStack;
    private final Stack<ICommand> redoStack;

    public CommandExecutor() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void execute(ICommand cmd) {
        if(cmd == null) {
            return;
        }
        System.out.println("Executing: " + cmd.purpose());

        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if(undoStack.empty()) return;
        ICommand cmd = undoStack.pop();
        System.out.println("Undoing: " + cmd.purpose());

        redoStack.push(cmd);
        cmd.undo();
    }

    public void redo() {
        if(redoStack.empty()) return;
        ICommand cmd = redoStack.pop();
        System.out.println("Redoing: " + cmd.purpose());

        undoStack.push(cmd);
        cmd.redo();
    }
}
