import java.util.Random;
import java.util.ArrayList;
import java.util.Stack;
/**
 *  This class is the main class of the "Ghostbusters" application.
 *  "ghostbusters" is a very simple, text based adventure game.  Users
 *  must explore a haunted mansion to find and capture ghosts to win the game.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game
{
    private Parser parser;
    private ArrayList<Room> rooms;
    private ArrayList<Ghost> ghosts;
    private Room currentRoom;
    private int ghostsCaptured;
    private int ghostsToCapture;
    private Random randomGenerator;
    private Stack<Room> roomStack;

        
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        parser = new Parser();
        rooms = new ArrayList<Room>();
        ghosts = new ArrayList<Ghost>();
        ghostsCaptured = 0;
        ghostsToCapture = 5;
        randomGenerator = new Random();
        createRooms();
        createGhosts();
        roomStack = new Stack<Room>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room foyer, diningHall, library, childsRoom, kitchen, ballroom, gallery, basement, gameRoom;
      
        // create the rooms
        foyer = new Room("foyer of the spooky mansion");
        diningHall = new Room("in the dining hall");
        library = new Room("in the family library");
        childsRoom = new Room("in a child's bedroom");
        kitchen = new Room("in the kitchen");
        ballroom = new Room("in the ballroom");
        gallery = new Room("in the gallery");
        basement = new Room("in the basement");
        gameRoom = new Room("in the game room");

        // initialise room exits
        foyer.setExit("east", diningHall);
        foyer.setExit("south", kitchen);
        foyer.setExit("west", library);

        diningHall.setExit("south", ballroom);
        diningHall.setExit("west", foyer);

        library.setExit("east", foyer);
        library.setExit("south", childsRoom);

        childsRoom.setExit("north", library);
        childsRoom.setExit("east", gallery);

        kitchen.setExit("north", ballroom);
        kitchen.setExit("west", basement);

        ballroom.setExit("north", diningHall);
        ballroom.setExit("south", kitchen);
        ballroom.setExit("west", gallery);

        gallery.setExit("north", foyer);
        gallery.setExit("east", ballroom);
        gallery.setExit("south", basement);
        gallery.setExit("west", childsRoom);

        basement.setExit("north", gallery);
        basement.setExit("east", kitchen);
        basement.setExit("west", gameRoom);

        gameRoom.setExit("north", childsRoom);
        gameRoom.setExit("east", basement);

        rooms.add(foyer);
        rooms.add(diningHall);
        rooms.add(library);
        rooms.add(childsRoom);
        rooms.add(kitchen);
        rooms.add(ballroom);
        rooms.add(gallery);
        rooms.add(basement);
        rooms.add(gameRoom);

        currentRoom = foyer;  // start game in the foyer
    }

    /**
     * Create ghosts and distribute them among the rooms.
     */
    private void createGhosts() {
        for (int i=0; i<ghostsToCapture; i++) {
            Ghost ghostI = new Ghost();
            moveGhostRandom(ghostI);
            ghosts.add(ghostI);
        }
    }

    /**
     *  Main play routine. Loops until all ghosts are captured.
     */
    public void play()
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        if (ghostsCaptured == ghostsToCapture) {
            System.out.println("Congratulations! You've captured all of the");
            System.out.println("ghosts and restored peace to the mansion and");
            System.out.println("souls which now rest. Until next time, you");
            System.out.println("know who to call... ;)");
        }

        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Ghostbusters!");
        System.out.println("You have been asked to rid a haunted mansion of undead spirits.");
        System.out.println("Explore the mansion to uncover and capture 5 ghosts to win the game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        boolean allGhostsCaptured = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("search")) {
            currentRoom.search();
        }
        else if (commandWord.equals("capture")) {
            captureAttempt();
            if (ghostsCaptured == ghostsToCapture) {
                allGhostsCaptured = true;
            }
        }
        else if (commandWord.equals("back")) {
            goRoom(new Command("go", "back"));
        }
        // else command not recognised.
        return (wantToQuit || allGhostsCaptured);
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a short message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are a Ghostbuster. Find the ghosts and");
        System.out.println("bring peace to the mansion.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to move to a specific direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * When a room is left, its searched state reverts to unsearched.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = null;

        // Try to leave current room.
        if (direction.equals("back")) {
            if (roomStack.size() == 0) {
                System.out.println("You cannot go further back. Try going forward!");
                return;
            } else {
                nextRoom = roomStack.pop();
            }
        } else {
            nextRoom = currentRoom.getExit(direction);
            roomStack.push(currentRoom);
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom.setSearched(false);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Move a ghost to a random unhaunted room
     * @param ghost the ghost to move
     */
    private void moveGhostRandom(Ghost ghost) {
        Room roomToHaunt;
        int roomIndex = -1;

        do {
            roomIndex = randomGenerator.nextInt(rooms.size() - 1);
        } while (rooms.get(roomIndex).getHaunted());

        roomToHaunt = rooms.get(roomIndex);
        ghost.setCurrentRoom(roomToHaunt);
        roomToHaunt.setHaunted(true);
        ghost.setHidden(true);
    }

    /**
     * Attempt to capture a ghost. If the room has not been searched or if it
     * is not haunted, the attempt is unsuccessful. Not all attempts to capture
     * are successful, and if the player fails once, the ghost is moved to
     * another room at random and the room is no longer haunted.
     * @return true if the ghost is successfully captured, false otherwise
     */
    private boolean captureAttempt() {
        if (currentRoom.getSearched() && currentRoom.getHaunted()) {
            for (Ghost ghost : ghosts) {
                if (ghost.getCurrentRoom().equals(currentRoom)) {
                    if (ghost.capture()) {
                        ghost.setCaptured(true);
                        ghost.setCurrentRoom(null);
                        currentRoom.setHaunted(false);
                        ghostsCaptured++;

                        System.out.println("Nice job! You snagged the ghost!");
                        System.out.println(ghostsCaptured + " down.");
                        System.out.println((ghostsToCapture - ghostsCaptured) + " to go...");

                        return true;
                    } else {
                        moveGhostRandom(ghost);
                        currentRoom.setHaunted(false);

                        System.out.println("The spirit escaped before you could capture it!");
                        System.out.println("You'll have to keep searching and try again.");
                        return false;
                    }
                }
            }
        } else {
            System.out.println("This room doesn't appear to be haunted.");
            System.out.println("Have you searched this room yet?");
            return false;
        }
        return false;
    }


}
