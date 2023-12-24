import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class GolfHost {
	
	private ArrayList<SocketStream> s;
	
	
	public GolfHost() {
		
	}

	
	
	class SocketStream {
		
		private Socket s;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		
		
		public SocketStream(Socket socket) {
			s = socket;
			try {
				ois = new ObjectInputStream(s.getInputStream());
				oos = new ObjectOutputStream(s.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public Socket getSocket() {
			return s;
		}

		public ObjectInputStream getOis() {
			return ois;
		}

		public ObjectOutputStream getOos() {
			return oos;
		}
		
		
	}

}