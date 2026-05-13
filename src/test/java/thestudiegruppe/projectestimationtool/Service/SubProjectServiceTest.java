package thestudiegruppe.projectestimationtool.Service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import thestudiegruppe.projectestimationtool.Model.Project;
import thestudiegruppe.projectestimationtool.Model.SubProject;
import thestudiegruppe.projectestimationtool.Repository.SubProjectRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static thestudiegruppe.projectestimationtool.Model.Status.DONE;

@ExtendWith( MockitoExtension.class)
public class SubProjectServiceTest {

    //Laver fake version af repository
    @Mock
    private SubProjectRepository repository;

    //Laver service-klasse og sætter vores fake version repository ind i den
    @InjectMocks
    private SubProjectService service;

//test for at tjekke om der returneres den liste som repository giver tilbage
    @Test
    void getAllSubProjectsShouldReturnList( ) {

        //Her adder vi et project objekt og sætter dataen ind i et subProject
        Project project = new Project();
        SubProject subProject = new SubProject(1 , "test SubProject" , "test" , project);

        // Viser at når repository bliver kaldt, så returner det test dataen
        when(repository.getAllSubProjects()).thenReturn(List.of(subProject));

        // tester om serivce-metoden virker
        List < SubProject > result = service.getAllSubProjects();

        //Tjekker om vores resultat er det vi forventer
        //om vi kan se SubProject med navnet "test Subprject" i listen
        assertEquals(1 , result.size());
        assertEquals("test SubProject" , result.get(0).getName());
    }

    //Tjekker om service kalder korrekt til repository
    @Test
    void deleteSubProjectShouldCallRepository( ) {

        // kalder service
        service.deleteSubProject(1);

        // tjekker om repository er blevet kaldt med værdien 1
        verify(repository).deleteSubProject(1);
    }

    //test om createSubProject() bliver korrekt kaldt fra SubProjectService til SubProjectRepository
    @Test
    void createSubProjectShouldCallRepository() {

        // Der laves et nyt projekt og addes et subProject til med testdata
        Project project = new Project();
        SubProject subProject = new SubProject(1 , "test" , "SubProject calls Repo" , project);

        //Her sætter vi vores lavet subprojekt ind i vores service
        service.createSubProject(subProject);

        //Tjekker om vores service kaldermtil repository automatisk
        verify(repository).addSubProject(subProject);
    }
}

