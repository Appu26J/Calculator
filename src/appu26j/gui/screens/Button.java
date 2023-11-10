package appu26j.gui.screens;

import appu26j.gui.Gui;

import java.awt.*;
import java.util.ArrayList;

public class Button
{
    private final GuiCalculator guiCalculator;
    private final float x, y, width, height;
    private final String text;

    public Button(String text, float x, float y, float width, float height, GuiCalculator guiCalculator)
    {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.guiCalculator = guiCalculator;
    }

    public void drawScreen(float mouseX, float mouseY)
    {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.guiCalculator.isInsideBox(mouseX, mouseY, this.x, this.y, this.x + this.width, this.y + this.height) ? new Color(235, 235, 235) : new Color(245, 245, 245));

        if (this.text.length() <= 1)
        {
            this.guiCalculator.getFontRenderers().get(2).drawString(this.text, (this.x + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(2).getStringWidth(this.text) / 2), this.y + 8, new Color(50, 50, 50));
        }

        else
        {
            if (this.text.equals("x2"))
            {
                this.guiCalculator.getFontRenderers().get(1).drawString("x", (this.x - 6 + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(1).getStringWidth("x") / 2), this.y + 18, new Color(50, 50, 50));
                this.guiCalculator.getFontRenderers().get(0).drawString("2", (this.x + 6 + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(0).getStringWidth("2") / 2), this.y + 18, new Color(50, 50, 50));
            }

            else if (this.text.equals("<-"))
            {
                this.guiCalculator.getFontRenderers().get(2).drawString("<", (this.x - 6 + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(2).getStringWidth("<") / 2), this.y + 8, new Color(50, 50, 50));
                this.guiCalculator.getFontRenderers().get(2).drawString("_", (this.x - 4 + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(2).getStringWidth("_") / 2), this.y - 8.5F, new Color(50, 50, 50));
                this.guiCalculator.getFontRenderers().get(2).drawString("_", (this.x + 5 + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(2).getStringWidth("_") / 2), this.y - 8.5F, new Color(50, 50, 50));
            }

            else
            {
                this.guiCalculator.getFontRenderers().get(1).drawString(this.text, (this.x + (this.width / 2)) - (this.guiCalculator.getFontRenderers().get(1).getStringWidth(this.text) / 2), this.y + 18, new Color(50, 50, 50));
            }
        }
    }

    public void mouseClicked(int mouseButton, float mouseX, float mouseY)
    {
        if (this.guiCalculator.isInsideBox(mouseX, mouseY, this.x, this.y, this.x + this.width, this.y + this.height))
        {
            if (this.text.equals("clr"))
            {
                this.guiCalculator.clearEquation();
            }

            else if (this.text.equals("<-"))
            {
                this.guiCalculator.backSpaceEquation();
            }

            else
            {
                String[] parts = this.separateNumbersAndLetters(this.guiCalculator.getEquation());

                if (!this.text.equals("=") && (parts.length < 3 || !this.isOperation(this.text)) && !this.guiCalculator.getEquation().endsWith("xx") && !this.guiCalculator.getEquation().endsWith("root") && !this.guiCalculator.getEquation().endsWith("sin") && !this.guiCalculator.getEquation().endsWith("cos") && !this.guiCalculator.getEquation().endsWith("&"))
                {
                    String textToAppend = this.text;

                    switch (textToAppend)
                    {
                        case "1/x":
                        {
                            textToAppend = "&";
                            break;
                        }

                        case "x2":
                        {
                            textToAppend = "xx";
                            break;
                        }
                    }

                    if (!isOperation(textToAppend) || (!this.guiCalculator.getEquation().contains("xx") && !this.guiCalculator.getEquation().contains("root") && !this.guiCalculator.getEquation().contains("sin") && !this.guiCalculator.getEquation().contains("cos") && !this.guiCalculator.getEquation().contains("/") && !this.guiCalculator.getEquation().contains("%") && !this.guiCalculator.getEquation().contains("*") && !this.guiCalculator.getEquation().contains("&") && !this.guiCalculator.getEquation().contains("-") && !this.guiCalculator.getEquation().contains("+")))
                    {
                        this.guiCalculator.appendEquation(textToAppend);
                    }
                }

                else
                {
                    // String[] parts = this.guiCalculator.getEquation().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

                    if (parts.length >= 2)
                    {
                        ArrayList<Double> numbers = new ArrayList<>();

                        for (String part : parts)
                        {
                            if (part.isEmpty())
                            {
                                continue;
                            }

                            if (!isOperation(part))
                            {
                                try
                                {
                                    numbers.add(Double.parseDouble(part));
                                }

                                catch (Exception e)
                                {
                                    ;
                                }
                            }
                        }

                        this.guiCalculator.clearEquation();
                        String operator = parts[1], answer = "0";

                        try
                        {
                            if (parts.length == 2)
                            {
                                switch (operator)
                                {
                                    case "xx":
                                    {
                                        answer = String.valueOf(numbers.get(0) * numbers.get(0));
                                        break;
                                    }

                                    case "root":
                                    {
                                        answer = String.valueOf(Math.sqrt(numbers.get(0)));
                                        break;
                                    }

                                    case "sin":
                                    {
                                        answer = String.valueOf(Math.sin(numbers.get(0)));
                                        break;
                                    }

                                    case "cos":
                                    {
                                        answer = String.valueOf(Math.cos(numbers.get(0)));
                                        break;
                                    }

                                    case "&":
                                    {
                                        answer = String.valueOf(1 / numbers.get(0));
                                        break;
                                    }
                                }
                            }

                            else
                            {
                                switch (operator)
                                {
                                    case "/":
                                    {
                                        answer = String.valueOf(numbers.get(0) / numbers.get(1));
                                        break;
                                    }

                                    case "%":
                                    {
                                        answer = String.valueOf(numbers.get(0) % numbers.get(1));
                                        break;
                                    }

                                    case "*":
                                    {
                                        answer = String.valueOf(numbers.get(0) * numbers.get(1));
                                        break;
                                    }

                                    case "-":
                                    {
                                        answer = String.valueOf(numbers.get(0) - numbers.get(1));
                                        break;
                                    }

                                    case "+":
                                    {
                                        answer = String.valueOf(numbers.get(0) + numbers.get(1));
                                        break;
                                    }
                                }
                            }
                        }

                        catch (Exception e)
                        {
                            ;
                        }

                        if (answer.length() > 10)
                        {
                            answer = answer.substring(0, 10);
                        }

                        if (answer.endsWith("0"))
                        {
                            answer = !answer.contains(".") ? answer : answer.replaceAll("0*$", "").replaceAll("\\.$", "");
                        }

                        if (this.text.equals("="))
                        {
                            this.guiCalculator.appendEquation(answer);
                        }

                        else
                        {
                            String textToAppend = this.text;

                            switch (textToAppend)
                            {
                                case "1/x":
                                {
                                    textToAppend = "&";
                                    break;
                                }

                                case "x2":
                                {
                                    textToAppend = "xx";
                                    break;
                                }
                            }

                            this.guiCalculator.appendEquation(answer + textToAppend);
                        }
                    }
                }
            }
        }
    }

    private boolean isOperation(String text)
    {
        String[] allLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()-=_+/".split("");

        for (String letter : allLetters)
        {
            if (text.contains(letter))
            {
                return true;
            }
        }

        return false;
    }

    public String[] separateNumbersAndLetters(String text)
    {
        ArrayList<String> temp = new ArrayList<>();
        StringBuilder tempVariable = new StringBuilder();
        boolean aBoolean = false;

        for (String letter : text.split(""))
        {
            if (!aBoolean && isOperation(letter))
            {
                temp.add(tempVariable.toString());
                tempVariable = new StringBuilder();
                aBoolean = true;
            }

            if (aBoolean && !isOperation(letter))
            {
                temp.add(tempVariable.toString());
                tempVariable = new StringBuilder();
                aBoolean = false;
            }

            tempVariable.append(letter);
        }

        temp.add(tempVariable.toString());
        return temp.toArray(new String[0]);
    }
}
