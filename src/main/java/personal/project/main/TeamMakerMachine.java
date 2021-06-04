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
import java.io.IOException;
import java.util.*;

public class TeamMakerMachine {
    private HashMap<String, List<Player>> talentList;
    private List<Player> players;
    private boolean hasPostions;
    private int numberOfTeams;
    private MinHeap<Team> teams;

    public TeamMakerMachine(GuiSheet guiSheet) throws IOException {
        if(guiSheet.hasPostions()){
            this.talentList = new HashMap<>();
            hasPostions = true;
        }else{
            this.players = new ArrayList<>();
            hasPostions = false;
        }
        createPlayers(guiSheet.getFile());
        this.numberOfTeams = guiSheet.getNumber();
        this.teams = new MinHeapImpl<>();
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

    private void getPlayersAsMap(Iterator<Row> rows){
        int rowIndex =1;
        label1: while (rows.hasNext()) {
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            int cellIndex = 1;
            Player p = new Player();
            while (cellsInRow.hasNext()) {
                Cell cell = cellsInRow.next();
                if(rowIndex == 1 && cell.getStringCellValue().startsWith("position")){
                    rowIndex++;
                    continue label1;
                }
                if (cellIndex == 1) {
                    p.setPosition(cell.getStringCellValue().toLowerCase());
                } else if(cellIndex == 2) {
                    p.setName(cell.getStringCellValue());
                } else{
                    p.setSkillLevel(cell.getNumericCellValue());
                }
                cellIndex++;
            }
            if (talentList.get(p.getPosition()) == null) {
                List<Player> talentLevel = new ArrayList<>();
                talentLevel.add(p);
                talentList.put(p.getPosition(),talentLevel);
            }else{
                talentList.get(p.getPosition()).add(p);
            }
        }
    }

    private void getPlayersAsList(Iterator<Row> rows) throws IOException {
        int rowIndex =1;
        label1: while (rows.hasNext()) {
            Row currentRow = rows.next();
            Iterator<Cell> cellsInRow = currentRow.iterator();
            int cellIndex = 1;
            Player p = new Player();
            while (cellsInRow.hasNext()) {
                Cell cell = cellsInRow.next();
                if(rowIndex == 1 && cellIndex ==1 && cell.getStringCellValue().startsWith("name")){
                    rowIndex++;
                    continue label1;
                }
                if(cellIndex == 1) {
                    p.setName(cell.getStringCellValue());
                } else{
                    p.setSkillLevel(cell.getNumericCellValue());
                }
                cellIndex++;
            }
            players.add(p);
        }
    }

    public void printTeamsWithoutPostions() {
        for(int i = 1; i < numberOfTeams+1; i++){
            teams.insert(new Team("team " + (i)));
        }
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
        while(!teams.isEmpty()){
            Team t = this.teams.remove();
            System.out.println(t.getUserFriendlyTeams());
            System.out.println(t.getTalentLevel());
            System.out.println();
            System.out.println("work");
        }
    }
}
