public interface GameState {
    void handleInput(Command command);
    void update();
}
