package edu.purdue.carlso70.calculator;

import java.util.LinkedList;
import java.util.Stack;

public class Calculator {

    private CalculatorViewInterface view;

    private String text = "";
    private LinkedList<String> previous;

    public Calculator(CalculatorViewInterface view) {
        this.previous = new LinkedList<>();
        this.view = view;
    }

    public void inputDigit(char act) {
        String[] equation = text.split(" ");
        if (hasMoreDecimals(equation[equation.length - 1])) {
            view.invalid();
            return;
        }
        text += act;
        this.previous.push(text);
        view.display(text);
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
        this.previous.push(text);
        view.display(text);
    }

    public void dot() {
        //cant have more than 2 decimal digits
        //Can only be clicked after following a number must follow a digit
        String[] equation = text.split(" ");
        char[] num = equation[equation.length - 1].toCharArray();
        for (char a : num)
            if (a == '.') {
                view.invalid();
                return;
            }
        if (text.length() == 0 || hasMoreDecimals(equation[equation.length - 1])) {
            view.invalid();
            return;
        }

        text += ".";
        view.display(text);
    }

    public void delete() {
        //acts as an undo button
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
        if (previous.size() < 2) {
            view.display(text);
            return;
        }
        previous.pop();
        text = previous.pop();
        view.display(text);

        System.out.println("Delete");
    }

    public boolean hasMoreDecimals(String number) {
        //This method checks if the number has 2 decimals or has more than 2 decimal places
        //returns true if it does
        char[] num = number.toCharArray();
        int indexOfDecimal = -1;
        int countOfDecimals = 0;

        for (int i = 0; i < num.length; i++) {
            if (num[i] == '.') {
                countOfDecimals++;
                indexOfDecimal = i;
            }
        }

        if (indexOfDecimal == -1)
            return false;

        if (countOfDecimals > 1 || num.length - indexOfDecimal > 2)
            return true;

        return false;
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
