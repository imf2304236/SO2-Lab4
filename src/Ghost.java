/**
 * Class Ghost - a ghost in the game "Ghostbusters".
 */
public class Ghost {
    private String description;
    private Room currentRoom;
    private Boolean hidden;
    private Boolean captured;

    public void Ghost(String description, Room currentRoom) {
        this.description = description;
        this.currentRoom = currentRoom;
        this.hidden = false;
        this.captured = false;
    }
}
