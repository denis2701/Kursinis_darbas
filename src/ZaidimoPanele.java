import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ZaidimoPanele extends JPanel implements MouseListener {
    private static final int aukstis = 600;
    private static final int plotis = 500;
    private ZaidimoKarkasas zaidimoKarkasas;
    private ZaidimoStatusas zaidimoStatusas;
    private String zaidimostatusas;
    public ZaidimoPanele() {
        setPreferredSize(new Dimension(plotis, aukstis));
        setBackground(Color.GREEN);

        zaidimoKarkasas = new ZaidimoKarkasas(new Pozicija(0,0), plotis, aukstis-100, 8, 8);
        nustatytiZaidimoStatusa(ZaidimoStatusas.geltonoeile);
        addMouseListener(this);
    }
    private void tekstoZaidimospalvos(Graphics grafika) {
        grafika.setColor(Color.BLACK);
        grafika.setFont(new Font("Arial", Font.BOLD, 35));
        int eilesplotis = grafika.getFontMetrics().stringWidth(zaidimostatusas);
        grafika.drawString(zaidimostatusas, plotis/2-eilesplotis/2, aukstis-40);
    }
    public void paint(Graphics lentele) {
        super.paint(lentele);
        zaidimoKarkasas.nuspalvinti(lentele);
        tekstoZaidimospalvos(lentele);
    }
    public void valdytiIvedima(int raktas) {
        if(raktas == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(raktas == KeyEvent.VK_R) {
            atnaujinti();
            repaint();
        }
    }
    public void atnaujinti() {
        zaidimoKarkasas.atnaujinti();
        nustatytiZaidimoStatusa(ZaidimoStatusas.melynoeile);
    }
    private void ZaidimoEile(Pozicija karkasoPozicija) {
        if(!zaidimoKarkasas.GalimasEjimas(karkasoPozicija)) {
            return;
        } else if(zaidimoStatusas == ZaidimoStatusas.melynoeile) {
            zaidimoKarkasas.ZaistiEjima(karkasoPozicija, 1);
            nustatytiZaidimoStatusa(ZaidimoStatusas.geltonoeile);
        } else if(zaidimoStatusas == ZaidimoStatusas.geltonoeile) {
            zaidimoKarkasas.ZaistiEjima(karkasoPozicija, 2);
            nustatytiZaidimoStatusa(ZaidimoStatusas.melynoeile);
        }
    }
    private void PatikrintiZaidimoPabaiga(boolean darGalimiEjimai) {
        int ZaidimoRezultatas = zaidimoKarkasas.gautiLaimetoja(darGalimiEjimai);
        if(ZaidimoRezultatas == 1) {
            nustatytiZaidimoStatusa(ZaidimoStatusas.geltonaslaimejo);
        } else if(ZaidimoRezultatas == 2) {
            nustatytiZaidimoStatusa(ZaidimoStatusas.melynaslaimejo);
        } else if(ZaidimoRezultatas == 3) {
            nustatytiZaidimoStatusa(ZaidimoStatusas.lygiosios);
        }
    }
    private void nustatytiZaidimoStatusa(ZaidimoStatusas naujasZaidimoStatusas) {
        zaidimoStatusas = naujasZaidimoStatusas;
        switch (zaidimoStatusas) {
            case melynoeile:
                if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                    zaidimostatusas = "Melyno eile";
                } else {
                    zaidimoKarkasas.AtnaujintiGalimusEjimus(1);
                    if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                        nustatytiZaidimoStatusa(ZaidimoStatusas.geltonoeile);
                    } else {
                        PatikrintiZaidimoPabaiga(false);
                    }
                }
                break;
            case geltonoeile:
                if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                    zaidimostatusas = "Geltono Eile";
                } else {
                    zaidimoKarkasas.AtnaujintiGalimusEjimus(2);
                    if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                        nustatytiZaidimoStatusa(ZaidimoStatusas.melynoeile);
                    } else {
                        PatikrintiZaidimoPabaiga(false);
                    }
                }
                break;
            case geltonaslaimejo: zaidimostatusas = "Geltonas Laimejo!"; break;
            case melynaslaimejo: zaidimostatusas = "Melynas Laimejo!"; break;
            case lygiosios: zaidimostatusas = "Lygiosios"; break;
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(zaidimoStatusas == ZaidimoStatusas.melynoeile || zaidimoStatusas == ZaidimoStatusas.geltonoeile) {
            Pozicija karkasoPozicija = zaidimoKarkasas.KonvertuotiPelesPaspaudimaILangelius(new Pozicija(e.getX(), e.getY()));
            ZaidimoEile(karkasoPozicija);
            PatikrintiZaidimoPabaiga(true);
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
