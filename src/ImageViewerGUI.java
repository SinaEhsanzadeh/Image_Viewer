import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

public class ImageViewerGUI extends JFrame implements ActionListener{

    JButton selectFileButton = new JButton("Select Image");
    JButton showImageButton = new JButton("Show Image");
    JButton resizeButton = new JButton("Resize Image");
    JButton grayscaleButton = new JButton("Greyscale Image");
    JButton brightnessButton = new JButton("Set Brightness");
    JButton closeButton = new JButton("Close");
    JButton showResizeButton = new JButton("Display Image");
    JButton showBrightnessButton = new JButton("Display Image");
    JButton backButton = new JButton("Back");
    JTextField widthTextField = new JTextField();
    JTextField heightTextField = new JTextField();
    JTextField brightnessTextField = new JTextField();
    JPanel mainPanel = new JPanel();
    String filePath = "/home/...";
    BufferedImage bufferedImage;
    File file;
    ImageIcon icon;
    JLabel displayedImage;
    JFileChooser fileChooser = new JFileChooser(filePath);
    int h = 900;
    int w = 1200;
    float brightenFactor = 1;

    ImageViewerGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Image Viewer");
        this.setSize(700, 300);
        this.setVisible(true);
        this.setResizable(true);

        mainPanel();
    }

    public void mainPanel(){
        // Create main panel for adding to Frame
        mainPanel.setLayout(null);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 2));
        buttonsPanel.setBounds(150, 50, 400, 150);

        // Adding all buttons to Grid panel
        buttonsPanel.add(this.selectFileButton);
        buttonsPanel.add(this.showImageButton);
        buttonsPanel.add(this.brightnessButton);
        buttonsPanel.add(this.grayscaleButton);
        buttonsPanel.add(this.resizeButton);
        buttonsPanel.add(this.closeButton);

        closeButton.addActionListener(this);
        showImageButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        selectFileButton.addActionListener(this);
        showResizeButton.addActionListener(this);
        grayscaleButton.addActionListener(this);
        resizeButton.addActionListener(this);
        backButton.addActionListener(this);
        brightnessButton.addActionListener(this);
        showBrightnessButton.addActionListener(this);

        // add Grid panel that contains 6 buttons to main panel
        mainPanel.add(buttonsPanel);

        // add main panel to our frame
        this.add(mainPanel);
    }

    public void resizePanel(){
        JPanel resizePanel = new JPanel();
        JLabel resize = new JLabel("Resize Image");
        JLabel width = new JLabel("Width:");
        JLabel height = new JLabel("Height:");

        resizePanel.setLayout(null);
        resizePanel.setSize(900, 1200);

        showResizeButton.setBounds(125, 200, 150, 30);
        backButton.setBounds(425, 200, 150, 30);
        widthTextField.setBounds(250,50,200 ,30);
        heightTextField.setBounds(250, 125, 200, 30);

        resize.setBounds(310, 20, 200, 20);
        width.setBounds(200, 40, 50, 50);
        height.setBounds(200, 115, 50, 50);

        resizePanel.add(showResizeButton);
        resizePanel.add(widthTextField);
        resizePanel.add(heightTextField);
        resizePanel.add(backButton);
        resizePanel.add(width);
        resizePanel.add(height);
        resizePanel.add(resize);


        this.add(resizePanel);
    }
    public void brightnessPanel(){

        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(null);

        JLabel brightness = new JLabel("Set Brightness");
        JLabel brightScale = new JLabel("Brightness Factor:");
        brightness.setBounds(305, 20, 100, 50);
        brightScale.setBounds(140,112, 200, 50);

        brightnessTextField.setBounds(250, 125, 200, 30);
        backButton.setBounds(125, 200, 150, 30);
        showBrightnessButton.setBounds(425, 200, 150, 30);

        brightnessPanel.add(brightness);
        brightnessPanel.add(brightScale);
        brightnessPanel.add(brightnessTextField);
        brightnessPanel.add(backButton);
        brightnessPanel.add(showBrightnessButton);
        this.add(brightnessPanel);
    }

    public void chooseFileImage(){
        try{
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION){
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                bufferedImage = ImageIO.read(file);
                icon = new ImageIcon(bufferedImage);
                displayedImage = new JLabel(icon);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public void showOriginalImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        tempFrame.setSize(1800, 1000);
        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempPanel.add(displayedImage);
        tempFrame.add(tempPanel);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);

    }

    public void rtrn(){
        this.getContentPane().removeAll();
        this.add(mainPanel);
        this.revalidate();
        this.repaint();
    }

    public void grayScaleImage(){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        BufferedImage grayscaleBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayscaleBufferedImage.getGraphics();
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();

        //Retrieved from https://stackoverflow.com/questions/9131678/convert-a-rgb-image-to-grayscale-image-reducing-the-memory-in-java

        ImageIcon grayscaleIcon = new ImageIcon(grayscaleBufferedImage);

        JLabel grayscaleDisplayedImage = new JLabel(grayscaleIcon);
        tempPanel.add(grayscaleDisplayedImage);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showResizeImage(int w, int h){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();
        BufferedImage resizedBufferedImage;

        Image tmp = bufferedImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        resizedBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedBufferedImage.createGraphics();
        g2d.drawImage(tmp,0, 0, null);
        g2d.dispose();

        //Resizing code acquired at https://stackoverflow.com/questions/9417356/bufferedimage-resize

        ImageIcon resizedIcon = new ImageIcon(resizedBufferedImage);
        JLabel resizedImage = new JLabel(resizedIcon);

        tempPanel.add(resizedImage);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }
    public void showBrightnessImage(float f){
        JFrame tempFrame = new JFrame();
        JPanel tempPanel = new JPanel();

        BufferedImage brightenedBufferedImage;

        Image tmp = bufferedImage.getScaledInstance(bufferedImage.getWidth(), bufferedImage.getHeight(), Image.SCALE_SMOOTH);
        brightenedBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = brightenedBufferedImage.createGraphics();
        g2d.drawImage(tmp,0, 0, null);
        g2d.dispose();

        RescaleOp rescaleOp = new RescaleOp( f, 0, null);
        rescaleOp.filter(bufferedImage, brightenedBufferedImage);

        ImageIcon brightenedIcon = new ImageIcon(brightenedBufferedImage);

        JLabel brightenedDisplayedImage = new JLabel(brightenedIcon);
        tempPanel.add(brightenedDisplayedImage);

        tempPanel.setSize(1800, 1000);
        tempFrame.setTitle("Image Viewer");
        tempFrame.setSize(1800, 1000);
        tempFrame.setVisible(true);
        tempFrame.setResizable(true);
        tempFrame.add(tempPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==resizeButton){
            this.getContentPane().removeAll();
            this.resizePanel();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()== showImageButton){
            showOriginalImage();

        }else if(e.getSource()==grayscaleButton){
            grayScaleImage();

        }else if(e.getSource()== showResizeButton){

            showResizeImage(Integer.parseInt(widthTextField.getText()), Integer.parseInt(heightTextField.getText()));

        }else if(e.getSource()==brightnessButton){

            this.getContentPane().removeAll();
            this.brightnessPanel();
            this.revalidate();
            this.repaint();

        }else if(e.getSource()== showBrightnessButton){
            showBrightnessImage(Float.parseFloat(brightnessTextField.getText()));

        }else if(e.getSource()== selectFileButton){
             chooseFileImage();

        }else if(e.getSource()==closeButton){
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource()==backButton){
            rtrn();
        }
    }
}