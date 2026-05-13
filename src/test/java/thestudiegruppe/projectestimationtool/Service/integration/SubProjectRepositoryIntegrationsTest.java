package thestudiegruppe.projectestimationtool.Service.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles ("test")
public class SubProjectRepositoryIntegrationsTest {

    //Indsætter rigtig repository
    @Autowired
    private SubProjectRepository repository;


    @Test
    void getAllSubProjectsShouldReturnDataFromDatabase( ) {

        // Henter alle SubProject fra database
        List <SubProject> result = repository.getAllSubProjects();

        // Tjekker om database returnerer data
        assertFalse(result.isEmpty());
    }
}
