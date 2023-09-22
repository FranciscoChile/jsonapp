package com.sonatafy.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListUpdate implements ChangeType {
    
    private String property;
    private List<String> added;
    private List<String> removed;

}
