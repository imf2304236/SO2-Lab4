import java.util.Random;
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
    private Room currentRoom;
    private int ghostsCaptured;
    private Random randomGenerator;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
        ghostsCaptured = 0;
        randomGenerator = new Random();
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


        currentRoom = foyer;  // start game in the foyer
    }

    /**
     *  Main play routine.  Loops until end of play.
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
        System.out.println("Thank you for playing.  Good bye."); // TODO: 17/05/17 Change finished message 
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
        } else if (commandWord.equals("search")) {
            currentRoom.search();
        } else if (commandWord.equals("capture")) {
            captureGhost();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            currentRoom.setSearched(false);
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

    private boolean captureGhost()
    {
        if (currentRoom.getHaunted()) {
            int random = randomGenerator.nextInt(50);

            if (random >= 0 && random <= 10) {
                currentRoom.setHaunted(false);
                ghostsCaptured++;
                System.out.println("Nice job! You snagged the ghost!");

                if (ghostsCaptured == 5) {
                    return true;
                } else {
                    return false;
                }
            } else {
                currentRoom.setHaunted(false);
                System.out.println("The spirit escaped before you could capture it!");
                System.out.println("You'll have to keep searching and try again.");
                return false;
            }
        } else {
            System.out.println("This room doesn't appear to be haunted.");
            System.out.println("Have you searched this room yet?");
            return false;
        }
    }
}
