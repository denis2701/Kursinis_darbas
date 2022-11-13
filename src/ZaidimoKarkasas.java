import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZaidimoKarkasas extends Kvadratas{
    private KarkasoLangai[][] karkasoLangai;
    private int ejimonumeris;
    private List<Pozicija> galimiEjimai;

    public ZaidimoKarkasas(Pozicija pozicija, int plotis, int aukstis, int karkasoplotis, int karkasoaukstis) {
        super(pozicija, plotis, aukstis);
        karkasoLangai = new KarkasoLangai[karkasoplotis][karkasoaukstis];
        int langelioplotis = (plotis-pozicija.a)/karkasoplotis;
        int langelioaukstis = (aukstis-pozicija.b)/karkasoaukstis;
        for(int i = 0; i < karkasoplotis; i++) {
            for(int j = 0; j < karkasoaukstis; j++) {
                karkasoLangai[i][j] = new KarkasoLangai(new Pozicija(pozicija.a+langelioplotis*i, pozicija.b+langelioaukstis*j), langelioplotis, langelioaukstis);
            }
        }
        ejimonumeris = 0;
        galimiEjimai = new ArrayList<>();
        AtnaujintiGalimusEjimus(1);
    }
    public Pozicija KonvertuotiPelesPaspaudimaILangelius(Pozicija pelesPozicija) {
        int gridX = (pelesPozicija.a- pozicija.a)/karkasoLangai[0][0].plotis;
        int gridY = (pelesPozicija.b- pozicija.b)/karkasoLangai[0][0].aukstis;
        if(gridX >= karkasoLangai.length || gridX < 0 || gridY >= karkasoLangai[0].length || gridY < 0) {
            return new Pozicija(-1,-1);
        }
        return new Pozicija(gridX,gridY);
    }
    private void nuspalvintiKarkasoLinijas(Graphics grafika) {
        grafika.setColor(Color.BLACK);
        int y2 = pozicija.b+aukstis;
        int y1 = pozicija.b;
        for(int i = 0; i < karkasoLangai.length+1; i++)
            grafika.drawLine(pozicija.a+i * karkasoLangai[0][0].plotis, y1, pozicija.a+i * karkasoLangai[0][0].plotis, y2);

        int x2 = pozicija.a+plotis;
        int x1 = pozicija.a;
        for(int i = 0; i < karkasoLangai[0].length+1; i++)
            grafika.drawLine(x1, pozicija.b+i * karkasoLangai[0][0].aukstis, x2, pozicija.b+i * karkasoLangai[0][0].aukstis);
    }
    public void atnaujinti() {
        for(int x = 0; x < karkasoLangai.length; x++) {
            for (int y = 0; y < karkasoLangai[0].length; y++) {
                karkasoLangai[x][y].atnaujinti();
            }
        }
        ejimonumeris = 0;
        AtnaujintiGalimusEjimus(1);
    }
    public List<Pozicija> gautiVisusGalimusEjimus() {
        return galimiEjimai;
    }
    public void ZaistiEjima(Pozicija pozicija, int zaidejas) {
        ejimonumeris++;
        karkasoLangai[pozicija.a][pozicija.b].nustatytiLangelioBusena(zaidejas);
        List<Pozicija> changeCellPositions = gautiPakeistasPozicijasKitamJudesiui(pozicija, zaidejas);
        for(Pozicija pakeistiPozicija : changeCellPositions) {
            karkasoLangai[pakeistiPozicija.a][pakeistiPozicija.b].nustatytiLangelioBusena(zaidejas);
        }
        AtnaujintiGalimusEjimus(zaidejas == 1 ? 2 : 1);
    }
    public void nuspalvinti(Graphics grafika) {
        nuspalvintiKarkasoLinijas(grafika);
        for(int x = 0; x < karkasoLangai.length; x++) {
            for (int y = 0; y < karkasoLangai[0].length; y++) {
                karkasoLangai[x][y].nuspalvinti(grafika);
            }
        }
    }
    public boolean GalimasEjimas(Pozicija pozicija) {
        return gautiVisusGalimusEjimus().contains(pozicija);
    }
    public int gautiLaimetoja(boolean yradarGalimiEjimai) {
        int[] skaicius = new int[3];
        for(int y = 0; y < karkasoLangai[0].length; y++) {
            for(int x = 0; x < karkasoLangai.length; x++) {
                skaicius[karkasoLangai[x][y].gautiLangelioBusena()]++;
            }
        }

        if(yradarGalimiEjimai && skaicius[0] > 0) return 0;
        else if(skaicius[1] == skaicius[2]) return 3;
        else return skaicius[1] > skaicius[2] ? 1 : 2;
    }
    public void AtnaujintiGalimusEjimus(int zaidejoID) {
        for(Pozicija galimasEjimas : galimiEjimai) {
            karkasoLangai[galimasEjimas.a][galimasEjimas.b].NustatytiRibojima(false);
        }
        galimiEjimai.clear();
        if(ejimonumeris < 4) {
            int vidurkisHorizantalus = karkasoLangai.length/2-1;
            int vidurkisVertikalus = karkasoLangai[0].length/2-1;
            for (int a = vidurkisHorizantalus; a < vidurkisHorizantalus+2; a++) {
                for (int b = vidurkisVertikalus; b < vidurkisVertikalus+2; b++) {
                    if (karkasoLangai[a][b].gautiLangelioBusena() == 0) {
                        galimiEjimai.add(new Pozicija(a, b));
                    }
                }
            }
        } else {
            for (int a = 0; a < karkasoLangai.length; a++) {
                for (int b = 0; b < karkasoLangai[0].length; b++) {
                    if (karkasoLangai[a][b].gautiLangelioBusena() == 0 && gautiPakeistasPozicijasKitamJudesiui(new Pozicija(a, b),zaidejoID).size()>0) {
                        galimiEjimai.add(new Pozicija(a, b));
                    }
                }
            }
        }
        for(Pozicija validMove : galimiEjimai) {
            karkasoLangai[validMove.a][validMove.b].NustatytiRibojima(true);
        }
    }
    private List<Pozicija> gautiPakeistasPozicijasPoZaidejoJudejimo(Pozicija pozicija, int zaidejoID, Pozicija kryptis) {
        List<Pozicija> rezultatas = new ArrayList<>();
        Pozicija ejimoPozicija = new Pozicija(pozicija);
        int kitasZaidejas = zaidejoID == 1 ? 2 : 1;
        ejimoPozicija.prideti(kryptis);
        while(YraLentelesRibose(ejimoPozicija) && karkasoLangai[ejimoPozicija.a][ejimoPozicija.b].gautiLangelioBusena() == kitasZaidejas) {
            rezultatas.add(new Pozicija(ejimoPozicija));
            ejimoPozicija.prideti(kryptis);
        }
        if(!YraLentelesRibose(ejimoPozicija) || karkasoLangai[ejimoPozicija.a][ejimoPozicija.b].gautiLangelioBusena() != zaidejoID) {
            rezultatas.clear();
        }
        return rezultatas;
    }
    public List<Pozicija> gautiPakeistasPozicijasKitamJudesiui(Pozicija pozicija, int zaidejoID) {
        List<Pozicija> rezultatas = new ArrayList<>();
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.DOWN));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.LEFT));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.UP));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.RIGHT));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.UPRIGHT));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.DOWNRIGHT));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.UPLEFT));
        rezultatas.addAll(gautiPakeistasPozicijasPoZaidejoJudejimo(pozicija, zaidejoID, Pozicija.DOWNLEFT));
        return rezultatas;
    }
    private boolean YraLentelesRibose(Pozicija pozicija) {
        return !(pozicija.a < 0 || pozicija.b < 0 || pozicija.a >= karkasoLangai.length || pozicija.b >= karkasoLangai[0].length);
    }
}
