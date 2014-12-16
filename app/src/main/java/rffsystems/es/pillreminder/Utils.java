package rffsystems.es.pillreminder;

/**
 * Created by Ruben on 12/12/2014.
 */
public class Utils {

    public static int getHour(String t)
    {
        int index = t.indexOf(":");

        String res = (String)  t.subSequence(0,index);

        return Integer.valueOf(res);
    }

    public static int getMinutes(String t)
    {
        int longitud= t.length();
        int index = t.indexOf(":");

        String res = (String)  t.subSequence(index+1,longitud);

        return Integer.valueOf(res);
    }

}
