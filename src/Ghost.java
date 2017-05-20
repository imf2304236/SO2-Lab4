import java.util.Random;
/**
 * Class Ghost - a ghost in the game "Ghostbusters".
 */
public class Ghost {
    private Room currentRoom;
    private Boolean hidden;
    private Boolean captured;
    private Random randomGen;

    public Ghost() {
        this.hidden = true;
        this.captured = false;
        randomGen = new Random();
    }

    public Ghost(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.hidden = true;
        this.captured = false;
        randomGen = new Random();
    }

    public boolean capture() {
        int random = randomGen.nextInt(49);

        if (random >= 0 && random <= 19) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }
}
