package com.school.sba.utility;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class ResponseStructure<T> 
{
   private int status;
   private String message;
   private T data;

} 

 