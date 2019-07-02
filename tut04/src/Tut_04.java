

// Submission deadline: Tuesday, 2 July 2019, 4 pm

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;

public class Tut_04 extends JFrame 
{
    static final int DEFAULT_FRAME_WIDTH = 500;
    static final int DEFAULT_FRAME_HEIGHT = 250;
    static final int DISPLAY_SIZE = 32;
    static final int NUMBER_OF_BUTTONS = 18;
    static final int NUMBER_OF_CTL_BUTTONS = 2;
    static final int NUMBER_OF_RADIX = 4;
    static final int[] RADIXES = {2, 8, 10, 16};

    private final JButton[] buttons;
    private final JTextField display;
    private final JRadioButton[] choices;
    private int radix;
    StringBuffer num_str = new StringBuffer();

    public static void main(String[] args) 
    {
        Tut_04 myFrame = new Tut_04();
        myFrame.setTitle("Number Format Conversion");
        myFrame.setVisible(true);
    }

    public Tut_04() {
        String[] keyLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "CLR", "<<"};
        // CLR = clear,  << = backspace

        String[] choiceLabels = {"Bin", "Oct", "Dec", "Hex"};

        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);

        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 4));

        buttons = new JButton[NUMBER_OF_BUTTONS];
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++) 
        {
            buttons[i] = new JButton(keyLabels[i]);
            if (i < NUMBER_OF_BUTTONS - NUMBER_OF_CTL_BUTTONS) 
                numberPanel.add(buttons[i]);
        }

        display = new JTextField(DISPLAY_SIZE);
        display.setHorizontalAlignment(JTextField.RIGHT);  //text is right-justified
        display.setFont(new Font("Dialog", Font.PLAIN, 14)); //select font and size
        display.setEditable(false); //user is not allowed to edit the text display
        display.setText("0");

        JPanel textPanel = new JPanel();
        textPanel.add(buttons[16]);
        textPanel.add(display);
        textPanel.add(buttons[17]);

        JPanel controlPanel = new JPanel();
        ButtonGroup group = new ButtonGroup();
        choices = new JRadioButton[4];
        for (int i = 0; i < NUMBER_OF_RADIX; i++) 
        {
            choices[i] = new JRadioButton(choiceLabels[i]);
            group.add(choices[i]);
            controlPanel.add(choices[i]);
        }
        choices[2].setSelected(true);  // default radix = decimal
        radix = RADIXES[2];

        add(textPanel, "North");
        add(numberPanel, "Center");
        add(controlPanel, "South");

        // Add your codes here to complete the deisgn of the JFrame constructor

        ActionListener listener = new ButtonListener();
        for (JButton m : buttons){
            m.addActionListener(listener);
        }
        for (JRadioButton n : choices){
            n.addActionListener(listener);
        }
    }

    private class ButtonListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent event) 
        {
            // Implement the actionPerformed method  

            Object source = event.getSource();
            String number = display.getText();

            if (source instanceof JRadioButton) // choosing the radix
            {
                // Your statements
                String radix_str = ((JRadioButton) source).getText();
                BigInteger num = new BigInteger(number, radix);

                if (radix_str == "Bin"){
                    radix = RADIXES[0];
                }else if (radix_str == "Oct"){
                    radix = RADIXES[1];
                }else if (radix_str == "Dec"){
                    radix = RADIXES[2];
                }else if (radix_str == "Hex"){
                    radix = RADIXES[3];
                }

                Integer r = new Integer(radix);
                display.setText(dec2other(num, new BigInteger(Integer.toString(r))));

            } 
            else // Other buttons
            {
                if (source == buttons[16])  ///Button "CLR"
                {
                    display.setText("0");
                    num_str = new StringBuffer();
                }
                else if (source == buttons[17]) ///Button "<<"
                {
                    if (number.length() == 1) {
                        display.setText("0");
                        num_str = new StringBuffer("0");
                    }else{
                        number = number.substring(0, number.length() - 1);
                        num_str = new StringBuffer(number);
                        display.setText(number);
                    }
                }
                //////////////////////Button "0" ~ "F"
                else {

                    for (int idx = 0; idx < radix; idx++)
                    {
                        if (num_str.toString().equals("0"))
                            num_str = new StringBuffer();

                        if (source == buttons[idx])
                        {
                            num_str.append(buttons[idx].getText());
                            display.setText(num_str.toString());
                        }
                    }
                }
            }
        }
    }

    public String dec2other(BigInteger dec, BigInteger rdx)
    {
        StringBuffer res = new StringBuffer();
        String[] keyLabels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F"};

        while (!dec.equals(new BigInteger("0")))
        {
            res.append(keyLabels[dec.remainder(rdx).intValue()]);
            dec = new BigInteger(dec.divide(rdx).toString());
        }
        return res.reverse().toString();
    }
}

