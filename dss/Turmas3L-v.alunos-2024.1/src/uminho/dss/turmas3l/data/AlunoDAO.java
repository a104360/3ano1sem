package uminho.dss.turmas3l.data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.naming.spi.DirStateFactory.Result;

import uminho.dss.turmas3l.business.Aluno;

public class AlunoDAO implements Map<String,Aluno>{
    private static AlunoDAO singleton;    

    private AlunoDAO(){

    }

    public static synchronized AlunoDAO getInstance(){
        if(AlunoDAO.singleton == null){
            AlunoDAO.singleton = new AlunoDAO();
        }
        return AlunoDAO.singleton;
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()){
                stm.executeUpdate("DELETE FROM ALUNOS");
        } catch (SQLException e){
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'clear'");
        }
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL,DAOconfig.USERNAME,DAOconfig.PASSWORD);
             PreparedStatement pstm = conn.prepareStatement("SELECT Num FROM alunos")) {
            pstm.setString(1, key.toString());
            try(ResultSet rs = pstm.executeQuery()){
                r = rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Aluno a = (Aluno) value;
        return this.containsKey(a.getNumero());
    }

    @Override
    public Set<Entry<String, Aluno>> entrySet() {
        throw new UnsupportedOperationException("Unimplemented method 'entrySet'");
    }

    @Override
    public Aluno get(Object key) {
        Aluno aluno = null;

        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstm = conn.prepareStatement("SELECT * FROM alunos WHERE Num = '" + key.toString() + "';")) {
            try (ResultSet rs = pstm.executeQuery()) {
                if(rs.next() == true){
                    aluno = new Aluno(rs.getString("Num"), rs.getString("Nome"),rs.getString("Email"));
                };
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return aluno;
    }

    @Override
    public boolean isEmpty() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             PreparedStatement pstm = conn.prepareStatement("SELECT count(*) from alunos;")) {
            try (ResultSet rs = pstm.executeQuery()) {
                if(rs.next() == false) return true;
                if(rs.getInt("count(*)") == 0) return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }   
        return false;
    }

    @Override
    public Set<String> keySet() {
        Set<String> numeros = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
        PreparedStatement pstm = conn.prepareStatement("SELECT Num FROM alunos;")) {
            try (ResultSet rs = pstm.executeQuery()) {
                while(rs.next()){
                    numeros.add(rs.getString("Num"));
                }
            } catch (SQLException e) {  
                e.printStackTrace();
                return numeros;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return numeros;
        }
        return numeros;
    }

    @Override
    public Aluno put(String arg0, Aluno arg1) {
        Aluno antigo = get(arg0);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             PreparedStatement pstm = conn.prepareStatement("SELECT Num FROM alunos WHERE Num = '" + arg0 + "';")) {
            

            try{
                stm.executeUpdate("INSERT INTO alunos VALUES ('" + arg1.getNumero() + "','" 
                                                                + arg1.getNome() + "','"
                                                                + arg1.getEmail() + "','"
                                                                + arg0 + "'');");
            } catch (SQLException e){
                e.printStackTrace();
                return null;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return arg1;

    }

    @Override
    public void putAll(Map<? extends String, ? extends Aluno> m) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putAll'");
    }

    @Override
    public Aluno remove(Object key) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'size'");
    }

    @Override
    public Collection<Aluno> values() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }


}