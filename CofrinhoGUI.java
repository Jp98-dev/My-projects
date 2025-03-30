import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Classe abstrata representando uma moeda genérica
abstract class Moeda {
    protected double valor; // Valor da moeda

    public Moeda(double valor) {
        this.valor = valor; // Define o valor inicial da moeda
    }

    // Método abstrato para conversão para Real
    public abstract double converterParaReal();

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + valor; // Retorna o nome da classe e o valor da moeda
    }
}

// Subclasse específica para Real
class Real extends Moeda {
    public Real(double valor) {
        super(valor);
    }

    @Override
    public double converterParaReal() {
        return valor; // Como já está em Real, apenas retorna o valor
    }
}

// Subclasse específica para Dólar
class Dolar extends Moeda {
    private static final double COTACAO = 5.0; // Taxa de conversão para Real

    public Dolar(double valor) {
        super(valor);
    }

    @Override
    public double converterParaReal() {
        return valor * COTACAO; // Converte o valor do Dólar para Real
    }
}

// Subclasse específica para Euro
class Euro extends Moeda {
    private static final double COTACAO = 5.5; // Taxa de conversão para Real

    public Euro(double valor) {
        super(valor);
    }

    @Override
    public double converterParaReal() {
        return valor * COTACAO; // Converte o valor do Euro para Real
    }
}

// Classe que representa o Cofrinho e gerencia as moedas
class Cofrinho {
    private ArrayList<Moeda> moedas; // Lista de moedas no cofrinho

    public Cofrinho() {
        moedas = new ArrayList<>();
    }

    // Adiciona uma moeda ao cofrinho
    public void adicionarMoeda(Moeda novaMoeda) {
        for (Moeda moeda : moedas) {
            if (moeda.getClass() == novaMoeda.getClass()) {
                moeda.valor += novaMoeda.valor; // Se a moeda já existe, soma ao valor existente
                return;
            }
        }
        moedas.add(novaMoeda); // Caso não exista, adiciona uma nova moeda à lista
    }

    // Remove um valor específico de uma moeda existente
    public boolean removerValorMoeda(Class<? extends Moeda> tipoMoeda, double valor) {
        for (Moeda moeda : moedas) {
            if (moeda.getClass() == tipoMoeda) {
                if (moeda.valor >= valor) {
                    moeda.valor -= valor; // Subtrai o valor desejado
                    if (moeda.valor == 0) {
                        moedas.remove(moeda); // Remove a moeda se o valor for 0
                    }
                    return true;
                }
            }
        }
        return false;
    }

    // Retorna uma string com a listagem de todas as moedas
    public String listarMoedas() {
        if (moedas.isEmpty()) {
            return "O cofrinho está vazio.";
        }
        StringBuilder lista = new StringBuilder();
        for (Moeda moeda : moedas) {
            lista.append(moeda).append("\n");
        }
        return lista.toString();
    }

    // Calcula o valor total do cofrinho em Reais
    public double calcularTotalEmReais() {
        double total = 0;
        for (Moeda moeda : moedas) {
            total += moeda.converterParaReal();
        }
        return total;
    }
}

// Classe responsável pela Interface Gráfica do Cofrinho
public class CofrinhoGUI extends JFrame {
    private Cofrinho cofrinho; // Instância do Cofrinho
    private JTextArea textArea; // Área de texto para exibir as moedas
    private JLabel totalLabel; // Label para exibir o total em reais

    public CofrinhoGUI() {
        cofrinho = new Cofrinho(); // Cria um novo cofrinho

        // Configuração da janela
        setTitle("Cofrinho de Moedas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Área de texto onde as moedas serão listadas
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Painel de botões para as ações do usuário
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 5, 5));

        // Botões para adicionar moedas, remover valores e calcular o total
        JButton addReal = new JButton("Adicionar Real");
        JButton addDolar = new JButton("Adicionar Dólar");
        JButton addEuro = new JButton("Adicionar Euro");
        JButton removerMoeda = new JButton("Remover Valor");
        JButton calcularTotal = new JButton("Calcular Total");

        // Adiciona os botões ao painel
        panel.add(addReal);
        panel.add(addDolar);
        panel.add(addEuro);
        panel.add(removerMoeda);
        panel.add(calcularTotal);

        add(panel, BorderLayout.SOUTH);

        // Label que exibe o total em reais
        totalLabel = new JLabel("Total em reais: R$ 0.00");
        add(totalLabel, BorderLayout.NORTH);

        // Ações dos botões
        addReal.addActionListener(e -> adicionarMoeda(new Real(lerValor("Real"))));
        addDolar.addActionListener(e -> adicionarMoeda(new Dolar(lerValor("Dólar"))));
        addEuro.addActionListener(e -> adicionarMoeda(new Euro(lerValor("Euro"))));

        removerMoeda.addActionListener(e -> removerValorMoeda());
        calcularTotal.addActionListener(e -> calcularTotal());

        atualizarLista();
        setVisible(true);
    }

    // Método para ler o valor da moeda digitado pelo usuário
    private double lerValor(String tipo) {
        String input = JOptionPane.showInputDialog(this, "Digite o valor da moeda em " + tipo + ":");
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido! Use um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    // Método para adicionar uma moeda ao cofrinho
    private void adicionarMoeda(Moeda moeda) {
        if (moeda.valor > 0) {
            cofrinho.adicionarMoeda(moeda);
            atualizarLista();
        }
    }

    // Método para remover um valor específico de uma moeda
    private void removerValorMoeda() {
        String[] opcoes = {"Real", "Dólar", "Euro"};
        String escolha = (String) JOptionPane.showInputDialog(this, "Escolha a moeda para remover valor:", "Remover Valor",
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha != null) {
            double valor = lerValor("valor a remover de " + escolha);
            Class<? extends Moeda> tipoMoeda = switch (escolha) {
                case "Real" -> Real.class;
                case "Dólar" -> Dolar.class;
                case "Euro" -> Euro.class;
                default -> null;
            };

            if (tipoMoeda != null) {
                boolean removido = cofrinho.removerValorMoeda(tipoMoeda, valor);
                if (!removido) {
                    JOptionPane.showMessageDialog(this, "Valor inválido ou insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                atualizarLista();
            }
        }
    }

    // Método para calcular o total do cofrinho e atualizar a interface
    private void calcularTotal() {
        double total = cofrinho.calcularTotalEmReais();
        totalLabel.setText("Total em reais: R$ " + String.format("%.2f", total));
    }

    // Atualiza a área de texto com a lista de moedas
    private void atualizarLista() {
        textArea.setText(cofrinho.listarMoedas());
    }

    // Método principal que inicia a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CofrinhoGUI::new);
    }
}
