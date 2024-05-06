import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clase para representar un nodo en el árbol
class Nodo {
    int dato;
    Nodo izquierdo, derecho;

    // Constructor
    public Nodo(int dato) {
        this.dato = dato;
        izquierdo = derecho = null;
    }
}

// Clase para representar el árbol binario de búsqueda
class ArbolBinarioBusqueda {
    Nodo raiz;

    // Constructor
    public ArbolBinarioBusqueda() {
        raiz = null;
    }

    // Método para insertar un dato en el árbol
    public void insertar(int dato) {
        raiz = insertarRec(raiz, dato);
    }

    // Función auxiliar para insertar un dato recursivamente
    private Nodo insertarRec(Nodo raiz, int dato) {
        // Si el árbol está vacío, se crea un nuevo nodo
        if (raiz == null) {
            raiz = new Nodo(dato);
            return raiz;
        }

        // Si el dato es menor que el valor del nodo actual, se inserta en el subárbol izquierdo
        if (dato < raiz.dato)
            raiz.izquierdo = insertarRec(raiz.izquierdo, dato);
        // Si el dato es mayor que el valor del nodo actual, se inserta en el subárbol derecho
        else if (dato > raiz.dato)
            raiz.derecho = insertarRec(raiz.derecho, dato);

        return raiz;
    }

    // Método para realizar el recorrido inorden
    public void inOrden(StringBuilder sb, Nodo raiz) {
        if (raiz != null) {
            inOrden(sb, raiz.izquierdo);
            sb.append(raiz.dato).append(" ");
            inOrden(sb, raiz.derecho);
        }
    }

    // Método para realizar el recorrido preorden
    public void preOrden(StringBuilder sb, Nodo raiz) {
        if (raiz != null) {
            sb.append(raiz.dato).append(" ");
            preOrden(sb, raiz.izquierdo);
            preOrden(sb, raiz.derecho);
        }
    }

    // Método para realizar el recorrido postorden
    public void postOrden(StringBuilder sb, Nodo raiz) {
        if (raiz != null) {
            postOrden(sb, raiz.izquierdo);
            postOrden(sb, raiz.derecho);
            sb.append(raiz.dato).append(" ");
        }
    }

    // Método para buscar un dato en el árbol
    public boolean buscar(int dato) {
        return buscarRec(raiz, dato);
    }

    // Función auxiliar para buscar un dato recursivamente
    private boolean buscarRec(Nodo raiz, int dato) {
        if (raiz == null)
            return false;
        if (raiz.dato == dato)
            return true;
        if (dato < raiz.dato)
            return buscarRec(raiz.izquierdo, dato);
        return buscarRec(raiz.derecho, dato);
    }

    // Método para eliminar un dato del árbol
    public void eliminar(int dato) {
        raiz = eliminarRec(raiz, dato);
    }

    // Función auxiliar para eliminar un dato recursivamente
    private Nodo eliminarRec(Nodo raiz, int dato) {
        if (raiz == null)
            return raiz;

        if (dato < raiz.dato)
            raiz.izquierdo = eliminarRec(raiz.izquierdo, dato);
        else if (dato > raiz.dato)
            raiz.derecho = eliminarRec(raiz.derecho, dato);
        else {
            if (raiz.izquierdo == null)
                return raiz.derecho;
            else if (raiz.derecho == null)
                return raiz.izquierdo;

            raiz.dato = minValor(raiz.derecho);
            raiz.derecho = eliminarRec(raiz.derecho, raiz.dato);
        }
        return raiz;
    }

    // Función para encontrar el valor mínimo en un subárbol
    private int minValor(Nodo raiz) {
        int minv = raiz.dato;
        while (raiz.izquierdo != null) {
            minv = raiz.izquierdo.dato;
            raiz = raiz.izquierdo;
        }
        return minv;
    }

    // Método para obtener la altura del árbol
    public int obtenerAltura(Nodo nodo) {
        if (nodo == null)
            return 0;
        else {
            int alturaIzquierda = obtenerAltura(nodo.izquierdo);
            int alturaDerecha = obtenerAltura(nodo.derecho);
            return Math.max(alturaIzquierda, alturaDerecha) + 1;
        }
    }
}

// Clase para la interfaz gráfica de usuario
class GUI extends JFrame {
    private ArbolBinarioBusqueda arbol;
    private JTextArea textoArea;

    public GUI() {
        arbol = new ArbolBinarioBusqueda();
        configurarGUI();
    }

    private void configurarGUI() {
        setTitle("Árbol Binario de Búsqueda");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        JButton botonInsertar = new JButton("Insertar");
        JButton botonBuscar = new JButton("Buscar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonInOrden = new JButton("In-orden");
        JButton botonPreOrden = new JButton("Pre-orden");
        JButton botonPostOrden = new JButton("Post-orden");
        JButton botonVisualizarArbol = new JButton("Visualizar Árbol");
        panelBotones.add(botonInsertar);
        panelBotones.add(botonBuscar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonInOrden);
        panelBotones.add(botonPreOrden);
        panelBotones.add(botonPostOrden);
        panelBotones.add(botonVisualizarArbol);

        // Manejadores de eventos para los botones
        botonInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String datoStr = JOptionPane.showInputDialog("Ingrese el dato a insertar:");
                int dato = Integer.parseInt(datoStr);
                arbol.insertar(dato);
                actualizarTextoArea();
            }
        });

        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String datoStr = JOptionPane.showInputDialog("Ingrese el dato a buscar:");
                int dato = Integer.parseInt(datoStr);
                boolean encontrado = arbol.buscar(dato);
                if (encontrado)
                    JOptionPane.showMessageDialog(null, "El dato " + dato + " está en el árbol.");
                else
                    JOptionPane.showMessageDialog(null, "El dato " + dato + " no está en el árbol.");
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String datoStr = JOptionPane.showInputDialog("Ingrese el dato a eliminar:");
                int dato = Integer.parseInt(datoStr);
                arbol.eliminar(dato);
                actualizarTextoArea();
            }
        });

        botonInOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                arbol.inOrden(sb, arbol.raiz);
                JOptionPane.showMessageDialog(null, "Recorrido In-orden:\n" + sb.toString());
            }
        });

        botonPreOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                arbol.preOrden(sb, arbol.raiz);
                JOptionPane.showMessageDialog(null, "Recorrido Pre-orden:\n" + sb.toString());
            }
        });

        botonPostOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                arbol.postOrden(sb, arbol.raiz);
                JOptionPane.showMessageDialog(null, "Recorrido Post-orden:\n" + sb.toString());
            }
        });

        botonVisualizarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameArbol = new JFrame("Visualización del Árbol");
                frameArbol.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frameArbol.setSize(600, 400);
                frameArbol.setLocationRelativeTo(null);

                JPanel panelArbol = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        dibujarArbol(g, getWidth() / 2, 30, arbol.raiz, getWidth() / 4, 0);
                    }
                };

                frameArbol.add(panelArbol);
                frameArbol.setVisible(true);
            }
        });

        // Panel para el área de texto
        textoArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textoArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Agregar componentes al marco
        add(panelBotones, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void actualizarTextoArea() {
        textoArea.setText("");
        textoArea.append("Recorrido inorden del árbol:\n");
        StringBuilder sb = new StringBuilder();
        arbol.inOrden(sb, arbol.raiz);
        textoArea.append(sb.toString());
    }

    private void dibujarArbol(Graphics g, int x, int y, Nodo nodo, int espacio, int nivel) {
        if (nodo != null) {
            g.setColor(Color.WHITE);
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.drawOval(x - 15, y - 15, 30, 30);
            g.drawString(Integer.toString(nodo.dato), x - 5, y + 5);
            if (nodo.izquierdo != null) {
                g.drawLine(x, y, x - espacio, y + 80);
                dibujarArbol(g, x - espacio, y + 80, nodo.izquierdo, espacio / 2, nivel + 1);
            }
            if (nodo.derecho != null) {
                g.drawLine(x, y, x + espacio, y + 80);
                dibujarArbol(g, x + espacio, y + 80, nodo.derecho, espacio / 2, nivel + 1);
            }
        }
    }
}

// Clase principal
public class Arboles {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
