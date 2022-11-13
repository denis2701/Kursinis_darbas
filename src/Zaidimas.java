import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Zaidimas implements KeyListener {

    public static void main(String[] args) {
        Zaidimas zaidimas = new Zaidimas();
    }


    private ZaidimoPanele zaidimoPanele;


    public Zaidimas() {
        JFrame langas = new JFrame("Reversi");
        langas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        zaidimoPanele = new ZaidimoPanele();
        langas.getContentPane().add(zaidimoPanele);
        langas.addKeyListener(this);
        langas.pack();
        langas.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        zaidimoPanele.valdytiIvedima(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}