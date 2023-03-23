package uswo.inc.uswofinal.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fund_release_requests")
public class FundReleaseRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "upload_date", nullable = false)
    private Date uploadDate;

    @Column(name = "approval_number", nullable = false)
    private String approvalNumber;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "lcode")
    private Integer lcode;

    @Column(name = "did")
    private Integer did;

    // Getters and setters
    // ...
}
