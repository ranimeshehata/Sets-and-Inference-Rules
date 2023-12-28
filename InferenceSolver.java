import java.util.ArrayList;

interface InferenceRule {
    boolean matches(Expression exp1, Expression exp2);
    Expression apply(Expression exp1, Expression exp2);
}
interface InferenceEngine {
    void addRule(InferenceRule rule);
    void addExpression(Expression exp);
    Expression applyRules();
}
class ModusPonens implements InferenceRule {
    String s1, s2;
    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        //code check if they match rule
        s1 = exp1.getRepresentation();
        s2 = exp2.getRepresentation();

        if (s2.length() == 1) {
            if (s1.length() == 3) {
                if (s1.charAt(0) == s2.charAt(0) && s1.charAt(1) == '>' && Character.isAlphabetic(s1.charAt(2)))
                    return true;
            } else if (s1.length() == 4) {
                if (s1.charAt(0) == s2.charAt(0) && s1.charAt(1) == '>' && s1.charAt(2) == '~' && Character.isAlphabetic(s1.charAt(3)))
                    return true;
            }
        } else if (s2.length() == 2) {
            if (s1.length() == 4) {
                if (s1.charAt(0) == s2.charAt(0) && s1.charAt(1) == s2.charAt(1) && s1.charAt(2) == '>' && Character.isAlphabetic(s1.charAt(3)))
                    return true;
            } else if (s1.length() == 5) {
                if (s1.charAt(0) == s2.charAt(0) && s1.charAt(1) == s2.charAt(1) && s1.charAt(2) == '>' && s1.charAt(3) == '~' && Character.isAlphabetic(s1.charAt(4)))
                    return true;
            }
        }
        return false;
    }
    @Override
    public Expression apply (Expression exp1, Expression exp2){
        InferenceSolver exp3 = new InferenceSolver();
        if (s2.length() == 1) {
            if (s1.length() == 3)
                exp3.setRepresentation(String.valueOf(s1.charAt(2)));
            else if (s1.length() == 4) {
                exp3.setRepresentation("~" + s1.charAt(3));
            }
        } else if (s2.length() == 2) {
            if (s1.length() == 4)
                exp3.setRepresentation(String.valueOf(s1.charAt(3)));
            else if (s1.length() == 5) {
                exp3.setRepresentation("~" + s1.charAt(4));
            }
        }

        return exp3;
    }
}

class ModusTollens implements InferenceRule {
    String s1, s2;

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        s1 = exp1.getRepresentation();
        s2 = exp2.getRepresentation();
        if (s2.length() == 1) {
            if (s1.length() == 4) {
                if (Character.isAlphabetic(s1.charAt(0)) && s1.charAt(1) == '>' && s1.charAt(2) == '~' && s1.charAt(3) == s2.charAt(0))
                    return true;
            } else if (s1.length() == 5) {
                if (s1.charAt(0) == s1.charAt(3) && Character.isAlphabetic(s1.charAt(1)) && s1.charAt(2) == '>' && s1.charAt(3) == '~' && s1.charAt(4) == s2.charAt(0))
                    return true;
            }
        } else if (s2.length() == 2 && s2.charAt(0) == '~') {
            if (s1.length() == 3) {
                if (s1.charAt(2) == s2.charAt(1) && s1.charAt(1) == '>' && Character.isAlphabetic(s1.charAt(0)))
                    return true;
            } else if (s1.length() == 4) {
                if (s1.charAt(0) == s2.charAt(0) && Character.isAlphabetic(s1.charAt(1)) && s1.charAt(2) == '>' && s1.charAt(3) == s2.charAt(1))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        InferenceSolver exp3 = new InferenceSolver();
        if (s2.length() == 2) {
            if (s1.length() == 3) {
                exp3.setRepresentation("~" + s1.charAt(0));
            } else if (s1.length() == 4) {
                exp3.setRepresentation(String.valueOf(s1.charAt(1)));
            }
        } else if (s2.length() == 1) {
            if (s1.length() == 4) {
                exp3.setRepresentation("~" + s1.charAt(0));
            } else if (s1.length() == 5) {
                exp3.setRepresentation(String.valueOf(s1.charAt(1)));
            }
        }
        return exp3;
    }
}
class HypotheticalSyllogism implements InferenceRule {

    String s1, s2;

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        s1 = exp1.getRepresentation();
        s2 = exp2.getRepresentation();
        if(s2.length() == 3)
        {
            if(s1.length() == 3){
                if(Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s2.charAt(2)) && s1.charAt(2) == s2.charAt(0) && s1.charAt(1) == '>' && s2.charAt(1) == '>' && s1.charAt(0) != s2.charAt(2))
                    return true;
            } else if (s1.length() == 4) {
                if(Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s2.charAt(2)) && s1.charAt(3) == s2.charAt(0) && s1.charAt(2) == '>' && s2.charAt(1) == '>' && s1.charAt(1) != s2.charAt(2))
                    return true;
            }
        }
        else if (s2.length() == 4) {
            if (s1.length() == 3) {
                if (Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s2.charAt(3)) && s1.charAt(2) == s2.charAt(0) && s1.charAt(1) == '>' && s2.charAt(1) == '>' && s1.charAt(0) != s2.charAt(3))
                    return true;
            } else if (s1.length() == 4) {
                if (Character.isAlphabetic(s1.charAt(0)) && s1.charAt(1) == '>' && s1.charAt(1) == s2.charAt(2) && s1.charAt(2) == '~' && s1.charAt(2) == s2.charAt(0) && s1.charAt(3) == s2.charAt(1) && Character.isAlphabetic(s2.charAt(3)))
                    return true;
                else if (s1.charAt(0) == '~' && s1.charAt(0) == s2.charAt(2) && Character.isAlphabetic(s1.charAt(1)) && s1.charAt(2) == '>' && s1.charAt(2) == s2.charAt(1) && s1.charAt(3) == s2.charAt(0) && Character.isAlphabetic(s2.charAt(3)))
                    return true;
            } else if (s1.length() == 5) {
                if (s1.charAt(0) == '~' && s1.charAt(3) == '~' && s2.charAt(0) == '~' && s1.charAt(2) == '>' && s2.charAt(2) == '>' && s1.charAt(4) == s2.charAt(1) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s2.charAt(3)) && Character.isAlphabetic(s1.charAt(4)))
                    return true;
            }
        } else if (s2.length() == 5) {
            if (s1.length() == 4) {
                if (s1.charAt(1) == '>' && s1.charAt(2) == '~' && s2.charAt(0) == '~' && s2.charAt(2) == '>' && s2.charAt(3) == '~' && Character.isAlphabetic(s1.charAt(0)) && s1.charAt(3) == s2.charAt(1) && Character.isAlphabetic(s1.charAt(3)) && Character.isAlphabetic(s2.charAt(4)))
                    return true;
            } else if (s1.length() == 5) {
                if(s1.charAt(2) == '>' && s1.charAt(2) == s2.charAt(2) && s1.charAt(0) == '~' && s1.charAt(0) == s1.charAt(3) && s1.charAt(0) == s2.charAt(0) && s1.charAt(0) == s2.charAt(3) && s1.charAt(4) == s2.charAt(1) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(4)) && Character.isAlphabetic(s2.charAt(4)))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String s3 = "";
        InferenceSolver exp3 = new InferenceSolver();
        if(s2.length() == 3){
            if(s1.length() == 3){
                if (s1.charAt(2) == s2.charAt(0))
                    s3 = s1.charAt(0) + ">" + s2.charAt(2);
                else if (s1.charAt(0) == s2.charAt(2)) {
                    s3 = s1.charAt(2) + ">" + s2.charAt(0);
                }
            } else if (s1.length() == 4) {
                s3 = "~" + s1.charAt(1) + ">" + s2.charAt(2);
            }
        } else if (s2.length() == 4) {
            if (s1.length() == 3){
                s3 = s1.charAt(0) + ">~" + s2.charAt(3);
            }
            else if (s1.length() == 4){
                if(s1.charAt(1) == s2.charAt(2) && s1.charAt(3) == s2.charAt(1))
                    s3 = s1.charAt(0) + ">" + s2.charAt(3);
                else if (s1.charAt(1) == s2.charAt(2) && s1.charAt(0) == s2.charAt(3)) {
                    s3 = "~" + s2.charAt(1) + ">~" + s1.charAt(3);
                } else if (s1.charAt(2) == s2.charAt(1) && s1.charAt(1) == s2.charAt(3)) {
                    s3 = s2.charAt(0) + ">" + s1.charAt(3);
                } else if (s1.charAt(2) == s2.charAt(1) && s1.charAt(3) == s2.charAt(0)) {
                    s3 = "~" + s1.charAt(1) + ">~" + s2.charAt(3);
                }
            }else if (s1.length() == 5) {
                s3 = "~" + s1.charAt(1) + ">" + s2.charAt(3);
            }
        } else if (s2.length() == 5) {
            if (s1.length() == 4){
                s3 = s1.charAt(0) + ">~" +s2.charAt(4);
            } else if (s2.length() == 5) {
                if(s1.charAt(4) == s2.charAt(1))
                    s3 = "~" +s1.charAt(1) + ">~" + s2.charAt(4);
                else if (s1.charAt(1) == s2.charAt(4)) {
                    s3 = "~" + s2.charAt(1) + ">~" + s1.charAt(4);
                }
            }
        }
        exp3.setRepresentation(s3);
        return exp3;
    }
}

class DisjunctiveSyllogism implements InferenceRule {
    String s1, s2;
    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        s1 = exp1.getRepresentation();
        s2 = exp2.getRepresentation();
        if(s2.length() == 1 && Character.isAlphabetic(s2.charAt(0))){
            if(s1.length() == 4){
                if(s1.charAt(0) == '~' && s1.charAt(1) == s2.charAt(0) && (s1.charAt(2) == 'v' || s1.charAt(2) =='V') && Character.isAlphabetic(s1.charAt(3)) && s1.charAt(1) != s1.charAt(3))
                    return true;
                else if (s1.charAt(2) == '~' && (s1.charAt(1) =='v' || s1.charAt(1) == 'V') && s1.charAt(3) == s2.charAt(0) && s1.charAt(3) != s1.charAt(0) && Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(3))) {
                    return true;
                }
            } else if (s1.length() == 5) {
                if(s1.charAt(0) == '~' && s1.charAt(3) == '~' && (s1.charAt(2) == 'v' || s1.charAt(2) =='V') && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(4)) && s1.charAt(1) != s1.charAt(4) && (s1.charAt(1) == s2.charAt(0) || s1.charAt(4) == s2.charAt(0)))
                    return true;
            }
        } else if (s2.length() == 2 && s2.charAt(0) == '~' && Character.isAlphabetic(s2.charAt(1))) {
            if(s1.length() == 3){
                if (s1.charAt(0) == s2.charAt(1) && (s1.charAt(1) == 'v' || s1.charAt(1) == 'V') && s1.charAt(0) != s1.charAt(2) && (s1.charAt(0) == s2.charAt(1) || s1.charAt(2) == s2.charAt(1))&& Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(2)))
                    return true;
            } else if (s1.length() == 4) {
                if((s1.charAt(1) == 'v' || s1.charAt(1) == 'V') && (s1.charAt(0) == s2.charAt(1)) && s1.charAt(2) == '~' && s1.charAt(3) != s1.charAt(0) && Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(3)) && (s2.charAt(1) != s1.charAt(3) ))
                    return true;
                else if ((s1.charAt(2) == 'v' || s1.charAt(2) == 'V')&& s1.charAt(3) == s2.charAt(1) && s1.charAt(0) == '~' && s1.charAt(1) != s1.charAt(3) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(3)) && (s2.charAt(1) != s1.charAt(1) ))
                    return true;

            }
        }
        return false;
    }
    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        InferenceSolver exp3 = new InferenceSolver();
        if(s2.length() == 1){
            if(s1.length() == 4){
                if(s1.charAt(1) == s2.charAt(0))
                    exp3.setRepresentation(String.valueOf(s1.charAt(3)));
                else if (s1.charAt(3) == s2.charAt(0)) {
                    exp3.setRepresentation(String.valueOf(s1.charAt(0)));
                }
            } else if (s1.length() == 5) {
                if (s1.charAt(1) == s2.charAt(0))
                    exp3.setRepresentation("~" + s1.charAt(4));
                else if (s1.charAt(4) == s2.charAt(0)) {
                    exp3.setRepresentation("~" + s1.charAt(1));
                }
            }
        } else if (s2.length() == 2) {
            if (s1.length() == 3){
                if(s1.charAt(0) == s2.charAt(1))
                    exp3.setRepresentation(String.valueOf(s1.charAt(2)));
                else if (s1.charAt(2) == s2.charAt(1)) {
                    exp3.setRepresentation(String.valueOf(s1.charAt(0)));
                }
            } else if (s1.length() == 4) {
                if(s1.charAt(0) == s2.charAt(1)){
                    exp3.setRepresentation("~" + s1.charAt(3));
                } else if (s1.charAt(3) == s2.charAt(1)) {
                    exp3.setRepresentation("~" + s1.charAt(1));
                }
            }
        }
        return exp3;
    }
}

class Resolution implements InferenceRule {
    String s1, s2;
    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        s1 = exp1.getRepresentation();
        s2 = exp2.getRepresentation();
        if(s2.length() == 3 && (s2.charAt(1) == 'v' || s2.charAt(1) == 'V')){
            if(s1.length() == 4){
                if(s1.charAt(0) == '~' && (s1.charAt(2) == 'v' || s1.charAt(2) =='V') && s1.charAt(1) == s2.charAt(0) && s1.charAt(1) != s1.charAt(3) && s1.charAt(1) != s2.charAt(2) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(3)) && Character.isAlphabetic(s2.charAt(2)))
                    return true;
            } else if (s1.length() == 5) {
                if(s1.charAt(0) =='~' && s1.charAt(3) =='~' && (s1.charAt(2) == 'v' || s1.charAt(2) =='V') && s1.charAt(1) == s2.charAt(0) && s1.charAt(1) != s1.charAt(4) && s1.charAt(1) != s2.charAt(2) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(4)) && Character.isAlphabetic(s2.charAt(2)))
                    return true;
            }
        } else if (s2.length() == 4) {
            if(s1.length() == 3){
                if(s2.charAt(0) == '~' && (s2.charAt(2) =='v' || s2.charAt(2) =='V') && (s1.charAt(1) == 'v' || s1.charAt(1) == 'V') && s1.charAt(0) == s2.charAt(1) && s1.charAt(0) != s1.charAt(2) && s1.charAt(0) != s2.charAt(3) && Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(2)) && Character.isAlphabetic(s2.charAt(3)))
                    return true;
            } else if (s1.length() == 4) {
                if(s2.charAt(0) == '~' && (s2.charAt(2) =='v' || s2.charAt(2) =='V') && (s1.charAt(1) == 'v' || s1.charAt(1) == 'V') && s1.charAt(0) == s2.charAt(1) && s1.charAt(0) != s1.charAt(3) && s1.charAt(0) != s2.charAt(3) && Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(3)) && Character.isAlphabetic(s2.charAt(3)))
                    return true;
                else if(s2.charAt(2) == '~' && (s2.charAt(1) =='v' || s2.charAt(1) =='V') && (s1.charAt(2) == 'v' || s1.charAt(2) == 'V') && s1.charAt(1) == s2.charAt(0) && s1.charAt(1) != s1.charAt(3) && s1.charAt(1) != s2.charAt(3) && s1.charAt(0) == '~' && Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(2)) && Character.isAlphabetic(s2.charAt(3))) {
                    return true;
                } else if (s1.charAt(0) == '~' && (s1.charAt(2) =='v' || s1.charAt(2) == 'V') && s2.charAt(2) == '~' && (s1.charAt(1) == 'v' || s1.charAt(1) == 'V') && s1.charAt(1) == s2.charAt(0) && s1.charAt(1) != s1.charAt(3) && s1.charAt(1) != s2.charAt(3) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(3)) && Character.isAlphabetic(s2.charAt(3))) {
                    return true;
                } else if (s1.charAt(2) == '~' && s2.charAt(0) == '~' && (s1.charAt(1) == 'v' || s1.charAt(1) == 'V') && (s2.charAt(2) == 'v' || s2.charAt(2) == 'V') && s1.charAt(0) == s2.charAt(1) && s1.charAt(0) != s1.charAt(3) && s1.charAt(0) != s2.charAt(3) && Character.isAlphabetic(s1.charAt(0)) && Character.isAlphabetic(s1.charAt(3)) && Character.isAlphabetic(s2.charAt(3))) {
                    return true;
                } else if (s1.charAt(0) == '~' && s2.charAt(2) =='~' && (s1.charAt(2) == 'v' || s1.charAt(2) == 'V') && (s2.charAt(1) == 'v' || s2.charAt(1) == 'V') && s1.charAt(1) == s2.charAt(0)) {
                    return true;
                }
            } else if (s1.length() == 5) {
                if(s1.charAt(0) == '~' && s1.charAt(3) == '~' && s2.charAt(2) == '~' && (s1.charAt(2) == 'v' || s1.charAt(2) == 'V') && (s2.charAt(1) == 'v' || s2.charAt(1) == 'V') && s1.charAt(1) == s2.charAt(0) && s1.charAt(1) != s1.charAt(4) && s1.charAt(1) != s2.charAt(3) && Character.isAlphabetic(s1.charAt(1)) && Character.isAlphabetic(s1.charAt(4)) && Character.isAlphabetic(s2.charAt(3)))
                    return true;
            }
        }
        return false;
    }
    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        InferenceSolver exp3 = new InferenceSolver();
        String ans = "";
        if(s2.length() == 3){
            if(s1.length() == 4)
                ans += s1.charAt(3) + "v" + s2.charAt(2);
            else if (s1.length() == 5) {
                ans += "~" + s1.charAt(4) + "v" + s2.charAt(2);
            }
        } else if (s2.length() == 4) {
            if (s1.length() == 4) {
                if (s1.charAt(0) == '~')
                    ans += s1.charAt(3) + "v~" + s2.charAt(3);
                else if (s2.charAt(0) == '~') {
                    ans += "~" + s1.charAt(3) + "v" + s2.charAt(3);
                }
            }
            else if (s1.length() == 5) {
                if(s2.length() == 4) {
                    ans += "~" + s1.charAt(4) + "v~" + s2.charAt(3);
                }
            }
        }
        exp3.setRepresentation(ans);
        return exp3;
    }
}

public class InferenceSolver implements Expression, InferenceEngine {
    //2 strings
    ArrayList<Expression> expressions = new ArrayList<>();
    ArrayList<InferenceRule> rules = new ArrayList<>();
    String exp;
    ModusPonens ponens= new ModusPonens();
    ModusTollens tollens = new ModusTollens();
    DisjunctiveSyllogism dis = new DisjunctiveSyllogism();
    HypotheticalSyllogism hyp = new HypotheticalSyllogism();
    Resolution resolution = new Resolution();
    int index0 = 0;
    int index1 = 1;
    @Override
    public String getRepresentation() {
        return this.exp;
    }
    @Override
    public void setRepresentation(String representation) {
        this.exp = representation;
    }
    @Override
    public void addRule(InferenceRule rule) {
        rules.add(rule);
    }
    @Override
    public void addExpression(Expression exp) {
        expressions.add(exp);
    }
    @Override
    public Expression applyRules() {
        //  perform calculation for 3rd answer string expression
        int flag =0;
        if(expressions.get(index0).getRepresentation().length() < expressions.get(index1).getRepresentation().length()){
            index0 = 1;
            index1 = 0;
            flag =1;
        }if(ponens.matches(expressions.get(index0), expressions.get(index1))){
            System.out.println("(Modus Ponens)");
            return ponens.apply(expressions.get(index0), expressions.get(index1));
        } else if (tollens.matches(expressions.get(index0), expressions.get(index1)) || tollens.matches(expressions.get(index0), expressions.get(index1))) {
            System.out.println("(Modus Tollens)");
            return tollens.apply(expressions.get(index0), expressions.get(index1));
        } else if (dis.matches(expressions.get(index0), expressions.get(index1)) || dis.matches(expressions.get(index0), expressions.get(index1))) {
            System.out.println("(Disjunctive Syllogism)");
            return dis.apply(expressions.get(index0), expressions.get(index1));
        } else if (hyp.matches(expressions.get(index0), expressions.get(index1)) || hyp.matches(expressions.get(index0), expressions.get(index1))) {
            System.out.println("(Hypothetical Syllogism)");
            return hyp.apply(expressions.get(index0), expressions.get(index1));
        } else if (resolution.matches(expressions.get(index0), expressions.get(index1)) ) {
            if(flag == 1)
            {
                String result1 = resolution.apply(expressions.get(index0), expressions.get(index1)).getRepresentation();
                String result2 = "";
                for(int i = 2; i>-1; i--)
                    result2 += result1.charAt(i);
                System.out.println(result2 + "\t(Resolution)");
            } else {
                System.out.println("(Resolution)");
                return resolution.apply(expressions.get(index0), expressions.get(index1));
            }
        } else
            System.out.println("The input expression cannot be inferred");
        return this;
    }
}



