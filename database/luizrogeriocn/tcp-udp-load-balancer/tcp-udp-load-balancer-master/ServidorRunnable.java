import java.io.*;
import java.net.*;

public class ServidorRunnable implements Runnable{

    protected Socket connectionSocket = null;
    public ListaDeCantinas lista;
    public Mensagem msg;
}