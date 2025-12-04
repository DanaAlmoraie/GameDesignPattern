public class PausedState implements GameState {

    private Board board;

    public PausedState(Board board) {
        this.board = board;
    }

    @Override
    public void handleInput(Command command) {
        // أثناء الإيقاف المؤقت نسمح فقط بأمر الـ TogglePause
        if (command instanceof TogglePauseCommand) {
            command.execute();
        }
    }

    @Override
    public void update() {
        // لا شيء: اللعبة متوقفة عند نفس الإحداثيات (Player + Aliens + Bombs)
    }
}
