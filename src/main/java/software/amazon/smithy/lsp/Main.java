/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package software.amazon.smithy.lsp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import org.eclipse.lsp4j.jsonrpc.Launcher;

public class Main {
  public String getGreeting() {
    return "Hello world.";
  }

  public static void main(String[] args) {

    Socket socket = null;

    try {
      String port = args[0];
      socket = new Socket("localhost", Integer.parseInt(port));

      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();
      SmithyLanguageServer server = new SmithyLanguageServer();
      Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, in, out);
      LanguageClient client = launcher.getRemoteProxy();

      server.connect(client);

      launcher.startListening().get();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Missing port argument");
    } catch (NumberFormatException e) {
      System.out.println("Port number must be a valid integer");
    } catch (Exception e) {
      System.out.println(e);

      e.printStackTrace();
    } finally {
      try {
        if (socket != null)
          socket.close();
      } catch (Exception e) {
        System.out.println("Failed to close the socket");
        System.out.println(e);
      }
    }

  }
}