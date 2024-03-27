public class PigLatin {

    public void toPigLatin(String english) {
        String[] words = english.split(" ");
        String[] newWords = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            newWords[i] = words[i].substring(1) + "ay";
            if (getSpecificChar(words[i]) != null) {
                newWords[i] = words[i].substring(1, words[i].length()-1) + "ay" + getSpecificChar(words[i]);
            }
            System.out.printf("%s\s", newWords[i]);
        }
    }

    public void fromPigLatin(String pigLatin) {
        String[] words = pigLatin.split(" ");
        String[] newWords = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            newWords[i] = words[i].substring(1);
            if (getSpecificChar(words[i]) != null) {
                newWords[i] = words[i].substring(0, words[i].length()-3) + getSpecificChar(words[i]);
            }
            System.out.printf("%s\s", newWords[i].substring(0, words[i].length()-3));
        }
    }

    public String getSpecificChar(String input) {
        String[] types = {",", ";", "?", "!", "."};
        for (String type : types) {
            if (input.contains(type)) {
                return type;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        PigLatin pl = new PigLatin();
        //pl.toPigLatin("hello, world!");
        pl.fromPigLatin("elloay");
    }
}