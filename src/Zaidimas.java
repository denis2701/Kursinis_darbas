import javax.swing.*;

public class Zaidimas {

    public static void main(String[] args) {
        Zaidimas zaidimas = new Zaidimas();
    }

    private ZaidimoPanele zaidimoPanele;

    public Zaidimas() {
        JFrame langas = new JFrame("Reversi");
        langas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        zaidimoPanele = new ZaidimoPanele();
        langas.getContentPane().add(zaidimoPanele);
        langas.pack();
        langas.setVisible(true);
    }
}