import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Класс главного окна приложения
public class GeoFrame extends JFrame {
    private static GeoFrame gFrame = new GeoFrame();

    private final int DEFAULT_POSITION_X; //координата X верхнего левого угла окна
    private final int DEFAULT_POSITION_Y; //координата Y верхнего левого угла окна
    private final int DEFAULT_WIDTH;      //ширина окна
    private final int DEFAULT_HEIGHT;     //высота окна

    //Компоненты
    private JPanel gPanel1;

    private JLabel jLabel1;
    private JTextArea jText1;
    private JPanel gPanel2;

    private JTextField jTextFieldx00;
    private JTextField jTextFieldy00;
    private JTextField jTextFieldx10;
    private JTextField jTextFieldy10;

    private JTextField jTextFieldx01;
    private JTextField jTextFieldy01;
    private JTextField jTextFieldx11;
    private JTextField jTextFieldy11;

    private JTextField jTextField12;
    private JTextField jTextField22;

    private JButton jButton1;
    private JButton jButton2;


    //Дополнительные компоненты
    private BufferedImage image1;
    private ImageIcon iIcon1;
    private Graphics g;

    //Коструктор окна
    private GeoFrame() {
        Toolkit t = Toolkit.getDefaultToolkit();
        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;

        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("C:\\Users\\Игорь\\Desktop\\Документы\\X7l5SlpT1Uc.jpg"));
        this.setTitle("Геометрия 1.1. Задание 1");

        gPanel1 = new JPanel(new BorderLayout());
        jLabel1 = new JLabel();
        jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1 = new JTextArea("Нарисуйте два отрезка в правой части окна -->");
        jText1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1.setEditable(false);
        gPanel2 = new JPanel(new GridLayout(12, 1));
        gPanel1.add(this.jLabel1, BorderLayout.EAST);
        gPanel1.add(this.gPanel2, BorderLayout.WEST);
        gPanel1.add(this.jText1, BorderLayout.CENTER);

        jButton1 = new JButton();
        jButton1.setText("Clear");
        jTextFieldx00 = new JTextField("0");
        jTextFieldy00 = new JTextField("0");
        jTextFieldx10 = new JTextField("0");
        jTextFieldy10 = new JTextField("0");
        jTextFieldx01 = new JTextField("0");
        jTextFieldy01 = new JTextField("0");
        jTextFieldx11 = new JTextField("0");
        jTextFieldy11 = new JTextField("0");
        jTextField12 = new JTextField("1-ый отрезок");
        jTextField12.setEditable(false);
        jTextField22 = new JTextField("2-ый отрезок");
        jTextField22.setEditable(false);
        jButton2 = new JButton();
        jButton2.setText("Create");
        gPanel2.add(jButton1);
        gPanel2.add(jTextField12);
        gPanel2.add(jTextFieldx00);
        gPanel2.add(jTextFieldy00);
        gPanel2.add(jTextFieldx10);
        gPanel2.add(jTextFieldy10);
        gPanel2.add(jTextField22);
        gPanel2.add(jTextFieldx01);
        gPanel2.add(jTextFieldy01);
        gPanel2.add(jTextFieldx11);
        gPanel2.add(jTextFieldy11);
        gPanel2.add(jButton2);

        this.setContentPane(this.gPanel1);
        this.setVisible(true);

        this.image1 = new BufferedImage(this.getWidth() / 2, this.jLabel1.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.image1.getGraphics().fillRect(0, 0, 480, 640);
        this.image1.getGraphics().setColor(Color.BLACK);

        iIcon1 = new ImageIcon();
        iIcon1.setImage(this.image1);
        this.jLabel1.setIcon(iIcon1);
        g = this.image1.getGraphics();
    }

    public static GeoFrame getGeoFrame() {
        return gFrame;
    }

    //Глобальные переменные
    ArrayList<Line> lines = new ArrayList<Line>();
    Line currentLine = new Line();
    boolean isClicked = false;
    byte isSimilar = -1;

    public static void main(String[] args) {
        final GeoFrame mainFrame = GeoFrame.getGeoFrame();

        mainFrame.jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mainFrame.g.setColor(Color.BLACK);
                mainFrame.isClicked = true;

                for (Line line : mainFrame.lines) {
                    if (new Point(e.getX(), e.getY()).isContained(line.getPoint0()) ||
                            new Point(e.getX(), e.getY()).isContained(line.getPoint1())) {
                        mainFrame.g.setXORMode(Color.white);
                        if (new Point(e.getX(), e.getY()).isContained(line.getPoint0())) {
                            mainFrame.isSimilar = (byte) ((mainFrame.lines.indexOf(line) + 1) * 10);

                            mainFrame.currentLine.setXY(line.getPoint1(), line.getPoint0());
                        } else {
                            mainFrame.isSimilar = (byte) ((mainFrame.lines.indexOf(line) + 1) * 10 + 1);
                            mainFrame.currentLine.setXY(line);
                        }
                        break;
                    }
                }

                if (mainFrame.lines.size() < 2 && mainFrame.isSimilar == -1) {
                    mainFrame.currentLine.setXY(e.getX(), e.getY(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (mainFrame.isClicked) {
                    if (mainFrame.lines.size() < 2 || mainFrame.isSimilar != -1) {
                        mainFrame.currentLine.setXY1(e.getX(), e.getY());
                        mainFrame.g.setPaintMode();
                        mainFrame.jLabel1.repaint();
                        if (mainFrame.isSimilar != -1) {
                            mainFrame.lines.remove((int) mainFrame.isSimilar / 10 - 1);
                            if (mainFrame.isSimilar % 10 == 1) {
                                mainFrame.lines.add(mainFrame.currentLine.copyLine());
                            } else {
                                mainFrame.lines.add(new Line(mainFrame.currentLine.copyLine().getPoint1(),
                                        mainFrame.currentLine.copyLine().getPoint0()));
                            }
                            if (mainFrame.isSimilar / 10 == 1) {
                                Line line1 = mainFrame.lines.get(0);
                                mainFrame.lines.remove(0);
                                mainFrame.lines.add(line1);
                            }
                        } else {
                            mainFrame.lines.add(mainFrame.currentLine.copyLine());
                        }

                        mainFrame.image1.getGraphics().setColor(Color.WHITE);
                        mainFrame.image1.getGraphics().fillRect(0, 0, 480, 640);
                        mainFrame.image1.getGraphics().setColor(Color.BLACK);
                        for (Line line : mainFrame.lines) {
                            mainFrame.g.drawLine(line.getPoint0().getX(), line.getPoint0().getY(),
                                    line.getPoint1().getX(), line.getPoint1().getY());
                        }

                        Point p0 = mainFrame.lines.get(0).getPoint0();
                        Point p1 = mainFrame.lines.get(0).getPoint1();
                        mainFrame.setText(p0, p1, true);

                        if (mainFrame.lines.size() == 1) {
                            mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0));
                        } else {
                            mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                                    "\nОтрезок №2: " + mainFrame.lines.get(1)
                                    + mainFrame.lines.get(0).isCrossed(mainFrame.lines.get(1)));

                            p0 = mainFrame.lines.get(1).getPoint0();
                            p1 = mainFrame.lines.get(1).getPoint1();
                            mainFrame.setText(p0, p1, false);
                        }

                    }

                    mainFrame.isSimilar = -1;
                    mainFrame.isClicked = false;
                    mainFrame.currentLine = new Line(0, 0, 0, 0);
                }
            }
        });

        mainFrame.jLabel1.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mainFrame.isClicked) {

                    if (mainFrame.lines.size() < 2 || mainFrame.isSimilar != -1) {

                        Point p0 = mainFrame.currentLine.getPoint0();
                        Point p1 = mainFrame.currentLine.getPoint1();
                        mainFrame.g.drawLine(p0.getX(),
                                p0.getY(),
                                p1.getX(),
                                p1.getY());
                        mainFrame.g.setXORMode(Color.white);
                        mainFrame.g.drawLine(p0.getX(),
                                p0.getY(),
                                e.getX(), e.getY());
                        mainFrame.currentLine.setXY1(e.getX(), e.getY());
                        mainFrame.jLabel1.repaint();

                        if (mainFrame.lines.size() == 0) {
                            mainFrame.jText1.setText("Отрезок №1: " + mainFrame.currentLine);
                            mainFrame.setText(p0, p1, true);
                        } else if (mainFrame.lines.size() == 1) {
                            if (mainFrame.isSimilar == -1) {
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                                        "\nОтрезок №2: " + mainFrame.currentLine
                                        + mainFrame.lines.get(0).isCrossed(mainFrame.currentLine));
                                mainFrame.setText(p0, p1, false);
                            } else if (mainFrame.isSimilar == 10) {
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.currentLine);
                                mainFrame.setText(p1, p0, true);
                            } else {
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.currentLine);
                                mainFrame.setText(p0, p1, true);
                            }
                        } else {

                            if (mainFrame.isSimilar == 10) {
                                System.out.println(mainFrame.isSimilar);
                                mainFrame.jTextFieldx00.setText("" + p1.getX());
                                mainFrame.jTextFieldy00.setText("" + p1.getY());
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                                        "\nОтрезок №2: " + mainFrame.currentLine
                                        + mainFrame.lines.get(1).isCrossed(new Line(p0, p1)));
                            } else if (mainFrame.isSimilar == 11) {
                                System.out.println(mainFrame.isSimilar);
                                mainFrame.jTextFieldx10.setText("" + p1.getX());
                                mainFrame.jTextFieldy10.setText("" + p1.getY());
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                                        "\nОтрезок №2: " + mainFrame.currentLine
                                        + mainFrame.lines.get(1).isCrossed(new Line(p0, p1)));
                            } else if (mainFrame.isSimilar == 20) {
                                System.out.println(mainFrame.isSimilar);
                                mainFrame.jTextFieldx01.setText("" + p1.getX());
                                mainFrame.jTextFieldy01.setText("" + p1.getY());
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                                        "\nОтрезок №2: " + mainFrame.currentLine
                                        + mainFrame.lines.get(0).isCrossed(new Line(p0, p1)));
                            } else if (mainFrame.isSimilar == 21) {
                                System.out.println(mainFrame.isSimilar);
                                mainFrame.jTextFieldx11.setText("" + p1.getX());
                                mainFrame.jTextFieldy11.setText("" + p1.getY());
                                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                                        "\nОтрезок №2: " + mainFrame.currentLine
                                        + mainFrame.lines.get(0).isCrossed(new Line(p0, p1)));
                            }


                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //Вызывается, если мышь не нажата
            }
        });

        mainFrame.jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.image1.getGraphics().setColor(Color.WHITE);
                mainFrame.image1.getGraphics().fillRect(0, 0, 480, 640);
                mainFrame.jLabel1.repaint();
                mainFrame.lines.clear();
                mainFrame.jText1.setText("Нарисуйте два отрезка в правой части окна -->");
                mainFrame.isSimilar = -1;
                mainFrame.isClicked = false;
                mainFrame.setText(new Point(0,0), new Point(0,0),true);
                mainFrame.setText(new Point(0,0), new Point(0,0),false);
            }
        });

        mainFrame.jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                mainFrame.lines.clear();
                mainFrame.image1.getGraphics().setColor(Color.WHITE);
                mainFrame.image1.getGraphics().fillRect(0, 0, 480, 640);
                mainFrame.lines.add(
                        new Line(mainFrame.jTextFieldx00.getText(), mainFrame.jTextFieldy00.getText(),
                                mainFrame.jTextFieldx10.getText(), mainFrame.jTextFieldy10.getText()));
                mainFrame.lines.add(
                        new Line(mainFrame.jTextFieldx01.getText(), mainFrame.jTextFieldy01.getText(),
                                mainFrame.jTextFieldx11.getText(), mainFrame.jTextFieldy11.getText()));
                mainFrame.image1.getGraphics().setPaintMode();
                mainFrame.g.setColor(Color.BLACK);
                for (Line line : mainFrame.lines) {
                    mainFrame.g.drawLine(line.getPoint0().getX(), line.getPoint0().getY(),
                            line.getPoint1().getX(), line.getPoint1().getY());
                }
                mainFrame.jText1.setText("Отрезок №1: " + mainFrame.lines.get(0) +
                        "\nОтрезок №2: " + mainFrame.lines.get(1)
                        + mainFrame.lines.get(0).isCrossed(mainFrame.lines.get(1)));
                mainFrame.isSimilar = -1;
                mainFrame.isClicked = false;
                mainFrame.currentLine = new Line(0, 0, 0, 0);

                mainFrame.jLabel1.repaint();
                }
                catch (IllegalArgumentException f){
                    mainFrame.jText1.setText("Ошибка: "+f.getMessage());
                }
            }
        });
    }

    public void setText(Point p0, Point p1, boolean isFirst) {
        if (isFirst) {
            this.jTextFieldx00.setText("" + p0.getX());
            this.jTextFieldy00.setText("" + p0.getY());
            this.jTextFieldx10.setText("" + p1.getX());
            this.jTextFieldy10.setText("" + p1.getY());
        } else {
            this.jTextFieldx01.setText("" + p0.getX());
            this.jTextFieldy01.setText("" + p0.getY());
            this.jTextFieldx11.setText("" + p1.getX());
            this.jTextFieldy11.setText("" + p1.getY());
        }
    }
}
