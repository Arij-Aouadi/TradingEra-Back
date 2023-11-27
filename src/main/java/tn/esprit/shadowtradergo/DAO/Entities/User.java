package tn.esprit.shadowtradergo.DAO.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name= "User")

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
        long id;
    String username ;
     String cin;
    @Temporal(TemporalType.DATE )
    Date birthDate;
    @Temporal(TemporalType.DATE )
    Date createdDate;

     int phoneNum ;
     String email ;
     String address ;
     Float salary ;
     Float investmentAmount ;
     String relationWithClient ;
     String Profession ;
     String NewQuestions;
     //changement

    // String TypeProjets;
     String TheuserNumber;
     //AJOUTER
     int Rank ;
     float score ; //float or int ??
    @NotNull
    @Size(min = 8,max = 50)
    String password ;


     double revenue;
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    Set<Role> role;
    @JsonIgnore
    @OneToOne
    Position position ;
    public String getMail() {
        return email;
    }


    public User(String username, String email, String encode) {

    }
}
