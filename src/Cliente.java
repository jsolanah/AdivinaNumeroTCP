import javax.management.StringValueExp;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            BufferedWriter escritorSalida = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader lectorEntrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner leerNumero = new Scanner(System.in);

            String respuesta = "";

            while (respuesta != null && !respuesta.contains("finalizar: ")) {
                System.out.print("Introduzca un número (0 - 100): ");
                String numero = leerNumero.nextLine();

                while (numero.equals("") || !numero.matches("\\d+")) {
                    System.out.println("Ese no es un número válido");
                    System.out.print("Introduzca un número (0 - 100): ");
                    numero = leerNumero.nextLine();
                }

                escritorSalida.write(numero);
                escritorSalida.newLine();
                escritorSalida.flush();

                respuesta = lectorEntrada.readLine();

                if (respuesta != null) {
                    System.out.println(respuesta.replace("finalizar: ", ""));
                }
            }

            leerNumero.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}