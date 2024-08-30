package com.htadg.taskit.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.htadg.taskit.constant.TaskItConstants;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "TASK")
public class Task extends AuditableEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @EqualsAndHashCode.Include
    private String name;

    private String description;
    private TaskItConstants.TASK_STATUS status;

    private String owner;
    private String assignee = "Unassigned";
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    private boolean active = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;

    public String getTaskNumber() {
        return "TASKIT-" + getId();
    }

    public String toString() {
        return "Task(taskNumber=" + this.getTaskNumber() + ", name=" + this.getName() + ", active=" + this.isActive() + ")";
    }
}
