//package edu.purdue.carlso70.calculator;

import java.nio.DoubleBuffer;
import java.util.LinkedList;
import java.util.Stack;


public class Calculator {

    private CalculatorViewInterface view;

    private String text = "";

    private LinkedList<String> previous;

    private Boolean equalsWasJustCalled;

    public Calculator(CalculatorViewInterface view) {
        this.previous = new LinkedList<>();
        this.view = view;
        this.equalsWasJustCalled = false;
    }

    public void inputDigit(char act) {
        String[] equation = text.split(" ");

        if (!equalsWasJustCalled) {
            if (hasMoreDecimals(equation[equation.length - 1])) {
                view.invalid();
                return;
            }
            text += act;
        } else {
            text = "" + act;
            equalsWasJustCalled = false;
        }
        this.previous.push(text);
        view.display(text);
    }

    public void equal() {
        String[] equation = text.split(" ");
        if (equation.length != 3) {
            view.invalid();
            return;
        }

        //todo solve for infinity with NaN and 0

        Boolean isNaN = false;

        switch (equation[1]) {
            case "+":
                try {
                    double op1 = Double.parseDouble(equation[0]);
                    double op2 = Double.parseDouble(equation[2]);
                    if (Double.isInfinite(op1) || Double.isInfinite(op2) || Double.isNaN(op1)
                            || Double.isNaN(op2)) {
                        view.invalid();
                        return;
                    }
                    text = "" + String.format("%.2f", op1 + op2);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    view.invalid();
                    return;
                }
                break;
            case "-":
                try {
                    double op1 = Double.parseDouble(equation[0]);
                    double op2 = Double.parseDouble(equation[2]);
                    if (Double.isInfinite(op1) || Double.isInfinite(op2) || Double.isNaN(op1) ||
                            Double.isNaN(op2)) {
                        view.invalid();
                        return;
                    }
                    text = "" + String.format("%.2f", op1 - op2);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    view.invalid();
                    return;
                }
                break;
            case "/":
                try {
                    double op1 = Double.parseDouble(equation[0]);
                    double op2 = Double.parseDouble(equation[2]);
                    if (Double.isInfinite(op1) || Double.isInfinite(op2) || Double.isNaN(op1) ||
                            Double.isNaN(op2)) {
                        view.invalid();
                        return;
                    }
                    text = "" + String.format("%.2f", op1 / op2);
                    if (Double.isInfinite(op1 / op2) && op1 > 0)
                        text = "Infinity";
                    if (Double.isInfinite(op1 / op2) && op1 < 0)
                        text = "-Infinity";
                    if (op1 == 0 && op2 == 0)
                        text = "NaN";
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    view.invalid();
                    return;
                }
                break;
            case "*":
                try {
                    double op1 = Double.parseDouble(equation[0]);
                    double op2 = Double.parseDouble(equation[2]);
                    if (Double.isInfinite(op1) || Double.isInfinite(op2) || Double.isNaN(op1) ||
                            Double.isNaN(op2)) {
                        view.invalid();
                        return;
                    }
                    text = "" + String.format("%.2f", op1 * op2);
                    if (Double.isInfinite(op1 / op2) && op1 > 0)
                        text = "Infinity";
                    if (Double.isInfinite(op1 / op2) && op1 < 0)
                        text = "-Infinity";
                    if (op1 == 0 && op2 == 0)
                        text = "NaN";
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    view.invalid();
                    return;
                }
                break;
            default:
                view.invalid();
        }
        equalsWasJustCalled = true;
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
        //todo fix stack
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }
        if (previous.size() < 2) { // fix this
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
        String[] equation = text.split(" ");

        for (int i = 0; i < equation.length; i++) {
            if (equation[i].equals("NaN") || equation[i].equals("-Infinity") ||
                    equation[i].equals("Infinity")) {
                view.invalid();
                return;
            }
        }

        if (equation.length > 2) {
            view.invalid();
            return;
        }
        Boolean thereIsAnOp = false;
        for (int i = 0; i < equation.length; i++) {
            if (equation[i].matches("[+,-,/,*]")) {
                equation[i] = "" + op + " ";
                thereIsAnOp = true;
            }
        }
        if (thereIsAnOp) {
            text = equationToString(equation);
            this.previous.push(text);
            view.display(text);
            return;
        }

        text += " " + op + " ";
        this.previous.push(text);
        view.display(text);
    }

    public String equationToString(String[] args) {
        String theText = "";
        for (int i = 0; i < args.length; i++) {
            if (i != args.length - 1)
                theText += args[i] + " ";
            else
                theText += args[i];
        }
        return theText;
    }
}
