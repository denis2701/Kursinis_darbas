import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ZaidimoPanele extends JPanel implements MouseListener {
    private static final int aukstis = 600;
    private static final int plotis = 500;
    private ZaidimoKarkasas zaidimoKarkasas;
    private ZaidimoStatusas zaidimoStatusas;
    public ZaidimoPanele() {
        setPreferredSize(new Dimension(plotis, aukstis));
        setBackground(Color.GREEN);

        zaidimoKarkasas = new ZaidimoKarkasas(new Pozicija(0,0), plotis, aukstis-100, 8, 8);
        nustatytiZaidimoStatusa(ZaidimoStatusas.geltonoeile);
        addMouseListener(this);
    }
    public void paint(Graphics lentele) {
        super.paint(lentele);
        zaidimoKarkasas.nuspalvinti(lentele);
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
    private void nustatytiZaidimoStatusa(ZaidimoStatusas naujasZaidimoStatusas) {
        zaidimoStatusas = naujasZaidimoStatusas;
        switch (zaidimoStatusas) {
            case melynoeile:
                if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                } else {
                    zaidimoKarkasas.AtnaujintiGalimusEjimus(1);
                    if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                        nustatytiZaidimoStatusa(ZaidimoStatusas.geltonoeile);
                    }
                }
                break;
            case geltonoeile:
                if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                } else {
                    zaidimoKarkasas.AtnaujintiGalimusEjimus(2);
                    if(zaidimoKarkasas.gautiVisusGalimusEjimus().size() > 0) {
                        nustatytiZaidimoStatusa(ZaidimoStatusas.melynoeile);
                    }
                }
                break;
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        if(zaidimoStatusas == ZaidimoStatusas.melynoeile || zaidimoStatusas == ZaidimoStatusas.geltonoeile) {
            Pozicija karkasoPozicija = zaidimoKarkasas.KonvertuotiPelesPaspaudimaILangelius(new Pozicija(e.getX(), e.getY()));
            ZaidimoEile(karkasoPozicija);
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
