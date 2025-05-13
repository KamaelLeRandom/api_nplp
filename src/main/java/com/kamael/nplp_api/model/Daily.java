package com.kamael.nplp_api.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="daily")
public class Daily {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.DATE)
	@Column
	private LocalDate createFor;
    
    @ManyToOne
    @JoinColumn(name = "cut_id")
    private Cut cut;

    @OneToMany(mappedBy = "daily")
    private List<Result> result;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreateFor() {
        return createFor;
    }

    public void setCreateFor(LocalDate createFor) {
        this.createFor = createFor;
    }

    public Cut getCut() {
        return cut;
    }

    public void setCut(Cut cut) {
        this.cut = cut;
    }

    public List<Result> getResults() {
        return result;
    }

    public void setResults(List<Result> result) {
        this.result = result;
    }
}
