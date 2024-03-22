package cse143exercises.ProgrammingClubChallenges;

public class RandomTest {
    public static void main(String[] args) {
        RandomTest test = new RandomTest();
        //test.spacey(40, 'a', 'b');
    
    }

    public void spacey(int num, char letter1, char letter2) {
        for (int i = 0; i < num*2; i++) { 
            String space = " ";
            if (i < num) {
                System.out.println(space.repeat(i) + letter1 + space.repeat(2*(num-i-1)) + letter2 + space.repeat(i));
            } else {
                System.out.println(space.repeat(2*num-i-1) + letter2 + space.repeat(2*(i-num)) + letter1 + space.repeat(2*num-i-1));
            }
        }
    }
}
