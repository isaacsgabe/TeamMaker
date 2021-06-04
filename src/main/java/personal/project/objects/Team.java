package personal.project.objects;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team implements Comparable<Team>{
    private String name;
    private Set<Player> players;
    private boolean hasPositions;
    private Set<String> positions;
    private double talentLevel;

    public Team(String name) {
        this.name=name;
        this.players=new HashSet<>();
        this.positions = new HashSet<>();
    }
    public void addPlayer(Player p) {
        this.players.add(p);
        this.talentLevel += p.getSkillLevel();
    }
    public String getName(){
        return this.name;
    }

    public List<String> getRoster() {
        List<String> roster = new ArrayList<>();
        for (Player p : players) {
            roster.add(p.getName());
        }
        roster.sort(String::compareTo);
        return roster;
    }
    public int getTeamSize(){
        return this.players.size();
    }

    public String getUserFriendlyTeams(){
        String teams = null;
        teams = this.name +"\n";
        teams += this.getRoster().toString();
        return teams;
    }

    public boolean isHasPositions() {
        return hasPositions;
    }

    public void setHasPositions(boolean hasPositions) {
        this.hasPositions = hasPositions;
    }

    public double getTalentLevel() {
        return talentLevel;
    }

    @Override
    public int compareTo(Team o) {
        if(o == null){
            throw new IllegalArgumentException();
        }
        if(this.talentLevel>o.talentLevel){
            return 1;
        }else if(o.talentLevel > this.talentLevel){
            return -1;
        }else{
            return 0;
        }
    }
}
