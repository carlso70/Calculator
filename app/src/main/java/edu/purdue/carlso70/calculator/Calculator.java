package edu.purdue.carlso70.calculator;

public class Calculator {

    private CalculatorViewInterface view;

    private String text = "";

    public Calculator(CalculatorViewInterface view) {
        this.view = view;
    }

    public void inputDigit(char act) {
        text += act;
        view.display(text);
        view.invalid();
    }

    public void equal() {
        String[] equation = text.split(" ");
        if (equation.length != 3) {
            view.invalid();
        }
        int countOfNumbers = 0;
        int countOfOperators = 0;
        for (String token: equation) {
            if (token.matches("[-+]?\\d*\\.?\\d+"))
                countOfNumbers++;
            if (token.matches("[+,-,/,*]"))
                countOfOperators++; //todo check this line, may be problematic
        }
        if (countOfNumbers != 2 || countOfOperators != 1)
            view.invalid();

        text += "=";
        view.display(text);
    }

    public void dot() {
        //cant have more than 2 decimal digits
        //Can only be clicked after following a number must follow a digit
        text += ".";
        view.display(text);
    }

    public void delete() {
        //acts as an undo button
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
        view.display(text);
        System.out.println("Delete");
    }

    public void operator(char op) {
        //can only be called after entering the first number
        //when calling the operator function twice in a row, only the last call will be considered
        //the result of the previous operation can be used as the first operand for a new equation
        //this will be the case if an operator is the next input after a call to equal
        text += " " + op + " ";
        view.display(text);
        System.out.println(op);
    }
}
