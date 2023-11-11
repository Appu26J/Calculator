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

        if (string.endsWith("root"))
        {
            String[] parts = this.getEquation().split(" ");
            ArrayList<Double> numbers = new ArrayList<>();

            for (String part : parts)
            {
                if (!this.containsOperator(part))
                {
                    numbers.add(Double.valueOf(part));
                }
            }

            String number = String.valueOf(numbers.get(0));

            if (number.endsWith(".0"))
            {
                number = number.substring(0, number.length() - 2);
            }

            string = "root(" + number + ")";
        }

        else if (string.endsWith("xx"))
        {
            String[] parts = this.getEquation().split(" ");
            ArrayList<Double> numbers = new ArrayList<>();

            for (String part : parts)
            {
                if (!this.containsOperator(part))
                {
                    numbers.add(Double.valueOf(part));
                }
            }

            String number = String.valueOf(numbers.get(0));

            if (number.endsWith(".0"))
            {
                number = number.substring(0, number.length() - 2);
            }

            string = number + " * " + number;
        }

        else if (string.endsWith("sin"))
        {
            String[] parts = this.getEquation().split(" ");
            ArrayList<Double> numbers = new ArrayList<>();

            for (String part : parts)
            {
                if (!this.containsOperator(part))
                {
                    numbers.add(Double.valueOf(part));
                }
            }

            String number = String.valueOf(numbers.get(0));

            if (number.endsWith(".0"))
            {
                number = number.substring(0, number.length() - 2);
            }

            string = "sin(" + number + ")";
        }

        else if (string.endsWith("cos"))
        {
            String[] parts = this.getEquation().split(" ");
            ArrayList<Double> numbers = new ArrayList<>();

            for (String part : parts)
            {
                if (!this.containsOperator(part))
                {
                    numbers.add(Double.valueOf(part));
                }
            }

            String number = String.valueOf(numbers.get(0));

            if (number.endsWith(".0"))
            {
                number = number.substring(0, number.length() - 2);
            }

            string = "cos(" + number + ")";
        }

        else if (string.endsWith("&"))
        {
            String[] parts = this.getEquation().split(" ");
            ArrayList<Double> numbers = new ArrayList<>();

            for (String part : parts)
            {
                if (!this.containsOperator(part))
                {
                    numbers.add(Double.valueOf(part));
                }
            }

            String number = String.valueOf(numbers.get(0));

            if (number.endsWith(".0"))
            {
                number = number.substring(0, number.length() - 2);
            }

            string = "1 / " + number;
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
            this.buttons.add(new Button("x2", false, xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("root", false, 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("sin", false, 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("cos", false, 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("<-", false, 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("7", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("8", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("9", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("/", false, 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("%", false, 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("4", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("5", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("6", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("*", false, 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("1/x", false, 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("1", xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("2", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("3", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("-", false, 255 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("+", false, 340 + xOffset, yOffset, 75, 75, this));
            yOffset += 85;
            this.buttons.add(new Button("clr", false, xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("0", 85 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button(".", 170 + xOffset, yOffset, 75, 75, this));
            this.buttons.add(new Button("=", false, 255 + xOffset, yOffset, 160, 75, this));
        }
    }

    public String getEquation()
    {
        return this.equation;
    }

    public void appendEquation(String text)
    {
        if (this.equation.equals("0") && !this.containsOperator(text))
        {
            this.equation = "";
        }

        this.equation += text;
    }

    public void backSpaceEquation()
    {
        if (this.equation.length() > 1)
        {
            this.equation = this.equation.substring(0, this.equation.length() - 1);
            this.equation = this.equation.trim();
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

    public boolean containsOperator(String text)
    {
        text = text.trim();
        return text.endsWith("xx") || text.endsWith("root") || text.endsWith("sin") || text.endsWith("cos") || text.endsWith("/") || text.endsWith("%") || text.endsWith("*") || text.endsWith("&") || text.endsWith("-") || text.endsWith("+");
    }
}
