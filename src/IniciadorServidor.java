import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IniciadorServidor {
    public int generarNumeroAleatorio() {
        return (int) (Math.random() * 100);
    }

    public static void main(String[] args) throws IOException {

        ServerSocket servidorSocket = null;
        try {
            servidorSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado en el puerto 12345");

            while (true) {
                int numero = new IniciadorServidor().generarNumeroAleatorio();
                Socket socketCliente = servidorSocket.accept();
                System.out.println("Cliente conectado");
                System.out.println("El número a adivinar es: " + numero);


                Servidor server  = new Servidor(socketCliente, numero, 5);
                new Thread(server).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (servidorSocket != null) {
                servidorSocket.close();
            }
        }
    }
}
