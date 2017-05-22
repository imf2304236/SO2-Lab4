import java.util.Random;
/**
 * This class is part of the "Ghostbusters" application.
 * "Ghostbusters" is a very simple, text based adventure game.  Users
 * must explore a haunted mansion to find and capture ghosts to win the game.
 *
 * This class represents a Ghost object which haunts a particular room
 * and can be captured by the player.
 *
 * @author  Ian Fennie
 * @version 2017.05.22
 *
 *
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
