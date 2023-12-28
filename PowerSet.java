import java.util.ArrayList;
import java.util.Scanner;


public class PowerSet {
    ArrayList<ArrayList<String>> powerSet= new ArrayList<>();
    int size,number=0;
    int i=0;
    ArrayList<String> universe;

    public PowerSet(ArrayList<String>universe, int size) {
        this.universe=universe;
        this.size = size;
    }

    void setGenerator(int number,int i){
        if(i==size)
            return;
        if(i>=0){
            ArrayList<String>newSet=new ArrayList<>();
            for (int j=universe.size();j>=0;j--){
                if(((number>>j)&1) == 1){
                    newSet.add(universe.get(universe.size()-j-1));
                }
            }
            if(!powerSet.contains(newSet))
                powerSet.add(newSet);
        }
        i++;
        setGenerator(number&(0<<i),i);
        setGenerator(number|(1<<i),i);

    }
    void iterative(int sizeSet, ArrayList<String> universe){
        int sizePowerset = (int) Math.pow(2, sizeSet);

        for(int i=0 ; i<sizePowerset ; i++)
        {
            System.out.print("{ ");
            for(int j = 0 ; j<sizeSet ; j++)
            {
                int x = i & (1<<j);
                if(x != 0)
                    System.out.print(universe.get(j) + " ");
            }
            System.out.println("}");
        }
    }

}