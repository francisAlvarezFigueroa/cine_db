package app;
import view.MainFrame;

public class Main {
    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(()->{
            try{
                dao.DatabaseConnection.getConnection();
                new MainFrame().setVisible(true);
            }catch(Exception e){
                String errorMessage = "Error al iniciar la aplicacion: "+ e.getMessage();
                javax.swing.JOptionPane.showMessageDialog(null, errorMessage, "Error de conexion", javax.swing.JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}
