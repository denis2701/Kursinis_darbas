import java.awt.*;

public class KarkasoLangai extends Kvadratas {
    private int langeliobukle;
    private boolean ribojimas;
    public KarkasoLangai(Pozicija pozicija, int plotis, int aukstis) {
        super(pozicija, plotis, aukstis);
        atnaujinti();
    }
    public void atnaujinti() {
        langeliobukle = 0;
        ribojimas = false;
    }
    public void nustatytiLangelioBusena(int naujasZaidimoStatusas) {
        this.langeliobukle = naujasZaidimoStatusas;
    }
    public int gautiLangelioBukle() {
        return langeliobukle;
    }
    public void NustatytiRibojima(boolean ribojimas) {
        this.ribojimas = ribojimas;
    }
    public void nuspalvinti(Graphics vidutiniskvadratas) {
        if(ribojimas) {
            vidutiniskvadratas.setColor(new Color(255, 0, 50, 150));
            vidutiniskvadratas.fillRect(pozicija.a, pozicija.b, plotis, aukstis);
        }

        if(langeliobukle == 0) return;
        vidutiniskvadratas.setColor(langeliobukle == 1 ? Color.BLUE : Color.YELLOW);
        vidutiniskvadratas.fillOval(pozicija.a, pozicija.b, plotis, aukstis);
    }
}
