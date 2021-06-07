package personal.project.main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import personal.project.objects.*;
import ui.isaacs.gabe.GuiPractice.GuiSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TeamMakerMachine {
    private HashMap<String, List<Player>> talentList;
    private List<Player> players;
    private final boolean hasPostions;
    private final int numberOfTeams;
    private final MinHeap<Team> teams;
    private GuiSheet guiSheet;
    public TeamMakerMachine(GuiSheet guiSheet) throws IOException {
        if (guiSheet ==null){
            throw new IllegalArgumentException();
        }
        this.guiSheet= guiSheet;
        if(this.guiSheet.hasPostions()){
            this.talentList = new HashMap<>();
            hasPostions = true;
        }else{
            this.players = new ArrayList<>();
            hasPostions = false;
        }
        createPlayers(guiSheet.getFile());
        this.numberOfTeams = guiSheet.getNumber();
        this.teams = new MinHeapImpl<>();
        for(int i = 1; i < numberOfTeams+1; i++){
            teams.insert(new Team("team " + (i)));
        }
    }

    public TeamMakerMachine(File file,boolean b) throws IOException {
        this.teams = new MinHeapImpl<>();
        if(b){
            this.talentList = new HashMap<>();
            hasPostions = true;
        }else{
            this.players = new ArrayList<>();
            hasPostions = false;
        }
        createPlayers(file);
        this.numberOfTeams = 4;
        for(int i = 1; i <= numberOfTeams; i++){
            teams.insert(new Team("team " + (i)));
        }
    }

    public void createPlayers(File file) throws IOException {
        FileInputStream getInformation = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(getInformation);
        Sheet sheet = workbook.getSheet("Sheet1");
        if(this.hasPostions){
            getPlayersAsMap(sheet.iterator());
        }else{
            getPlayersAsList(sheet.iterator());
        }
        workbook.close();
    }

    private void getPlayersAsMap(Iterator<Row> rows) throws IOException {
        String exception = "";
        int rowIndex = 0;
        label1: while (rows.hasNext()) {
            rowIndex++;
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            int cellIndex = 1;
            Player p = new Player();
            while (cellsInRow.hasNext()) {
                Cell cell = cellsInRow.next();
                try {
                    if (rowIndex == 1 && cell.getStringCellValue().startsWith("position")) {
                        continue label1;
                    }
                }catch (IllegalStateException e){
                    exception ="there is a number in "  + (char)(cell.getColumnIndex()+65) + (cell.getRowIndex()+1);
                    createFile(exception);
                    System.exit(0);
                }
                try {
                    if (cellIndex == 1) {
                        p.setPosition(cell.getStringCellValue().toLowerCase());
                    } else if (cellIndex == 2) {
                        p.setName(cell.getStringCellValue());
                    }
                }catch (IllegalStateException e){
                        exception = "there is a number in "  + (char)(cell.getColumnIndex()+65) + rowIndex;
                        createFile(exception);
                        System.exit(0);
                    }
                try{
                    if(cellIndex != 1 && cellIndex !=2) {
                        p.setSkillLevel(cell.getNumericCellValue());
                    }
                }catch (IllegalStateException e){
                    exception = "there is a character in "  + (char)(cell.getColumnIndex()+65) + rowIndex;
                    createFile(exception);
                    System.exit(0);
                }
                    cellIndex++;
                    if (talentList.get(p.getPosition()) == null) {
                        List<Player> talentLevel = new ArrayList<>();
                        talentLevel.add(p);
                        talentList.put(p.getPosition(), talentLevel);
                    } else {
                        talentList.get(p.getPosition()).add(p);
                    }
            }

        }
    }

    private void getPlayersAsList(Iterator<Row> rows) throws IOException {
        String exception = "";
        int rowIndex = 0;
        label1: while (rows.hasNext()) {
            rowIndex++;
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            int cellIndex = 1;
            Player p = new Player();
            while (cellsInRow.hasNext()) {
                Cell cell = cellsInRow.next();
                try {
                    if (rowIndex == 1 && cell.getStringCellValue().startsWith("name")) {
                        continue label1;
                    }
                }catch (IllegalStateException e){
                    exception ="there is a number in "  + (char)(cell.getColumnIndex()+65) + (cell.getRowIndex()+1);
                    createFile(exception);
                    System.exit(0);
                }
                try{
                    if(cellIndex == 1) {
                        p.setName(cell.getStringCellValue());
                    }
                }catch (IllegalStateException e){
                    exception ="there is a number in "  + (char)(cell.getColumnIndex()+65) + (cell.getRowIndex());
                    createFile(exception);
                    System.exit(0);
                }
                try{
                    if(cellIndex != 1){
                        p.setSkillLevel(cell.getNumericCellValue());
                    }
                }catch (IllegalStateException e){
                    exception = "there is a character in "  + (char)(cell.getColumnIndex()+65) + rowIndex;
                    createFile(exception);
                    System.exit(0);
                }
                cellIndex++;
            }
            players.add(p);
        }
    }

    public void printTeamsWithoutPositions() throws IOException {
        int counter = 0;
        Collections.sort(players);
        while(counter != players.size()){
            for(Player p: this.players){
                Team head = this.teams.remove();
                head.addPlayer(p);
                this.teams.insert(head);
                counter++;
            }
        }
        createFile(getTeamsPrinted());
    }
     public void printTeamsWithPosition() throws IOException {
        Set<String> positions = this.talentList.keySet();
        for(String s: positions){
            List<Player> positionPlayers = this.talentList.get(s);
            Collections.sort(positionPlayers);
            Set<Team> oldTalentLevel = new HashSet<>();
            while(!positionPlayers.isEmpty()){
                Team team = this.teams.min();
                double z =Math.ceil(positionPlayers.size()/(double)this.numberOfTeams);
                while(team.playersPerPosition(s) >= z){
                    team.setTalentLevel(Double.MAX_VALUE);
                    this.teams.reHeapify(team);
                    oldTalentLevel.add(team);
                    team = this.teams.min();
                }
                team.addPlayer(positionPlayers.remove(0));
                this.teams.reHeapify(team);
            }
            for(Team team: oldTalentLevel){
                team.setTalentLevel(team.getPreviousTalentLevel());
                this.teams.reHeapify(team);
            }
        }
        createFile(getTeamsPrinted());
     }

     private void createFile(String string) throws IOException {
         String currentDirectory = System.getProperty("user.dir");
         File dir = new File(currentDirectory + File.separator + "TeamFolder");
         dir.mkdir();
         String pathWithFile = dir + File.separator + "teams.txt";
         File info = new File(pathWithFile);
         if(info.exists()){
             info.delete();
         }
         FileWriter fileWriter = new FileWriter(info);
         fileWriter.write(string);
         fileWriter.close();
     }

     private String getTeamsPrinted() throws IOException {
        String string = "";
         while(!teams.isEmpty()){
             Team t = this.teams.remove();
             string += t.getUserFriendlyTeams();
             string += "\n " + t.getTalentLevel() + "\n";
         }
         createFile(string);
         return string;
     }
}
