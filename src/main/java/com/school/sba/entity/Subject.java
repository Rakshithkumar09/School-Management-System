package com.school.sba.entity;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor 
@Builder
public class Subject 
{
   private int subjectId;
   private List<String> subjectName;
}
