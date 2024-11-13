import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {
    private Socket cliente;
    private int numero;
    private int intentosMaximos;
    private int intentosCliente;

    public Servidor(Socket cliente, int numero, int intentosMaximos) {
        this.cliente = cliente;
        this.numero = numero;
        this.intentosMaximos = intentosMaximos;
        this.intentosCliente = 0;
    }

    @Override
    public void run() {
        try {
            BufferedReader lectorEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter escritorSalida = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

            int numeroCliente = 0;
            String respuesta = "";
            while (!respuesta.contains("finalizar: ")) {
                numeroCliente = Integer.valueOf(lectorEntrada.readLine());
                this.intentosCliente++;

                if (this.intentosCliente < this.intentosMaximos) {
                    if (numeroCliente > numero) {
                        respuesta = "Tu número es mayor al buscado (Intentos restantes: " + (this.intentosMaximos - this.intentosCliente) + ")";
                    } else if (numeroCliente < numero) {
                        respuesta = "Tu número es menor al buscado (Intentos restantes: " + (this.intentosMaximos - this.intentosCliente) + ")";
                    } else {
                        respuesta = "finalizar: ¡Has encontrado el número!";
                        break;
                    }
                } else {
                    respuesta = "finalizar: Has alcanzado el máximo de intentos, el número era: " + this.numero;
                }

                escritorSalida.write(respuesta);
                escritorSalida.newLine();
                escritorSalida.flush();
            }

            lectorEntrada.close();
            escritorSalida.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}