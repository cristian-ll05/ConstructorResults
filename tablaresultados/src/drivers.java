public class drivers {
    private int id;
    private String name;
    private String team;
    private int wins;
    private int points;
    private int position;
    private int year;

    public drivers(int id, String name, String team, int wins, int points, int position, int year) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.wins = wins;
        this.points = points;
        this.position = position;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}