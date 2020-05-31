import java.io.Closeable;
import java.io.IOException;

public interface IMessageService extends Closeable {

    void sendMessage(String message);

    void readMessage(String message);



}
