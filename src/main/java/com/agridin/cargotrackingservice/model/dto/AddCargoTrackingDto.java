package com.agridin.cargotrackingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCargoTrackingDto {

    private String cargoTrackingNumbers;
    
    private String username;

    private String password;
}
