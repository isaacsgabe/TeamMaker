package personal.project.objects;
import java.util.*;

public class Team implements Comparable<Team>{
    private String name;
    private Set<Player> players;
    private boolean hasPositions;
    private Set<String> positions;
    private double talentLevel;
    private double previousTalentLevel;
    private HashMap<String,Integer> playersPerPosition;

    public Team(String name) {
        this.name=name;
        this.players=new HashSet<>();
        this.positions = new HashSet<>();
        this.playersPerPosition = new HashMap<>();
    }
    public void addPlayer(Player p) {
        this.players.add(p);
        this.talentLevel += p.getSkillLevel();
        if(hasPositions){
            Integer amount = this.playersPerPosition.get(p.getPosition());
            if(amount == null){
                this.playersPerPosition.put(p.getPosition(),1);
            }else{
                playersPerPosition.put(p.getPosition(),amount +1);
            }
        }
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

    public void setHasPositions(boolean hasPositions) {
        this.hasPositions = hasPositions;
    }

    public double getTalentLevel() {
        return talentLevel;
    }

    public void setTalentLevel(Double d) {
        this.previousTalentLevel = this.talentLevel;
         this.talentLevel = d;
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

    public double getPreviousTalentLevel() {
        return previousTalentLevel;
    }

    public int playersPerPosition(String s){
        if(this.playersPerPosition.get(s) == null){
            return 0;
        }
        return this.playersPerPosition.get(s);
    }
}
