import java.util.Set;
import java.util.HashMap;
import java.util.Random;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private Random randomGenerator;
    private boolean searched;
    private boolean haunted;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        randomGenerator = new Random();
        searched = false;
        haunted = false;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Search the room.
     * If the room's state defines it as searched, it will not be searched again.
     * If the room is not searched, the room's haunted state will be revealed to the player.
     */
    public void search()
    {
        if (searched == false) {
            if (haunted) {
                System.out.println("A spooky ghost appears! Quick capture it!");
            } else {
                System.out.println("The coast is clear. No ghosts in this room. For now...");
            }
            searched = true;
        } else {
            System.out.println("You already searched this room. Maybe try again a bit later..");
        }
    }

    /**
     * Return the room's searched state
     * @return true if searched, false otherwise
     */
    public boolean getSearched() { return searched; }

    /**
     * Update the room's searched state
     * @param bool true to be searched, false otherwise
     */
    public void setSearched(boolean bool) { searched = bool; }

    /**
     * Update the room's haunted state
     * @param bool true to be haunted, false otherwise
     */
    public void setHaunted(boolean bool) { haunted = bool; }

    /**
     * Return the room's haunted state
     * @return true if haunted, false otherwise
     */
    public boolean getHaunted() { return haunted; }
}

