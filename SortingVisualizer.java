import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortingVisualizer extends JFrame {

    private JPanel optionsPanel;
    private DrawPanel drawPanel;
    private JComboBox<String> algorithmComboBox;
    private JSlider speedSlider;
    private JSlider minSizeSlider;
    private JSlider maxSizeSlider;
    private JSlider arraySizeSlider;
    private List<Integer> array;
    private String selectedAlgorithm = "Merge Sort"; // Default value
    private int sortingSpeed;

    public SortingVisualizer() {
        setTitle("DSA Project Sorting Algorithm Visualizer");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        optionsPanel = new JPanel();
        optionsPanel.setPreferredSize(new Dimension(700, 150));
        optionsPanel.setBackground(Color.GREEN);
        add(optionsPanel, BorderLayout.NORTH);

        drawPanel = new DrawPanel();
        drawPanel.setPreferredSize(new Dimension(700, 450));
        drawPanel.setBackground(Color.GRAY);
        add(drawPanel, BorderLayout.CENTER);

        setupOptionsPanel();
    }

    private void setupOptionsPanel() {
        JLabel algorithmLabel = new JLabel("Algorithm Choice: ");
        optionsPanel.add(algorithmLabel);

        String[] algorithms = {"Merge Sort", "Insertion Sort"};
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.addActionListener(e -> selectedAlgorithm = (String) algorithmComboBox.getSelectedItem());
        optionsPanel.add(algorithmComboBox);

        speedSlider = new JSlider(1, 20, 10);
        speedSlider.setPaintLabels(true);
        speedSlider.setPaintTicks(true);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setLabelTable(speedSlider.createStandardLabels(1));
        optionsPanel.add(new JLabel("Sorting Speed"));
        optionsPanel.add(speedSlider);

        minSizeSlider = new JSlider(5, 20, 5);
        minSizeSlider.setPaintLabels(true);
        minSizeSlider.setPaintTicks(true);
        minSizeSlider.setMajorTickSpacing(1);
        minSizeSlider.setLabelTable(minSizeSlider.createStandardLabels(1));
        optionsPanel.add(new JLabel("Min Size"));
        optionsPanel.add(minSizeSlider);

        maxSizeSlider = new JSlider(20, 100, 20);
        maxSizeSlider.setPaintLabels(true);
        maxSizeSlider.setPaintTicks(true);
        maxSizeSlider.setMajorTickSpacing(10);
        maxSizeSlider.setLabelTable(maxSizeSlider.createStandardLabels(10));
        optionsPanel.add(new JLabel("Max Size"));
        optionsPanel.add(maxSizeSlider);

        arraySizeSlider = new JSlider(3, 25, 3);
        arraySizeSlider.setPaintLabels(true);
        arraySizeSlider.setPaintTicks(true);
        arraySizeSlider.setMajorTickSpacing(1);
        arraySizeSlider.setLabelTable(arraySizeSlider.createStandardLabels(1));
        optionsPanel.add(new JLabel("Array Size"));
        optionsPanel.add(arraySizeSlider);

        JButton generateArrayButton = new JButton("Generate Array");
        generateArrayButton.addActionListener(e -> generateArray());
        optionsPanel.add(generateArrayButton);

        JButton startSortingButton = new JButton("Start Sorting");
        startSortingButton.addActionListener(e -> startSorting());
        optionsPanel.add(startSortingButton);
    }

    private void generateArray() {
        int lowest = minSizeSlider.getValue();
        int highest = maxSizeSlider.getValue();
        int size = arraySizeSlider.getValue();

        array = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            array.add(rand.nextInt(highest - lowest + 1) + lowest);
        }

        drawPanel.setArray(array);
        drawPanel.repaint();
    }

    private void startSorting() {
        sortingSpeed = 2000 - speedSlider.getValue() * 100;
        Thread sortingThread = new Thread(() -> {
            if ("Merge Sort".equals(selectedAlgorithm)) {
                mergeSort(0, array.size() - 1);
            } else if ("Insertion Sort".equals(selectedAlgorithm)) {
                insertionSort();
            }
        });
        sortingThread.start();
    }

    private void mergeSort(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(left, mid);
            mergeSort(mid + 1, right);
            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Integer> L = new ArrayList<>(n1);
        List<Integer> R = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            L.add(array.get(left + i));
        }
        for (int i = 0; i < n2; i++) {
            R.add(array.get(mid + 1 + i));
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L.get(i) <= R.get(j)) {
                array.set(k, L.get(i));
                i++;
            } else {
                array.set(k, R.get(j));
                j++;
            }
            k++;
            drawPanel.setArray(array);
            drawPanel.repaint();
            try {
                Thread.sleep(sortingSpeed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        while (i < n1) {
            array.set(k, L.get(i));
            i++;
            k++;
            drawPanel.setArray(array);
            drawPanel.repaint();
            try {
                Thread.sleep(sortingSpeed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        while (j < n2) {
            array.set(k, R.get(j));
            j++;
            k++;
            drawPanel.setArray(array);
            drawPanel.repaint();
            try {
                Thread.sleep(sortingSpeed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void insertionSort() {
        int n = array.size();
        for (int i = 1; i < n; ++i) {
            int key = array.get(i);
            int j = i - 1;

            while (j >= 0 && array.get(j) > key) {
                array.set(j + 1, array.get(j));
                j = j - 1;
                drawPanel.setArray(array);
                drawPanel.repaint();
                try {
                    Thread.sleep(sortingSpeed);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            array.set(j + 1, key);
            drawPanel.setArray(array);
            drawPanel.repaint();
            try {
                Thread.sleep(sortingSpeed);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class DrawPanel extends JPanel {
        private List<Integer> array;

        public void setArray(List<Integer> array) {
            this.array = array;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (array != null) {
                int width = getWidth();
                int height = getHeight();
                int barWidth = width / array.size();
                int maxVal = array.stream().max(Integer::compare).orElse(1);

                for (int i = 0; i < array.size(); i++) {
                    int value = array.get(i);
                    int barHeight = (int) ((double) value / maxVal * height);
                    g.setColor(Color.BLUE);
                    g.fillRect(i * barWidth, height - barHeight, barWidth, barHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(i * barWidth, height - barHeight, barWidth, barHeight);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingVisualizer visualizer = new SortingVisualizer();
            visualizer.setVisible(true);
        });
    }
}
