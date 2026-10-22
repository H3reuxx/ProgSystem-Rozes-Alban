import java.net.*;
import java.io.*;
import java.util.*;

public class ServeurChat {
    private static final List<PrintWriter> clients = 
        Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Serveur Chat démarré");

        while (true) {
            Socket client = serverSocket.accept();
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            synchronized (clients) {
                clients.add(out);
            }
            System.out.println("Client connecté. Total : " + clients.size());
            new Thread(new ChatHandler(client, out)).start();
        }
    }

    static class ChatHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;

        public ChatHandler(Socket socket, PrintWriter out) {
            this.socket = socket;
            this.out = out;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );

                String ligne;
                while ((ligne = in.readLine()) != null) {
                    System.out.println("Message reçu : " + ligne);
                    broadcast(ligne);
                }
            } catch (IOException e) {
                System.out.println("Erreur sur les IO.");
            } finally {
                synchronized (clients) {
                    clients.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException ignored) {}
                System.out.println("Client déconnecté. Total : " + clients.size());
            }
        }

        private void broadcast(String message) {
            synchronized (clients) {
                for (PrintWriter writer : clients) {
                    writer.println("-> " + message);
                }
            }
        }
    }
}
