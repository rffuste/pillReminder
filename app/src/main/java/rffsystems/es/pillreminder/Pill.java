package rffsystems.es.pillreminder;

import java.util.List;

/**
 * Created by Ruben on 30/11/2014.
 */
public class Pill {

    private long id;
    private String pillName;
    private String pillTime;
    private int pillDosis;


    public Pill(String pillName, String pillTime, int pillDosis) {
        this.pillName = pillName;
        this.pillTime = pillTime;
        this.pillDosis = pillDosis;
    }

    public Pill()
    {

    }

    public Pill(long id, String pillName, String pillTime, int pillDosis) {
        this.id = id;
        this.pillName = pillName;
        this.pillTime = pillTime;
        this.pillDosis = pillDosis;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public String getPillTime() {
        return pillTime;
    }

    public void setPillTime(String pillTime) {
        this.pillTime = pillTime;
    }

    public int getPillDosis() {
        return pillDosis;
    }

    public void setPillDosis(int pillDosis) {
        this.pillDosis = pillDosis;
    }


    //Used by ArrayAdapter on main pillList
    @Override
    public String toString() {
        return
               pillName;
    }
}
