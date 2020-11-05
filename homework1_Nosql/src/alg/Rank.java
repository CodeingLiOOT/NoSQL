package alg;

import java.util.Arrays;

public class Rank {
    public static String[] reRank(String[] input) {
        if (input == null) {
            return null;
        }
        int r = 0, b = 0, g = 0;
        for (String c :
                input) {
            if (c.equals("r")) {
                r++;
            } else if (c.equals("b")) {
                b++;
            } else {
                g++;
            }
        }
        String[] ans=new String[r+b+g];


        return new String[0];
    }
    public static String[] reRankPointers(String[] input){
        if(input==null||input.length==0){
            return new String[0];
        }
        int i=0;
        int left=0;
        int right=input.length-1;
        while(i<=right){
            if(input[i].equals("r")){
                String t1=input[i];
                input[i]=input[left];
                input[left]=t1;
                i++;
                left++;

            }else if(input[i].equals("g")){
                i++;

            }else if(input[i].equals("b")){
                String t1=input[i];
                input[i]=input[right];
                input[right]=t1;
                right--;

            }
        }
        return input;
    }

    public static void main(String[] args) {
        String[] input = new String[]{"r", "b", "g", "r", "b", "b", "g"};
        //input = reRank(input);
        //input=reRankPointers(input);
        Arrays.asList(reRankPointers(input)).forEach((f)->{
            System.out.print(f);
        });

    }
}
