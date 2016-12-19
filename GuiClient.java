import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import seriaf.poo.client.ClientPeer;
 
abstract public class GuiClient extends JFrame implements ActionListener {
 
    // we define the elements used in
    // the frame
    private ClientPeer peer;
    private static String user;
    private JPanel mainPanel;
    private JLabel mSenderNameLabel;
    private JTextField mSenderTextField;
    private JButton mChangeSenderButton;
    private JTextArea mDisplayTextArea;
    private JTextField mMessageTextField;
    private JScrollPane mMessageAreaScroll;
    private JButton mSendButton;
    
public GuiClient(Socket sock, String user){
    this.peer = peer;
    this.user = user;
    
    peer.setOutputPane(mDisplayTextArea);
    initComponents();

    pack();
    setResizable(false);
    setVisible(true);
 
    setDefaultCloseOperation(EXIT_ON_CLOSE);
}
  
void display(){
    peer.start();
}

public static void main(String[] args) {
    String hostname;
    int port;
    Socket sock = new Socket();
    for(String s : args){
        user = args[0];
        hostname = args[1];
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[2] + " must be an integer.");
                System.exit(1);
            }
        }
    }
    
    GuiClient gui = new GuiClient(sock,user) {
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };
    
}
 
private void initComponents() {
    // setting the layout as FlowLayout
    
 
    // creating the components
 
    // the panel adds the components in
    // a FlowLayout
    mainPanel = new JPanel(); 
    mSenderNameLabel = new JLabel("Name");
    mSenderTextField = new JTextField(15);
    mMessageTextField = new JTextField(25);
    mDisplayTextArea = new JTextArea(30, 30); //10 columns, 10 rows
    mMessageAreaScroll = new JScrollPane();
    mChangeSenderButton = new JButton("Change Name");
    mSendButton = new JButton("Send");
    
    setSize(300,300);
    BoxLayout layout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);
    //mainPanel.add(new JButton("OK"));
    //adding components to panel
    
    add(mainPanel);
    mainPanel.setLayout(layout);
    
    JPanel firstRow = new JPanel(new FlowLayout());
    firstRow.add(mSenderNameLabel);
    firstRow.add(mSenderTextField);
    firstRow.add(mChangeSenderButton);

    JPanel secondRow = new JPanel(new FlowLayout());
    secondRow.add(mDisplayTextArea);
    
    JPanel thirdRow = new JPanel(new FlowLayout());
    thirdRow.add(mMessageTextField);
    thirdRow.add(mSendButton);
    
    firstRow.setVisible(true);
    secondRow.setVisible(true);
    thirdRow.setVisible(true);
    
    mainPanel.add(firstRow);
    mainPanel.add(secondRow);
    mainPanel.add(thirdRow);
   
    //adding scrollPane to frame
    

    mChangeSenderButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent _actionEvent) {
        //peer.setUsername(mSenderTextField.getText());
        System.out.println(mSenderTextField.getText());
        }
    });
    mSendButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent _actionEvent) {
            if(mMessageTextField.getText().startsWith("/q"))
                dispose();
            if(mMessageTextField.getText().startsWith("/w"))
                    //peer.sendMessage(user, user);
                System.out.println("HEI HEI HEI");
            //peer.sendMessage(mMessageTextField.getText());
            System.out.println(mMessageTextField.getText());
            //peer.sendMessage(mMessageTextField.getText());
        }
    
    });
    
} 
// this is the handler defined in the ActionListener interface

}
