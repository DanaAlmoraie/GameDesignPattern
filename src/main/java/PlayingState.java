public class PlayingState implements GameState {

    private Board board;

    public PlayingState(Board board) {
        this.board = board;
    }

    @Override
    public void handleInput(Command command) {
        if (command != null) {
            command.execute();
        }
    }

    @Override
    public void update() {
        // في وضع اللعب العادي نحدّث اللعبة كاملة
        board.animationCycle();
    }
}
