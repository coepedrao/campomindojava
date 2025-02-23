import java.util.Random;
import java.util.Scanner;

public class CampoMinado {
    private static final int TAMANHO = 8;
    private static final int MINAS = 10;
    private static char[][] tabuleiro = new char[TAMANHO][TAMANHO];
    private static boolean[][] minas = new boolean[TAMANHO][TAMANHO];
    private static boolean[][] revelado = new boolean[TAMANHO][TAMANHO];

    public static void main(String[] args) {
        inicializarTabuleiro();
        posicionarMinas();
        jogar();
    }

    private static void inicializarTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiro[i][j] = '-';
            }
        }
    }

    private static void posicionarMinas() {
        Random rand = new Random();
        int colocadas = 0;
        while (colocadas < MINAS) {
            int x = rand.nextInt(TAMANHO);
            int y = rand.nextInt(TAMANHO);
            if (!minas[x][y]) {
                minas[x][y] = true;
                colocadas++;
            }
        }
    }

    private static void jogar() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            imprimirTabuleiro();
            System.out.print("Digite linha e coluna (ex: 3 4): ");
            int linha = scanner.nextInt();
            int coluna = scanner.nextInt();

            if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
                System.out.println("Coordenadas inválidas!");
                continue;
            }

            if (minas[linha][coluna]) {
                System.out.println("BOOM! Você perdeu!");
                revelarTodasMinas();
                imprimirTabuleiro();
                break;
            }
            revelarCelula(linha, coluna);

            if (checarVitoria()) {
                System.out.println("Parabéns! Você venceu!");
                break;
            }
        }
        scanner.close();
    }

    private static void imprimirTabuleiro() {
        System.out.print("  ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO; j++) {
                if (revelado[i][j]) {
                    System.out.print(tabuleiro[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    private static void revelarCelula(int x, int y) {
        if (revelado[x][y]) return;
        revelado[x][y] = true;

        int minasAoRedor = contarMinasAoRedor(x, y);
        tabuleiro[x][y] = (char) ('0' + minasAoRedor);

        if (minasAoRedor == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int nx = x + i, ny = y + j;
                    if (nx >= 0 && nx < TAMANHO && ny >= 0 && ny < TAMANHO) {
                        revelarCelula(nx, ny);
                    }
                }
            }
        }
    }

    private static int contarMinasAoRedor(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nx = x + i, ny = y + j;
                if (nx >= 0 && nx < TAMANHO && ny >= 0 && ny < TAMANHO && minas[nx][ny]) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean checarVitoria() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (!minas[i][j] && !revelado[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void revelarTodasMinas() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (minas[i][j]) {
                    tabuleiro[i][j] = '*';
                    revelado[i][j] = true;
                }
            }
        }
    }
}


  

