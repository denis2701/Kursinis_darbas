public class Pozicija {
    public static final Pozicija DOWN = new Pozicija(0,-1);
    public static final Pozicija UP = new Pozicija(0,1);
    public static final Pozicija LEFT = new Pozicija(-1,0);
    public static final Pozicija UPRIGHT = new Pozicija(1,1);
    public static final Pozicija DOWNRIGHT = new Pozicija(1,-1);
    public static final Pozicija UPLEFT = new Pozicija(-1,1);
    public static final Pozicija DOWNLEFT = new Pozicija(-1,-1);
    public static final Pozicija RIGHT = new Pozicija(1,0);

    public int a;
    public int b;
    public Pozicija(int a, int b) {
        this.a = a;
        this.b = b;
    }
    public Pozicija(Pozicija pozicijaKopijavimui) {
        this.a = pozicijaKopijavimui.a;
        this.b = pozicijaKopijavimui.b;
    }
    public void prideti (Pozicija kitaPozicija) {
        this.a += kitaPozicija.a;
        this.b += kitaPozicija.b;
    }

    @Override
    public boolean equals(Object objektas) {
        if (this == objektas) return true;
        if (objektas == null || getClass() != objektas.getClass()) return false;
        Pozicija pozicija = (Pozicija) objektas;
        return a == pozicija.a && b == pozicija.b;
    }

    @Override
    public String toString() {
        return "Pozicija{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}