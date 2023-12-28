import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

interface Expression {
    String getRepresentation();
    void setRepresentation(String representation);
}
interface LogicalExpressionSolver {
    boolean evaluateExpression(Expression expression);
}
public class ExpressionSolver implements Expression,LogicalExpressionSolver{
    Stack<Character> operators = new Stack<>();
    Stack<Boolean> operands = new Stack<>();
    String representation;
    ArrayList<Character> literals= new ArrayList<>();
    ArrayList<Boolean>values= new ArrayList<>();
    int flag=0;
    @Override
    public String getRepresentation() {
        return this.representation;
    }

    @Override
    public void setRepresentation(String representation) {
        this.representation=representation;
    }
    void settingStacks(String representation){
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        int bracketopen=0;
        int bracketclose=0;

        //this.representation = representation.toUpperCase();
        char []arr = this.representation.toUpperCase().toCharArray();
        for (int i=0;i<arr.length;i++){
            if(Character.isAlphabetic(arr[i]) && arr[i]!='v' && arr[i]!='V'){
                if (!literals.contains(arr[i])){
                    literals.add(arr[i]);
                }
            }else if( arr[i]=='('){
                bracketopen++;
            }else if( arr[i]==')'){
                bracketclose++;
            }else if( arr[i]=='~' ||arr[i]=='v' || arr[i]=='^' || arr[i]=='>'|| arr[i]=='V'){
                if(i< arr.length-1){
                    if ( arr[i+1]=='v' || arr[i+1]=='^' || arr[i+1]=='>'|| arr[i+1]=='V'){
                        this.flag=1;
                    }
                }
            }else{
                this.flag=1;
            }
        }
        if (bracketopen!=bracketclose){
            this.flag=1;
        }
        Scanner in = new Scanner(System.in);
        for(int i=0;i<literals.size();i++){
            System.out.println("Enter INTEGER value of "+literals.get(i) +"(0 for FALSE, otherwise for TRUE)");
            int v= in.nextInt();
            if (v==0)
                values.add(i,false);
            else
                values.add(i,true);
        }
    }
    @Override
    public boolean evaluateExpression(Expression expression) {
        this.settingStacks(this.getRepresentation());
        //this.representation=this.representation.toUpperCase();
        char []arr = this.representation.toUpperCase().toCharArray();
        if (this.flag==1){
            return false;
        }

        for (int i=0;i<arr.length;i++){
            //System.out.println(arr[i]);
            if (Character.isAlphabetic(arr[i]) && arr[i]!='v' && arr[i]!='V'){
                int index= literals.indexOf(arr[i]);
                operands.push(values.get(index));
            } else if (arr[i]=='(') {
                operators.push((Character) arr[i]);
            } else if (arr[i]==')') {
                int closed=0;
                while (!operators.isEmpty()){

                    Character ch = operators.pop();
                    if (ch=='~'){
                        operands.push(!operands.pop());
                    }
                    else if(ch=='V'){
                        operands.push(operands.pop() | operands.pop());
                    }else if(ch=='^'){
                        operands.push(operands.pop() & operands.pop());
                    }else if(ch=='>'){
                        operands.push(operands.pop() | !operands.pop());
                    } else if (ch=='(') {
                        closed=1;
                        break;
                    }
                }
                if (operators.isEmpty() && closed==0){
                    this.flag=1;
                    break;
                }

            } else if (arr[i]=='~') {
                while (!operators.isEmpty() && !operands.isEmpty() && operators.peek()=='~'){
                    operands.push(!operands.pop());
                }
                operators.push((Character) arr[i]);
            } else if (arr[i]=='^') {
                while (!operators.isEmpty() && (operators.peek()=='~' || operators.peek()=='^')){
                    if (operators.peek()!='(') {
                        Character ch = operators.pop();
                        if (ch == '~') {
                            operands.push(!operands.pop());
                        } else if (ch == '^') {
                            operands.push(operands.pop() & operands.pop());
                        }
                    }
                }
                operators.push((Character) arr[i]);
            }else if (arr[i]=='V') {
                while (!operators.isEmpty() && operators.peek()!='>'){
                    if (operators.peek()!='(') {
                        Character ch = operators.pop();
                        if (ch == '~') {
                            operands.push(!operands.pop());
                        } else if (ch == 'V') {
                            operands.push(operands.pop() | operands.pop());
                        } else if (ch == '^') {
                            operands.push(operands.pop() & operands.pop());
                        }
                    }else{ break;}
                }
                operators.push((Character) arr[i]);
            } else if (arr[i]=='>') {

                while (!operators.isEmpty()){
                    if (operators.peek()!='(') {
                        Character ch = operators.pop();
                        if (ch == '~') {
                            operands.push(!operands.pop());
                        } else if (ch == 'V') {
                            operands.push(operands.pop() | operands.pop());
                        } else if (ch == '^') {
                            operands.push(operands.pop() & operands.pop());
                        } else if (ch == '>') {
                            operands.push(operands.pop() | !operands.pop());
                        }
                    }else {break;}
                }
                operators.push((Character) arr[i]);
            }
        }

        while (!operators.isEmpty()){

            while (!operators.isEmpty()){
                Character ch = operators.pop();
                if (ch=='~'){
                    operands.push(!operands.pop());
                }else if(ch=='^'){
                    operands.push(operands.pop() & operands.pop());
                }else if(ch=='V'){
                    operands.push(operands.pop() | operands.pop());
                }else if(ch=='>'){
                    operands.push(operands.pop() | (!operands.pop()));
                }
            }
        }
        if(!operands.isEmpty() && operands.size()==1)
            return operands.pop();
        else {
            flag=1;
            return false;
        }
    }


}