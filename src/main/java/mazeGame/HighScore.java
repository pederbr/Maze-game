package mazeGame;

public class HighScore {
    private String name;
    private int difficulty;
    private long time;
    private long achievement;

    public HighScore(String name, int difficulty, long time) {
        this.name = name;
        this.difficulty = difficulty;
        this.time = time;
        this.achievement = difficulty * difficulty * 100 - time;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public long getTime() {
        return time;
    }

    public long getAchievement() {
        return achievement;
    }
    
    //formaterrer for CSV
    public String toCSV() {
        return name + "," + difficulty + "," + time+ "," + achievement;
    }

    @Override
    public String toString() {
        return "HighScore [name=" + name + ", difficulty=" + difficulty + ", time=" + time + "]";
    }
}
