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

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }
}
