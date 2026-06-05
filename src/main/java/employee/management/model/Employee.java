package employee.management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @SequenceGenerator(
            name = "my_seq",
            sequenceName = "my_seq",
            initialValue = 101,
            allocationSize = 1
    )
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="email_id")
    private String emailId;

    @Column(name="phone_no")
    private String phoneNo;

    @Column(name="department")
    private String department;

    @Column(name="experience")
    private String experience;

    @Column(name="technology")
    private String technology;

}
