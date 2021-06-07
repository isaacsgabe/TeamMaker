package personal.project.main;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class TeamMakerMachineTest {

    public TeamMakerMachine getInfo() throws IOException {
        File file = new File("C:\\Users\\gabei\\Documents\\personal git\\excel files\\book1.xlsx");
        TeamMakerMachine gabe = new TeamMakerMachine(file,false);
        return gabe;
    }
    @Test
    public void Test1() throws IOException {
        TeamMakerMachine gabe = getInfo();
        gabe.printTeamsWithoutPositions();

    }

}