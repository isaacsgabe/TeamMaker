package personal.project.main;


import ui.isaacs.gabe.GuiPractice.GuiSheet;

import java.io.IOException;

public class TeamMaker {
    public static void main(String[] args) throws IOException, InterruptedException {
        //get players as a set of the Player class. Check
        //sort players by compareTo (best being at beginning, worst being at end) and return as a SortedList. Check.
        //get the number of teams as an int. Check.
        //loop through list from best to worst, putting players on teams. Check.
        //create new txt file, adding each team name with players, and saving as a text file
        //email text file to email put in form
        GuiSheet userInterface = new GuiSheet();
        while (userInterface.getNumber() == Integer.MIN_VALUE || userInterface.getFile() == null || !userInterface.getFile().getName().endsWith(".xlsx")) {
            Thread.sleep(1000);
        }
        TeamMakerMachine teamMaker = new TeamMakerMachine(userInterface);
        teamMaker.printTeamsWithoutPositions();
    }
}