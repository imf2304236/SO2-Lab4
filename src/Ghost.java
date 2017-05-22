import java.util.Random;
/**
 * Class Ghost - a ghost in the game "Ghostbusters".
 */
public class Ghost {
    private Room currentRoom;
    private Boolean hidden;
    private Boolean captured;
    private Random randomGen;

    /**
     * Create hidden ghost
     */
    public Ghost() {
        this.hidden = true;
        this.captured = false;
        randomGen = new Random();
    }

    /**
     * Attempt to capture the ghost.
     * There is a chance that the ghost may or may not be captured.
     * @return true If capture is successful, false otherwise
     */
    public boolean capture() {
        int random = randomGen.nextInt(49);

        if (random >= 0 && random <= 19) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Return hidden state
     * @return true if hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * Update hidden state
     * @param hidden true to hide, false otherwise
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Return the ghost's location.
     * @return the ghost's current room
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Update the ghost's location
     * @param currentRoom The room to move the ghost to
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * Return the ghost's captured state
     * @return true if captured, false otherwise
     */
    public Boolean getCaptured() {
        return captured;
    }

    /**
     * Update the ghost's captured state
     * @param captured true to capture, false to set free
     */
    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }
}
