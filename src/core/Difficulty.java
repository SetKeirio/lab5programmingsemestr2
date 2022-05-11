package core;

/**
 * Source class. Enum with all Difficulty level constants
 */
public enum Difficulty {
    VERY_EASY,
    NORMAL,
    HOPELESS,
    TERRIBLE;

    public static String getNames(){
        String answer = "";
        for (Difficulty d: Difficulty.values()){
            answer += d.name() + ", ";
        }
        return answer.substring(0, answer.length() - 2);
    }
}
