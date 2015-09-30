import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;


public class swing extends JFrame {
    private final JFrame frame = new JFrame("my frame");
    private ArrayList<JButton> list = new ArrayList<>();
    private ArrayList<GridBagConstraints> gridList = new ArrayList<>();
    private JButton emptyButton = new JButton("16");
    private JLabel labelOfSteps = new JLabel("0");
    private JLabel timerLabel = new JLabel("00:00:00");
    private GridBagConstraints emptyButtonsConstr = new GridBagConstraints(3, 3, 1, 1, 0.0, 0.9, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(2, 2, 2, 2), 10, 10);
    private int count = 0;
    private int secds = 0;
    private int mins = 0;
    private int hours = 0;

    private Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            secds++;
            if (secds == 60) {
                secds = 0;
                mins++;
            }
            if (mins == 60) {
                mins = 0;
                hours++;
            }
            if (secds > 9) {
                if (mins > 9) {
                    if (hours > 9) {
                        timerLabel.setText(String.format("%d:%d:%d", hours, mins, secds));
                    } else timerLabel.setText(String.format("0%d:%d:%d", hours, mins, secds));
                } else timerLabel.setText(String.format("0%d:0%d:%d", hours, mins, secds));
            } else {
                if (mins > 9) {
                    if (hours > 9) {
                        timerLabel.setText(String.format("%d:%d:0%d", hours, mins, secds));
                    } else timerLabel.setText(String.format("0%d:%d:0%d", hours, mins, secds));
                } else timerLabel.setText(String.format("0%d:0%d:0%d", hours, mins, secds));
            }
        }
    });
    private ArrayList<JButton> buttons = new ArrayList<>();

    public void init() {
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(true);
        emptyButton.setEnabled(false);
        emptyButton.setBorderPainted(false);
        JLabel textNumbers = new JLabel("Steps:");
        JLabel timerDescription = new JLabel("Timer:");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Game");
        JMenuItem menuItem1 = new JMenuItem("New game");
        JMenuItem menuItem2 = new JMenuItem("Exit");
        textNumbers.setFont(new Font("1", Font.ITALIC, 13));
        timerDescription.setFont(new Font("2", Font.ITALIC, 13));
        timerLabel.setFont(new Font("3", Font.ITALIC, 13));
        labelOfSteps.setFont(new Font("4", Font.ITALIC, 13));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) break;
                gridList.add(new GridBagConstraints(i, j, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(2, 2, 2, 2), 10, 20));
            }
        }
        buttonsAddToList(list);
        for (int i = 0; i < 15; i++) {
            frame.add(list.get(i), gridList.get(i));
        }
        buttonsAddListeners(list);
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewGame();
            }
        });
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitFromGame();
            }
        });
        menu1.add(menuItem1);
        menu1.add(menuItem2);
        menuBar.add(menu1);
        frame.setJMenuBar(menuBar);
        frame.add(emptyButton, emptyButtonsConstr);
        frame.add(textNumbers, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 10, 10));
        frame.add(labelOfSteps, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 10, 10));
        frame.add(timerDescription, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 10, 10));
        frame.add(timerLabel, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 10, 10));
        emptyButton.setText(null);
        for (int i = 0; i < list.size(); i++) // buttons - this is sorted list array
        {
            int k = i + 1;
            for (int j = 0; j < list.size(); j++) {
                if (k == Integer.parseInt(list.get(j).getText())) buttons.add(list.get(j));
            }
        }
        frame.pack();
    }

    private class ButtonAction implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                String str = button.getText();
                int number = 0;
                if (count == 0) timer.start();
                for (JButton jButton : list) {
                    if (jButton.getText().equals(str)) break;
                    number++;
                }
                int differenceX = gridList.get(number).gridx - emptyButtonsConstr.gridx;
                int differenceY = gridList.get(number).gridy - emptyButtonsConstr.gridy;

                if (differenceX == 1 || differenceX == -1 || differenceY == 1 || differenceY == -1) {
                    if ((differenceX + differenceY) % 2 == 1 || (differenceX + differenceY) % 2 == -1) {
                        if (differenceX == 0)
                            checkerXOrY(differenceY, number, "y");
                        if (differenceY == 0)
                            checkerXOrY(differenceX, number, "x");
                    }
                }
            }
            if (gameIsOver() == 22) {
                String time = String.format("%d hours %d minutes and %d seconds", hours, mins, secds);
                int countOfSteps = count;
                timer.stop();
                JLabel textForPane = new JLabel("Congratulations! You did " + String.valueOf(countOfSteps) + " steps for " + time);
                textForPane.setFont(new Font("arial", Font.ITALIC, 13));
                Object[] choices = {"Play again"};
                int res = JOptionPane.showOptionDialog(frame, textForPane,
                        "Result", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE, null, choices, null);
                if (res == 0) {
                    createNewGame();
                } else exitFromGame();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private void buttonsAddListeners(ArrayList<JButton> list) {
        for (JButton button : list) {
            button.addMouseListener(new ButtonAction());
        }
    }

    private void buttonsAddToList(ArrayList<JButton> list) {
        Set<String> set = new LinkedHashSet<>();
        Random random = new Random();
        while (set.size() < 15) {
            String str = String.valueOf(random.nextInt(15) + 1);
            set.add(str);
        }
        for (String s : set) {
            JButton button = new JButton(s);
            button.setPreferredSize(emptyButton.getPreferredSize());
            button.setBackground(Color.WHITE);
            button.setFont(new Font("one", Font.ITALIC, 15));
            button.setFocusPainted(false);
            list.add(button);
        }
    }

    private int gameIsOver() {
        int checker = 0;
        for (int i = 0; i < buttons.size() - 1; i++) {
            if (buttons.get(i).getX() < buttons.get(i + 1).getX() && i != 3 && i != 7 && i != 11) {
                checker++;
            }
        }
        for (int i = 0; i < buttons.size() - 4; i++) {
            if (buttons.get(i).getY() < buttons.get(i + 4).getY()) {
                checker++;
            }
        }
        return checker;
    }

    public static void main(String[] args)
    {
        swing sw = new swing();
        sw.init();
    }

    private void createNewGame() {
        frame.dispose();
        swing s = new swing();
        s.init();
    }

    private void exitFromGame() {
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }

    private void difference(int number, int minus, String xOrY) {
        frame.remove(list.get(number));
        frame.add(emptyButton, gridList.get(number));
        GridBagConstraints c = gridList.get(number);
        if (xOrY.equals("x")) {
            c.gridx += -minus;
            emptyButtonsConstr.gridx -= -minus;
        } else if (xOrY.equals("y")) {
            c.gridy += -minus;
            emptyButtonsConstr.gridy -= -minus;
        }
        frame.add(list.get(number), c);
        count++;
        labelOfSteps.setText(String.valueOf(count));
        frame.repaint();
    }

    private void checkerXOrY(int differenceY, int number, String xOrY) {
        switch (differenceY) {
            case -1:
                if (xOrY.equals("y")) {
                    difference(number, -1, "y");
                } else difference(number, -1, "x");
                break;
            case 1:
                if (xOrY.equals("y")) {
                    difference(number, 1, "y");
                } else difference(number, 1, "x");
                break;
        }
    }
}
