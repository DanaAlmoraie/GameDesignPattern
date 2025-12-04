public class StopMoveCommand implements Command {

    private Player player;

    public StopMoveCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.stopMoving();
    }
}
