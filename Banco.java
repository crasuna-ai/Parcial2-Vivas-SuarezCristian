import java.util.Stack;
import javax.swing.JOptionPane;

class Credito {
    private String usuario;
    private String tipoCredito;
    private double monto;

    public Credito(String usuario, String tipoCredito, double monto) {
        this.usuario = usuario;
        this.tipoCredito = tipoCredito;
        this.monto = monto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTipoCredito() {
        return tipoCredito;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Usuario: " + usuario + ", Tipo: " + tipoCredito + ", Monto: $" + monto;
    }
}


class Banco {
    private Stack<Credito> creditos = new Stack<>();

    
    public void mostrarMenu() {
        while (true) {
            String opcionStr = JOptionPane.showInputDialog(
                    "Menú del Banco\n" +
                    "1. Registrar crédito\n" +
                    "2. Mostrar créditos\n" +
                    "3. Vender crédito\n" +
                    "4. Eliminar crédito\n" +
                    "5. Salir\n" +
                    "Seleccione una opción:");
            
            if (opcionStr == null) break; 

            try {
                int opcion = Integer.parseInt(opcionStr);

                switch (opcion) {
                    case 1 -> registrarCredito();
                    case 2 -> mostrarCreditos();
                    case 3 -> venderCredito();
                    case 4 -> eliminarCredito();
                    case 5 -> {
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                        return;
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
            }
        }
    }

    
    public void registrarCredito() {
        String usuario = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
        if (usuario == null || usuario.trim().isEmpty()) return;

        String tipo = JOptionPane.showInputDialog("Ingrese el tipo de crédito:");
        if (tipo == null || tipo.trim().isEmpty()) return;

        double monto;
        try {
            monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto del crédito:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
            return;
        }

        creditos.push(new Credito(usuario, tipo, monto));
        JOptionPane.showMessageDialog(null, "Crédito registrado con éxito.");
    }

    
    public void mostrarCreditos() {
        if (creditos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay créditos registrados.");
            return;
        }

        StringBuilder lista = new StringBuilder("Créditos Registrados:\n");
        for (Credito c : creditos) {
            lista.append(c.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    
    public void venderCredito() {
        if (creditos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay créditos disponibles.");
            return;
        }

        String usuario = JOptionPane.showInputDialog("Ingrese el usuario que solicita el crédito:");
        if (usuario == null || usuario.trim().isEmpty()) return;

        String tipo = JOptionPane.showInputDialog("Ingrese el tipo de crédito que solicita:");
        if (tipo == null || tipo.trim().isEmpty()) return;

        double monto;
        try {
            monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto solicitado:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Monto inválido.");
            return;
        }

        Stack<Credito> aux = new Stack<>();
        boolean encontrado = false;

        while (!creditos.isEmpty()) {
            Credito c = creditos.pop();
            if (c.getUsuario().equalsIgnoreCase(usuario)) {
                if (c.getTipoCredito().equalsIgnoreCase(tipo)) {
                    c.setMonto(c.getMonto() + monto);
                    JOptionPane.showMessageDialog(null, "Crédito actualizado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario tiene un crédito de tipo diferente. Se creará un nuevo crédito.");
                    aux.push(new Credito(usuario, tipo, monto));
                }
                encontrado = true;
            }
            aux.push(c);
        }

        while (!aux.isEmpty()) {
            creditos.push(aux.pop());
        }

        if (!encontrado) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }

    
    public void eliminarCredito() {
        if (creditos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay créditos para eliminar.");
            return;
        }

        String usuario = JOptionPane.showInputDialog("Ingrese el usuario cuyo crédito desea eliminar:");
        if (usuario == null || usuario.trim().isEmpty()) return;

        Stack<Credito> aux = new Stack<>();
        boolean eliminado = false;

        while (!creditos.isEmpty()) {
            Credito c = creditos.pop();
            if (!c.getUsuario().equalsIgnoreCase(usuario)) {
                aux.push(c);
            } else {
                eliminado = true;
            }
        }

        while (!aux.isEmpty()) {
            creditos.push(aux.pop());
        }

        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Crédito eliminado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.");
        }
    }
}
