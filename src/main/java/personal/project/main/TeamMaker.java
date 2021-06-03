package personal.project.main;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import personal.project.objects.Player;
import personal.project.objects.Team;
import ui.isaacs.gabe.GuiPractice.guiSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class TeamMaker {
    private static guiSheet userInterface = null;
    public static void main(String[] args) throws IOException, InterruptedException {
        //get players as a set of the Player class. Check
        //sort players by compareTo (best being at beginning, worst being at end) and return as a SortedList. Check.
        //get the number of teams as an int. Check.
        //loop through list from best to worst, putting players on teams. Check.
        //create new txt file, adding each team name with players, and saving as a text file
        //email text file to email put in form
        userInterface = new guiSheet();
        while(userInterface.getNumber() == Integer.MIN_VALUE || userInterface.getFile() == null || !userInterface.getFile().getName().endsWith(".xlsx")) {
            Thread.sleep(1000);
        }
        if(!userInterface.hasPostions()){
            List<Player> players = getPlayersAsList(userInterface.getFile());
            Collections.sort(players);
            printTeamsWithoutPostions(players);
        }else{
            Map<String,List<Player>> players = new HashMap<>();
            printTeamsWithPositions(players);
        }
    }

    private static void printTeamsWithPositions(Map<String,List<Player>> players) {

    }

    private static void printTeamsWithoutPostions(List<Player> players) {
        int teamNumber = userInterface.getNumber();
        List<Team> teams = new LinkedList<>();
        for(int i = 0; i < teamNumber; i++){
            teams.add(new Team("team " + (i+ 1)));
        }
        int counter = 0;
        while(counter != players.size()){
            for(Team t: teams){
                Player p = players.get(counter);
                t.addPlayer(p);
                counter++;
                if(counter == players.size()){
                    break;
                }
            }
            Collections.reverse(teams);
        }
        for(Team t: teams){
            System.out.println(t.getUserFriendlyTeams());
            System.out.println();
        }
    }


    private static List<Player> getPlayersAsList(File file) throws IOException {
        FileInputStream getInformation = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(getInformation);
        Sheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        List<Player> players = new ArrayList<>();
        int rowIndex =1;
        label1: while (rows.hasNext()) {
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            int cellIndex = 1;
            Player p = new Player();
            while (cellsInRow.hasNext()) {
                Cell cell = cellsInRow.next();
                if(userInterface.hasPostions()){
                    if(rowIndex == 1 && (cell.getStringCellValue().startsWith("position")|| cell.getStringCellValue().startsWith("name"))){
                        rowIndex++;
                        continue label1;
                    }
                    if (cellIndex == 1) {
                        p.setPosition(cell.getStringCellValue());
                    } else if(cellIndex == 2) {
                        p.setName(cell.getStringCellValue());
                    } else{
                        p.setSkillLevel(cell.getNumericCellValue());
                    }
                }else{
                    if(rowIndex == 1 && (cell.getStringCellValue().startsWith("position")|| cell.getStringCellValue().startsWith("name"))){
                        rowIndex++;
                        continue label1;
                    }
                    if(cellIndex == 1) {
                        p.setName(cell.getStringCellValue());
                    } else{
                        p.setSkillLevel(cell.getNumericCellValue());
                    }
                }
                cellIndex++;
            }
            players.add(p);
        }
        workbook.close();
        return players;
    }
}