import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String getString(){
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        string=string.replaceAll("\\s+","");
        return string;
    }

    public static void main(String[] args) {

        Scanner input=new Scanner(System.in);
        Scanner input2=new Scanner(System.in);
        System.out.println("Choose on of the following:\n1) Power Set.\n2) Expression Solver.\n3) Inference Solver/Engine.\nPRESS ANY CHARACTER TO EXIT.");
        int x = input.nextInt();
        switch (x){
            case 1:
                System.out.println("PART 1: Power Set");
                System.out.println("Enter size of Universe");
                int size=input.nextInt();
                while(size>32 || size<=0){
                    System.out.println("Re-Enter your size, must be between zero and 64");
                    size=input.nextInt();
                }
                System.out.println("Enter universe elements:");
                String str=input2.nextLine();
                str=str.replaceAll("\\s+","");
                String[]s=str.split(",");
                ArrayList<String> Universe=new ArrayList<>();
                for (String value : s) {
                    if (!Universe.contains(value))
                        Universe.add(value);
                }
                System.out.println("Choose one of the following:\n1) Iterative Method.\n2) Recursive Method.");
                int option = input.nextInt();
                switch (option){
                    case 1:
                        PowerSet powerSet1 = new PowerSet(Universe, size);
                        powerSet1.iterative(Universe.size(), Universe);
                        break;
                    case 2:
                        PowerSet powerSet2=new PowerSet(Universe,size);
                        powerSet2.setGenerator(0,-1);
                        System.out.println(powerSet2.powerSet);
                        break;
                    default:
                        System.out.println("Option is invalid!!");
                }
                break;
            case 2:
                System.out.println("PART 2: Expression Solver");
                ExpressionSolver expression= new ExpressionSolver();
                System.out.println("Enter the expression to evaluate: ");
                expression.setRepresentation(getString());
                Boolean result = expression.evaluateExpression(expression);
                if (expression.flag==1)
                    System.out.println("WRONG EXPRESSION, IT CANNOT BE EVALUATED");
                else
                    System.out.println(expression.representation+" results in << "+result+" >>");
                break;
            case 3:
                System.out.println("PART 3: Inference Solver/Engine");

                InferenceSolver solver= new InferenceSolver();
                InferenceSolver exp1= new InferenceSolver();
                InferenceSolver exp2= new InferenceSolver();

                System.out.println("Enter the first expression to infere: ");
                exp1.setRepresentation(getString());

                System.out.println("Enter the second expression to infere: ");
                exp2.setRepresentation(getString());

                solver.addExpression(exp1);
                solver.addExpression(exp2);

                ModusPonens ponens= new ModusPonens();
                ModusTollens tollens = new ModusTollens();
                DisjunctiveSyllogism dis = new DisjunctiveSyllogism();
                HypotheticalSyllogism hyp = new HypotheticalSyllogism();
                Resolution resolution = new Resolution();


                solver.addRule(ponens);
                solver.addRule(tollens);
                solver.addRule(dis);
                solver.addRule(hyp);
                solver.addRule(resolution);

                if (solver.applyRules().getRepresentation()!=null)
                    System.out.println(solver.applyRules().getRepresentation());
                break;

            default:
                System.out.println("Thank you for using.");
        }
    }
}
