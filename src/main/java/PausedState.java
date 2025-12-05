public class PausedState implements GameState {


    public PausedState() {
    }

    // the player doesn't move because the game is paused
    @Override
    public void handleInput(Command command) {
      
    }

    @Override
    public void update() {
        // here we do nothing because the game is paused
    }
}
