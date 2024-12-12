package hill.ascona.asconapipergames.DAO;

import hill.ascona.asconapipergames.entities.Tournament;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class TournamentDAO {
    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("myconfig");

    public void saveTM(Tournament tm){

    }

    public Tournament getTmById(){
        return new Tournament();
    }

    public List<Tournament> getAllTournaments(){
        return new ArrayList<>();
    }
}
