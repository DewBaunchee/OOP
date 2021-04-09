package sample.commands;

public interface ICommand {
    void execute();
    void undo();
    void redo();
    String purpose();
}

/*
* TODO добавить юнит
* TODO удалить юнит
* TODO добавить ранг
* */
