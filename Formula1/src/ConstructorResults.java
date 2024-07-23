public class ConstructorResults {

    private int constructorResultId;
    private int raceId;
    private int constructorId;
    private double points;
    private String status;
    
    

    public ConstructorResults(int constructorResultId, int raceId, int constructorId, double points, String status) {
        this.constructorResultId = constructorResultId;
        this.raceId = raceId;
        this.constructorId = constructorId;
        this.points = points;
        this.status = status;
    }

    public int getConstructorResultId() {
        return constructorResultId;
    }

    public void setConstructorResultId(int constructorResultId) {
        this.constructorResultId = constructorResultId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getConstructorId() {
        return constructorId;
    }

    public void setConstructorId(int constructorId) {
        this.constructorId = constructorId;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
}
