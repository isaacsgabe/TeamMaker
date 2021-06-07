package personal.project.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TeamMakerMachineTest {

    public TeamMakerMachine getInfo() throws IOException {
        File file = new File("C:\\Users\\gabei\\Documents\\personal git\\excel files\\book2.xlsx");
        TeamMakerMachine gabe = new TeamMakerMachine(file,true);
        return gabe;
    }
    @Test
    public void Test1() throws IOException {
        TeamMakerMachine gabe = getInfo();
        gabe.printTeamsWithPosition();

    }

}