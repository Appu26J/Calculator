package appu26j.gui.screens;

import appu26j.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class Button
{
    private final GuiCalculator guiCalculator;
    private final float x, y, width, height;
    private final String text;
    private boolean number;

    public Button(String text, boolean number, float x, float y, float width, float height, GuiCalculator guiCalculator)
    {
        this.text = text;
        this.number = number;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.guiCalculator = guiCalculator;
    }

    public Button(String text, float x, float y, float width, float height, GuiCalculator guiCalculator)
    {
        this.number = true;
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
                String[] parts = this.guiCalculator.getEquation().split(" ");

                if ((parts.length < (this.guiCalculator.getEquation().endsWith("xx") || this.guiCalculator.getEquation().endsWith("root") || this.guiCalculator.getEquation().endsWith("sin") || this.guiCalculator.getEquation().endsWith("cos") || this.guiCalculator.getEquation().endsWith("&") ? 2 : 3) || (this.number && !this.guiCalculator.getEquation().endsWith("xx") && !this.guiCalculator.getEquation().endsWith("root") && !this.guiCalculator.getEquation().endsWith("sin") && !this.guiCalculator.getEquation().endsWith("cos") && !this.guiCalculator.getEquation().endsWith("&"))) && !(this.guiCalculator.containsOperator(this.guiCalculator.getEquation()) && !this.number) && !this.text.equals("="))
                {
                    String text = this.text;

                    if (text.equals("x2"))
                    {
                        text = "xx";
                    }

                    else if (text.equals("1/x"))
                    {
                        text = "&";
                    }

                    this.guiCalculator.appendEquation(this.number && (this.guiCalculator.getEquation().endsWith("0") || this.guiCalculator.getEquation().endsWith("1") || this.guiCalculator.getEquation().endsWith("2") || this.guiCalculator.getEquation().endsWith("3") || this.guiCalculator.getEquation().endsWith("4") || this.guiCalculator.getEquation().endsWith("5") || this.guiCalculator.getEquation().endsWith("6") || this.guiCalculator.getEquation().endsWith("7") || this.guiCalculator.getEquation().endsWith("8") || this.guiCalculator.getEquation().endsWith("9") || this.guiCalculator.getEquation().endsWith(".")) ? text : (" " + text));
                }

                else
                {
                    try
                    {
                        ArrayList<Double> numbers = new ArrayList<>();

                        for (String part : parts)
                        {
                            if (!this.guiCalculator.containsOperator(part))
                            {
                                numbers.add(Double.valueOf(part));
                            }
                        }

                        double answer = 0;

                        switch (parts[1])
                        {
                            case "xx":
                            {
                                answer = BigDecimal.valueOf(numbers.get(0)).multiply(BigDecimal.valueOf(numbers.get(0)), new MathContext(3)).doubleValue();
                                break;
                            }

                            case "root":
                            {
                                answer = BigDecimal.valueOf(Math.sqrt(numbers.get(0))).round(new MathContext(3)).doubleValue();
                                break;
                            }

                            case "sin":
                            {
                                answer = BigDecimal.valueOf(Math.sin(numbers.get(0))).round(new MathContext(3)).doubleValue();
                                break;
                            }

                            case "cos":
                            {
                                answer = BigDecimal.valueOf(Math.cos(numbers.get(0))).round(new MathContext(3)).doubleValue();
                                break;
                            }

                            case "&":
                            {
                                answer = new BigDecimal(1).divide(BigDecimal.valueOf(numbers.get(0)), new MathContext(3)).doubleValue();
                                break;
                            }

                            case "/":
                            {
                                answer = BigDecimal.valueOf(numbers.get(0)).divide(BigDecimal.valueOf(numbers.get(1)), new MathContext(3)).doubleValue();
                                break;
                            }

                            case "%":
                            {
                                answer = BigDecimal.valueOf(numbers.get(0)).remainder(BigDecimal.valueOf(numbers.get(1)), new MathContext(3)).doubleValue();
                                break;
                            }

                            case "*":
                            {
                                answer = BigDecimal.valueOf(numbers.get(0)).multiply(BigDecimal.valueOf(numbers.get(1)), new MathContext(3)).doubleValue();
                                break;
                            }

                            case "-":
                            {
                                answer = BigDecimal.valueOf(numbers.get(0)).subtract(BigDecimal.valueOf(numbers.get(1)), new MathContext(3)).doubleValue();
                                break;
                            }

                            case "+":
                            {
                                answer = BigDecimal.valueOf(numbers.get(0)).add(BigDecimal.valueOf(numbers.get(1)), new MathContext(3)).doubleValue();
                                break;
                            }
                        }

                        if (this.text.equals("="))
                        {
                            String ans = String.valueOf(answer);

                            if (ans.endsWith(".0"))
                            {
                                ans = ans.substring(0, ans.length() - 2);
                            }

                            this.guiCalculator.setEquation(ans);
                        }

                        else
                        {
                            String text = this.text, ans = String.valueOf(answer);

                            if (ans.endsWith(".0"))
                            {
                                ans = ans.substring(0, ans.length() - 2);
                            }

                            if (text.equals("x2"))
                            {
                                text = "xx";
                            }

                            else if (text.equals("1/x"))
                            {
                                text = "&";
                            }

                            this.guiCalculator.setEquation(this.number ? ans : (ans + " " + text));
                        }
                    }

                    catch (Exception e)
                    {
                        ;
                    }
                }
            }
        }
    }
}
