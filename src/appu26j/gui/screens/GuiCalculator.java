package appu26j.gui.screens;

import java.awt.*;
import java.util.ArrayList;

public class GuiCalculator extends GuiScreen
{
    private final ArrayList<Button> buttons = new ArrayList<>();
    private String equation = "0";

    @Override
    public void drawScreen(float mouseX, float mouseY)
    {
        for (Button button : this.buttons)
        {
            button.drawScreen(mouseX, mouseY);
        }

        String string = this.equation;

        try
        {
            if (string.contains("root"))
            {
                String number = String.valueOf(Double.parseDouble(string.replace("root", "")));

                if (number.length() > 10)
                {
                    number = number.substring(0, 10);
                }

                if (number.endsWith("0"))
                {
                    number = !number.contains(".") ? number : number.replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                string = "root(" + number + ")";
            }

            else if (string.contains("sin"))
            {
                String number = String.valueOf(Double.parseDouble(string.replace("sin", "")));

                if (number.length() > 10)
                {
                    number = number.substring(0, 10);
                }

                if (number.endsWith("0"))
                {
                    number = !number.contains(".") ? number : number.replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                string = "sin(" + number + ")";
            }

            else if (string.contains("cos"))
            {
                String number = String.valueOf(Double.parseDouble(string.replace("cos", "")));

                if (number.length() > 10)
                {
                    number = number.substring(0, 10);
                }

                if (number.endsWith("0"))
                {
                    number = !number.contains(".") ? number : number.replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                string = "cos(" + number + ")";
            }

            else if (string.contains("xx"))
            {
                String number = String.valueOf(Double.parseDouble(string.replace("xx", "")));

                if (number.length() > 10)
                {
                    number = number.substring(0, 10);
                }

                if (number.endsWith("0"))
                {
                    number = !number.contains(".") ? number : number.replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                string = number + " * " + number;
            }

            else if (string.contains("&"))
            {
                String number = String.valueOf(Double.parseDouble(string.replace("&", "")));

                if (number.length() > 10)
                {
                    number = number.substring(0, 10);
                }

                if (number.endsWith("0"))
                {
                    number = !number.contains(".") ? number : number.replaceAll("0*$", "").replaceAll("\\.$", "");
                }

                string = "1 / " + number;
            }

            else
            {
                String[] parts = this.buttons.get(0).separateNumbersAndLetters(this.getEquation());
                String previousString = string;
                string = "";

                for (String part : parts)
                {
                    if (part.isEmpty())
                    {
                        continue;
                    }

                    string += part + " ";
                }

                string = string.substring(0, string.length() - 1);

                if (string.startsWith("-"))
                {
                    string = previousString;
                }
            }
        }

        catch (Exception e)
        {
            ;
        }

        if (string.startsWith("."))
        {
            string = "0" + string;
        }

        this.fontRendererExtraBig.drawString(string, this.width - (this.fontRendererExtraBig.getStringWidth(string) + 10), this.height / 15, new Color(50, 50, 50));
    }

    @Override
    public void mouseClicked(int mouseButton, float mouseX, float mouseY)
    {
        super.mouseClicked(mouseButton, mouseX, mouseY);

        for (Button button : this.buttons)
        {
            button.mouseClicked(mouseButton, mouseX, mouseY);
        }
    }

    @Override
    public void initGUI(float width, float height)
    {
        super.initGUI(width, height);

        if (this.buttons.isEmpty())
        {
            float xOffset = 15, yOffset = height - 430;
            this.buttons.add(new Button("x2", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("root", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("sin", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("cos", 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("<-", 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("7", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("8", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("9", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("/", 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("%", 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("4", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("5", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("6", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("*", 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("1/x", 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("1", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("2", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("3", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("-", 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("+", 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("clr", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("0", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button(".", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("=", 255 + xOffset, yOffset, 160, 75, this));
        }
    }

    public String getEquation()
    {
        return this.equation;
    }

    public void appendEquation(String text)
    {
        if (this.equation.equals("0") && (text.contains("0") || text.contains("1") || text.contains("2") || text.contains("3") || text.contains("4") || text.contains("5") || text.contains("6") || text.contains("7") || text.contains("8") || text.contains("9") || text.contains("NaN") || text.contains("Infinity")))
        {
            this.equation = "";
        }

        this.equation += text;

        if (this.equation.startsWith("0") && (this.equation.endsWith("1") || this.equation.endsWith("2") || this.equation.endsWith("3") || this.equation.endsWith("4") || this.equation.endsWith("5") || this.equation.endsWith("6") || this.equation.endsWith("7") || this.equation.endsWith("8") || this.equation.endsWith("9")) && this.buttons.get(0).separateNumbersAndLetters(this.equation).length == 1)
        {
            this.equation = this.equation.substring(1);
        }
    }

    public void backSpaceEquation()
    {
        if (this.equation.length() > 1)
        {
            this.equation = this.equation.substring(0, this.equation.length() - 1);
        }

        else
        {
            this.equation = "0";
        }
    }

    public void appendAtStartEquation(String text)
    {
        this.equation = text + this.equation;
    }

    public void setEquation(String equation)
    {
        this.equation = equation;
    }

    public void clearEquation()
    {
        this.equation = "0";
    }
}
