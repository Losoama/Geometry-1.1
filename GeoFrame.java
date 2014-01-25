import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Класс главного окна приложения
public class GeoFrame extends JFrame {
    //Компоненты
    private JPanel gPanel1;
    private JLabel jLabel1;
    private JButton jButton1;
    private JTextArea jText1;

    //Синглетон. Единственный объект класса GeoFrame.
    private static GeoFrame gFrame = new GeoFrame();

    private final int DEFAULT_POSITION_X; //координата X верхнего левого угла окна
    private final int DEFAULT_POSITION_Y; //координата Y верхнего левого угла окна
    private final int DEFAULT_WIDTH;      //ширина окна
    private final int DEFAULT_HEIGHT;     //высота окна

    //Вычисления по умолчанию
    {
        Toolkit t = Toolkit.getDefaultToolkit();

        DEFAULT_POSITION_X = t.getScreenSize().width / 4;
        DEFAULT_POSITION_Y = t.getScreenSize().height / 4;
        DEFAULT_WIDTH = t.getScreenSize().width / 2;
        DEFAULT_HEIGHT = t.getScreenSize().height / 2;
    }

    //В конструкторе произведем инициализацию компонентов: создали окно - создадим и компоненты
    private GeoFrame() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(DEFAULT_POSITION_X, DEFAULT_POSITION_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setIconImage(Toolkit.getDefaultToolkit().createImage("C:\\Users\\Игорь\\Desktop\\X7l5SlpT1Uc.jpg"));
        this.setTitle("Геометрия 1.1. Задание 1");

        gPanel1 = new JPanel(new BorderLayout());
        jLabel1 = new JLabel();
        jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1 = new JTextArea("Нарисуйте два отрезка в правой части окна -->");
        jText1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jText1.setEditable(false);
        jButton1 = new JButton();
        jButton1.setText("Clear");
        this.gPanel1.add(this.jLabel1, BorderLayout.EAST);
        this.gPanel1.add(this.jButton1, BorderLayout.WEST);
        this.gPanel1.add(this.jText1, BorderLayout.CENTER);
    }

    public static GeoFrame getGeoFrame() {
        return gFrame;
    }

    public int getPositionX() {
        return DEFAULT_POSITION_X;
    }

    public int getPositionY() {
        return DEFAULT_POSITION_Y;
    }

    @Override
    public int getWidth() {
        return DEFAULT_WIDTH;
    }

    @Override
    public int getHeight() {
        return DEFAULT_HEIGHT;
    }

    //Событие при запуске программы
    private static CreateEventProducer eventProducer = new CreateEventProducer();

    //Глобальные переменные
    ArrayList<Line> lines = new ArrayList<Line>();
    Line currentLine = new Line();
    boolean HOMEWORK = false;
    byte ISCONTAINED = -1;

    public static void main(String[] args) {
        final GeoFrame mainFrame = GeoFrame.getGeoFrame();

        eventProducer.addCreateListener(new CreateListener() {
            public void create(CreateEvent myEvent) {
                mainFrame.setContentPane(mainFrame.gPanel1);
                mainFrame.setVisible(true);

                //Загрузка изображения jLabel1
                final BufferedImage image1 = new BufferedImage(mainFrame.getWidth() / 2,
                        mainFrame.jLabel1.getHeight(),
                        BufferedImage.TYPE_INT_RGB);

                image1.getGraphics().fillRect(0, 0, 480, 640);
                image1.getGraphics().setColor(Color.BLACK);
                ImageIcon iIcon1 = new ImageIcon();
                iIcon1.setImage(image1);
                mainFrame.jLabel1.setIcon(iIcon1);
                final Graphics g = image1.getGraphics();


                mainFrame.jLabel1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        super.mousePressed(e);
                        if (!mainFrame.HOMEWORK) {
                            System.out.println(mainFrame.ISCONTAINED);
                            mainFrame.HOMEWORK = true;
                            for (Line line : mainFrame.lines) {
                                if (new Point(e.getX(), e.getY()).isContained(line.getPoint0()) ||
                                        new Point(e.getX(), e.getY()).isContained(line.getPoint1())) {

                                    g.setXORMode(Color.white);
                                    if (new Point(e.getX(), e.getY()).isContained(line.getPoint0())) {
                                        mainFrame.ISCONTAINED = (byte) ((mainFrame.lines.indexOf(line) + 1) * 10);
                                        mainFrame.currentLine.setXY(
                                                line.getPoint0().getX(),
                                                line.getPoint0().getY(),
                                                line.getPoint1().getX(),
                                                line.getPoint1().getY());
                                    } else {
                                        mainFrame.ISCONTAINED = (byte) ((mainFrame.lines.indexOf(line) + 1) * 10 + 1);
                                        mainFrame.currentLine.setXY(
                                                line.getPoint1().getX(),
                                                line.getPoint1().getY(),
                                                line.getPoint0().getX(),
                                                line.getPoint0().getY());
                                    }


                                    break;
                                }
                            }
                            System.out.println(mainFrame.ISCONTAINED);
                            if (mainFrame.lines.size() < 2 && mainFrame.ISCONTAINED == -1) {
                                g.setColor(Color.BLACK);
                                mainFrame.currentLine.setXY(e.getX(), e.getY(), e.getX(), e.getY());
                            }

                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseReleased(e);
                        if (mainFrame.HOMEWORK) {
                            if (mainFrame.lines.size() < 2 || mainFrame.ISCONTAINED != -1) {
                                mainFrame.currentLine.setXY1(e.getX(), e.getY());
                                g.setPaintMode();

                                mainFrame.jLabel1.repaint();
                                if (mainFrame.ISCONTAINED != -1) {
                                    mainFrame.lines.remove((int) mainFrame.ISCONTAINED / 10 - 1);
                                }

                                mainFrame.lines.add(new Line(
                                        mainFrame.currentLine.getPoint0().getX(),
                                        mainFrame.currentLine.getPoint0().getY(),
                                        mainFrame.currentLine.getPoint1().getX(),
                                        mainFrame.currentLine.getPoint1().getY()));

                                image1.getGraphics().setColor(Color.WHITE);
                                image1.getGraphics().fillRect(0, 0, 480, 640);
                                image1.getGraphics().setColor(Color.BLACK);
                                for (Line line : mainFrame.lines) {
                                    g.drawLine(line.getPoint0().getX(),
                                            line.getPoint0().getY(),
                                            line.getPoint1().getX(),
                                            line.getPoint1().getY());
                                }

                                if (mainFrame.lines.size() == 1) {
                                    mainFrame.jText1.setText("Отрезок №1: " +
                                            mainFrame.lines.get(0));
                                } else {
                                    System.out.println(mainFrame.lines.get(0));
                                    System.out.println(mainFrame.lines.get(1));
                                    mainFrame.jText1.setText("Отрезок №1: " +
                                            mainFrame.lines.get(0) +
                                            "\nОтрезок №2: " +
                                            mainFrame.lines.get(1));
                                }
                            }
                            mainFrame.ISCONTAINED = -1;
                            mainFrame.HOMEWORK = false;
                            mainFrame.currentLine = new Line(0, 0, 0, 0);
                        }
                    }
                });
                mainFrame.jLabel1.addMouseMotionListener(new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (mainFrame.HOMEWORK) {

                            if (mainFrame.lines.size() < 2 || mainFrame.ISCONTAINED != -1) {

                                Point p0 = mainFrame.currentLine.getPoint0();
                                Point p1 = mainFrame.currentLine.getPoint1();
                                g.drawLine(p0.getX(),
                                        p0.getY(),
                                        p1.getX(),
                                        p1.getY());
                                g.setXORMode(Color.white);
                                g.drawLine(p0.getX(),
                                        p0.getY(),
                                        e.getX(), e.getY());
                                mainFrame.currentLine.setXY1(e.getX(), e.getY());
                                mainFrame.jLabel1.repaint();
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
                        image1.getGraphics().setColor(Color.WHITE);
                        image1.getGraphics().fillRect(0, 0, 480, 640);
                        mainFrame.jLabel1.repaint();
                        mainFrame.lines.clear();
                        mainFrame.jText1.setText("Нарисуйте два отрезка в правой части окна -->");
                        mainFrame.ISCONTAINED = -1;
                        mainFrame.HOMEWORK = false;
                    }
                });
            }

        });

        eventProducer.doWork();
    }
}
